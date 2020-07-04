$(document).ready(function(){
    $(function () {
        var table = $("#example1").DataTable({
            "responsive": true,
            "autoWidth": false,
        });
        var table2 = $("#example2").DataTable({
            "responsive": true,
            "autoWidth": false,
        });
    });
    $("#delete").on("click",function () {
        var id = $(this).data("id");
        $('#delete_modal').modal("show");
    });
    $("#accion_modal").on("click", function () {
        $('#delete_modal').modal("hide");
        $("#delete").attr("disabled", "disabled");
        $("#delete").text("Eliminando");

        var tabla_prof = $("#example1").DataTable();
        $.ajax({
            type: "POST",
            url: "/planilla/empleado/delete-profesiones",
            data: tabla_prof.$('input').serialize()+ "&" + $("#idEmpleado").serialize(),
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
                $("#delete").removeAttr("disabled");
                $("#delete").text("Eliminar");
            }
        });
    });


    $("#save").on("click", function () {
        $("#save").attr("disabled", "disabled");
        $("#save").text("Enviando");

        var tabla_prof = $("#example2").DataTable();
        $.ajax({
            type: "POST",
            url: "/planilla/empleado/add-profesiones",
            data: tabla_prof.$('input').serialize()+ "&" + $("#idEmpleado").serialize(),
            dataType: "json",
            success: function (data) {
                window.location.href = document.location;
            },
            error: function (data) {
                $("#success_alert_div").attr("hidden", "hidden");
                $("#save").removeAttr("disabled");
                $("#save").text("Agregar");
            }
        });
    });
});