$(document).ready(function(){
    $("#example1").DataTable({
            "responsive": true,
            "autoWidth": false,
            "columnDefs": [
                { "width": "30%", "targets": 2 }
            ]
        });
        $("#crear_depto_btn").on("click", function () {
            $("#error").attr("hidden", "hidden");
            $("#departamento").val("");
            $("#create_modal").modal("show");
        });

        $("#btn_registrar").on("click", function () {

            $.ajax({
                type: "POST",
                url: "/planilla/departamentos/store",
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
                    $("#error").removeAttr("hidden");
                    $("#error").text(data.responseText);
                }
            });
        });

        $("#example1").on("click",'.edit-depto', function () {
            var idDepto = $(this).data("id");
            $("#idDepartamento_edit").val(idDepto);
            $.get('/planilla/departamentos/edit/' + idDepto, function (data) {
                $("#departamento_edit").val(data.result.departamento + "");
            });
            $("#error_edit").attr("hidden", "hidden");
            $("#edit_modal").modal("show");
        });

        $("#btn_actualizar").on("click", function () {

            $.ajax({
                type: "POST",
                url: "/planilla/departamentos/update",
                data: $("#update_form").serialize(),
                success: function (data) {
                    $("#error_edit").attr("hidden", "hidden");
                    $("#edit_modal").modal("hide");
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

        $("#example1").on("click",'.delete-depto',function () {
            var id = $(this).data("id");
            $("#idDepartamento_delete").val(id);
            $("#delete_modal").modal("show");
            $("#delete").on("click", function () {
                $.ajax({
                    type: "POST",
                    url: "/planilla/departamentos/delete",
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