$(document).ready(function(){
    $(function () {
        $("#example1").DataTable({
            "responsive": true,
            "autoWidth": false,
        });
    });
    $(".disable_enable").click(function () {
        var id=$(this).data("id");
        var type=$(this).data("type");
        var  title="";
        var message_modal="";
        var accion_modal="";
        $('#accion_modal').removeClass("btn btn-success");
        $('#accion_modal').removeClass("btn btn-danger");
        if(type!=true){
            $('#accion_modal').addClass("btn btn-success");
            title="Habilitar Usuario";
            message_modal="¿Esta seguro que desea habilitar el empleado seleccionado?";
            accion_modal="Habilitar";
        }else {
            $('#accion_modal').addClass("btn btn-danger");
            title="Deshabilitar Usuario";
            message_modal="¿Esta seguro que desea deshabilitar el empleado seleccionado?";
            accion_modal="Deshabilitar";
        }

        $('#titulo_modal').text(title);
        $('#mensaje_modal').text(message_modal);
        $('#accion_modal').attr("href",document.location.origin+"/planilla/empleado/status?id="+id);
        $('#accion_modal').text(accion_modal).click();
        $('#status_modal').modal("show");
    });
});