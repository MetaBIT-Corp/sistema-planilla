$(document).ready(function() {
    $("#btn_create").on("click", function() {
        $("#id_rango").val(0);
        $("#ventaMin").val(1);
        $("#ventaMax").val(1);
        $("#tasaComision").val(0.01);
        $("#habilitado").prop("checked", false);
        $("#staticBackdropLabel").text("Crear Rango Comision");
        $("#create_modal").modal("show");
        $("#error").attr("hidden", "hidden");
    });
    $(".btn_edit").on("click", function() {
        var id = $(this).data("id");
        $("#error").attr("hidden", "hidden");
        $("#staticBackdropLabel").text("Editar Rango Comision");
        $("#create_modal").modal("show");
        $.get('/planilla/rango-comision/edit/' + id, function(data) {
            $("#id_rango").val(data.result.id);
            $("#ventaMin").val(data.result.ventaMin);
            $("#ventaMax").val(data.result.ventaMax);
            $("#tasaComision").val(data.result.tasaComision);
            if (data.result.habilitado) {
                $("#habilitado").prop("checked", true);
            }
        });
    });
    $("#btn_registrar").on("click", function() {
        $.ajax({
            type: "POST",
            url: "/planilla/rango-comision/store",
            data: $("#create_form").serialize(),
            success: function(data) {
                $("#error").attr("hidden", "hidden");
                $("#create_modal").modal("hide");
                toastr.success(data);
                setTimeout(function() {
                    location.reload();
                }, 2000);
            },
            error: function(data) {
                console.log(data.responseText);
                $("#error").removeAttr("hidden");
                $("#error").text(data.responseText);
            }
        });
    });
});