$(document).ready(function(){
    $("#example1").DataTable({
        "responsive": true,
        "autoWidth": false,
        "columnDefs": [
            { "width": "20%", "targets": 4 }
        ]
    });

    
    $("#crear_unidad_btn").on("click", function () {
        $("#error").attr("hidden", "hidden");
        $("#nombreUnidad").val("");
        $("#create_modal").modal("show");
    });

    $("#btn_registrar").on("click", function () {

        $.ajax({
            type: "POST",
            url: "/planilla/unidades-organizacionales/store",
            data: $("#create_form").serialize(),
            success: function (data) {
                $("#error").attr("hidden", "hidden");
                $("#create_modal").modal("hide");
                toastr.success(data);
                setTimeout(function () {
                    location.reload();
                }, 2000);
            },
            error: function (data) {
                console.log(data.responseText);
                $("#error").removeAttr("hidden");
                $("#error").text(data.responseText);
            }
        });
    });

    $("#example1").on("click",".edit-unidad", function () {
        var idUnidad = $(this).data("id");
        $("#idUnidadOrganizacional").val(idUnidad);
        $.get('/planilla/unidades-organizacionales/edit/' + idUnidad, function (data) {
            $("#edit_nombreUnidad").val(data.result.unidadOrganizacional + "");
            $("#edit_tipo_unidad option:selected").removeAttr("selected");
            $("#edit_tipo_unidad option[value=" + data.result.tipoUnidadOrganizacional.idTipoUnidadOrganizacional + "]").attr("selected", "selected");
            $("#edit_unidadPadre option:selected").removeAttr("selected");
            if (data.result.unidadPadre != null) {
                $("#edit_unidadPadre option[value=" + data.result.unidadPadre.idUnidadOrganizacional + "]").attr("selected", "selected");
            } else {
                $("#edit_unidadPadre option[value='-1']").attr("selected", "selected");
            }

            //Empleados para asignar jefe de unidad
            $.get('/planilla/api/empleados-unidad/' + idUnidad, function (data_emp) {
                var options = "";
                if(data_emp.length !=0){
                    for (var i = 0; i < data_emp.length; i++) {
                        var empleado = data_emp[i];
                        options += '<option value="' + empleado.idEmpleado + '">'
                            +empleado.codigo+' ' + empleado.nombrePrimero +' '+empleado.apellidoMaterno
                            + '</option>';
                    }
                }else{
                    options = '<option value="' + -1 + '">'
                        +'No hay empleados'+'</option>';
                }
                document.getElementById("empleado_jefe_select").innerHTML = options;
                console.log(data.result);
                if(data.result.empleadoJefe!=null){
                    $("#empleado_jefe_select option[value='"+data.result.empleadoJefe.idEmpleado+"']").attr("selected", "selected");
                }else{
                    $("#empleado_jefe_select option[value='"+-1+"']").attr("selected", "selected");
                }
            });
        });



        $("#error_edit").attr("hidden", "hidden");
        $("#edit_unidad").modal("show");
    });

    $("#btn_actualizar").on("click", function () {

        $.ajax({
            type: "POST",
            url: "/planilla/unidades-organizacionales/update",
            data: $("#update_form").serialize(),
            success: function (data) {
                $("#error_edit").attr("hidden", "hidden");
                $("#edit_unidad").modal("hide");
                toastr.success(data);
                setTimeout(function () {
                    location.reload();
                }, 2000);
            },
            error: function (data) {
                $("#error_edit").removeAttr("hidden");
                $("#error_edit").text(data.responseText);
            }
        });
    });

    $("#example1").on("click", ".delete_btn",function () {
        var id = $(this).data("id");
        $("#id_unidad_delete").val(id);
        $("#delete_modal").modal("show");
        $("#delete").on("click", function () {
            $.ajax({
                type: "POST",
                url: "/planilla/unidades-organizacionales/delete",
                data: $("#delete_form").serialize(),
                success: function (data) {
                    $("#delete_modal").modal("hide");
                    toastr.success(data);
                    setTimeout(function () {
                        location.reload();
                    }, 2000);
                },
                error: function (data) {
                    $("#delete_modal").modal("hide");
                    toastr.error(data.responseText);
                    setTimeout(function () {
                        location.reload();
                    }, 2000);
                }
            });
        });
    });
});