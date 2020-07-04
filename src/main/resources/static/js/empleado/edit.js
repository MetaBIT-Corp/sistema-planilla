
    $(document).ready(function () {
        var idGenero = $("#genero_select").val();
        var idPuesto = $("#puestos_select").val();

        $.get('/planilla/api/genero/' + idGenero, function (data) {
            if(data.genero=="Mujer"){
                $("#divCasada").removeAttr("hidden");
            }else{
                $("#divCasada").attr("hidden","hidden")
            }
        });
        $.get('/planilla/api/required-user/' + idPuesto, function (data) {
            if (data == true) {
                $("#usuario-tab").removeAttr("hidden");
            } else {
                $("#usuario-tab").attr("hidden", "hidden")
            }
        });
    });
    $("#departamento_select").on('change', function () {
        var idDepartamento = $(this).val();
        $.get('/planilla/api/municipios/' + idDepartamento, function (data) {
            var options = "";
            for (var i = 0; i < data.length; i++) {
                var municipio = data[i];
                options += '<option value="' + municipio.idMunicipio + '">' +
                    municipio.municipio
                    + '</option>'
            }

            document.getElementById("municipio_select").innerHTML = options;
        });
    });

    $("#genero_select").on('change', function () {
        var idGenero = $(this).val();
        $.get('/planilla/api/genero/' + idGenero, function (data) {
            if(data.genero=="Mujer"){
                $("#divCasada").removeAttr("hidden");
            }else{
                $("#lastCName").val("");
                $("#divCasada").attr("hidden","hidden");

            }
        });
    });


    $("#save").on("click",function () {
        $("#save").attr("disabled","disabled");
        $("#save").text("Enviando");
        $.ajax({
            type: "POST",
            url:"/planilla/empleado/update",
            data:$("#form").serialize(),
            dataType:"json",
            success:function (data) {
                window.location.href=document.location.origin+"/planilla/empleado/index?edit=true";
            },
            error:function (data) {
                $("#error_alert_div").removeAttr("hidden");
                console.log(data);
                var m="";
                $.each(data.responseJSON,function (key,value) {
                    console.log(value);
                    m+="<li>"+value+"</li>";
                });
                document.getElementById("error_alert").innerHTML=m;
                //Para mover al inicio de la pagina el control
                $('html, body').animate({
                    scrollTop: 0
                }, 'slow');

                $("#success_alert_div").attr("hidden", "hidden");
                $("#save").removeAttr("disabled");
                $("#save").text("Guardar Datos");
            }
        })
    });
    /*Funcion para activar zona de usuario segun puesto*/
    $("#puestos_select").on('change', function () {
        var idPuesto = $(this).val();
        $.get('/planilla/api/required-user/' + idPuesto, function (data) {
            if (data == true) {
                $("#usuario-tab").removeAttr("hidden");
            } else {
                $("#usuario-tab").val("");
                $("#usuario-tab").attr("hidden", "hidden")
            }
        });
    });