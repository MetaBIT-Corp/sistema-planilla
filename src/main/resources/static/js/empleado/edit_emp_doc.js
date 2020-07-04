$(document).ready(function(){
    $(function () {
        var table = $("#example1").DataTable({
            "responsive": true,
            "autoWidth": false,
        });
        if (table.rows().count() == 1) {
            $(".delete_doc").attr("disabled", "disabled");
        } else {
            $(".delete_doc").removeAttr("disabled");
        }
        var table2 = $("#example2").DataTable({
            "responsive": true,
            "autoWidth": false,
        });

        if (table2.rows().count() == 0) {
            $("#save").attr("disabled", "disabled");
        } else {
            $("#save").removeAttr("disabled");
        }
    });

    //EDITAR TIPO DOCUMENTO
    $(".edit_doc").on("click", function () {
        var id = $(this).data("id");
        var input = $("#emp_doc_" + id);
        $("#icon_doc_" + id)
        var icon = $("#icon_doc_" + id);
        if ($(this).data("accion") != 1) {
            input.removeAttr("disabled");
            icon.removeClass();
            icon.addClass("fas fa-check-circle");
            $(this).attr("data-accion", 1);
        } else {
            console.log(input.serialize());
            $.ajax({
                type: "POST",
                url: "/planilla/empleado/update-documentos",
                data: input.serialize(),
                dataType: "json",
                success: function (data) {
                    window.location.href = document.location;
                },
                error: function (data) {
                    $("#error_alert_div").removeAttr("hidden");
                    console.log(data);
                    var m = "";
                    $.each(data.responseJSON, function (key, value) {
                        console.log(value);
                        m += "<li>" + value + "</li>";
                    });
                    document.getElementById("error_alert").innerHTML = m;
                    //Para mover al inicio de la pagina el control
                    $('html, body').animate({
                        scrollTop: 0
                    }, 'slow');
                    $("#success_alert_div").attr("hidden", "hidden");
                }
            });
        }
    });

    //ELIMINAR TIPO DOCUMENTO
    $(".delete_doc").on("click", function () {
        var id = $(this).data("id");
        var nombreTipoDoc = $(this).data("name");
        var nombreEmp = $(this).data("name");
        $('#titulo_modal').text("Eliminar " + nombreTipoDoc);
        $('#mensaje_modal').text("¿Desea eliminar " + nombreTipoDoc + " del empleado/a " + $("#Empleado").data("emp") + " ?");
        $("#id-doc-emp").val(id);
        $('#delete_modal').modal("show");
    });
    $("#accion_modal").on("click", function () {
        $.ajax({
            type: "POST",
            url: "/planilla/empleado/delete-documentos",
            data: $("#id-doc-emp").serialize(),
            dataType: "json",
            success: function (data) {
                window.location.href = document.location;
            },
            error: function (data) {
                console.log(data);
            }
        });
    });

    //AGREGAR NUEVO TIPO DOCUMENTO
    $("#save").on("click", function () {
        $("#save").attr("disabled", "disabled");
        $("#save").text("Enviando");
        var tabla_docs = $("#example2").DataTable();

        $.ajax({
            type: "POST",
            url: "/planilla/empleado/add-documentos",
            data: tabla_docs.$('input').serialize()+ "&" +$("#idEmpleado").serialize(),
            dataType: "json",
            success: function (data) {
                window.location.href = document.location;
            },
            error: function (data) {
                $("#error_alert_div").removeAttr("hidden");
                console.log(data);
                var m = "";
                $.each(data.responseJSON, function (key, value) {
                    console.log(value);
                    m += "<li>" + value + "</li>";
                });
                document.getElementById("error_alert").innerHTML = m;
                //Para mover al inicio de la pagina el control
                $('html, body').animate({
                    scrollTop: 0
                }, 'slow');

                $("#success_alert_div").attr("hidden", "hidden");
                $("#save").removeAttr("disabled");
                $("#save").text("Agregar Nuevos Documentos");
            }
        });
    });
});