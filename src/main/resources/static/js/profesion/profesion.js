$(document).ready(function(){

    $('#profesionModal').on('show.bs.modal', function(event){

        $('#errorDiv').hide();

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idProfesion = link.data('id-profesion');
        var profesion = link.data('profesion');
        var esProfesion = link.data('es-profesion');
        var profesionHabilitada = link.data('profesion-habilitada');

        modal.find('.modal-body #idProfesionInput').val(idProfesion);
        modal.find('.modal-body #profesionInput').val(profesion);
        if(esProfesion==1){
            modal.find('.modal-body #esProfesionInput').prop("checked",true);
        }else{
            modal.find('.modal-body #esProfesionInput').prop("checked",false);
        }
        if(profesionHabilitada==1){
            modal.find('.modal-body #profesionHabilitadaInput').prop("checked",true);
        }else{
            modal.find('.modal-body #profesionHabilitadaInput').prop("checked",false);
        }

    });

    $('#profesionBtnNueva').on('click',function () {
        var modal = $('#profesionModal');
        modal.find('.modal-header #profesionModalTitle').text('Crear Profesión u Oficio');
        modal.find('.modal-body #idProfesionInput').attr('disabled',true);
        modal.find('.modal-body #profesionInput').attr('disabled',false);
        modal.find('.modal-body #esProfesionInput').attr('disabled',false);
        modal.find('.modal-footer #profesionSubmitBtn').attr('class','btn btn-primary');
        modal.find('.modal-footer #profesionSubmitBtn').text('Crear');
        modal.find('.modal-footer #profesionCancelarBtn').text('Cancelar');
    });

    $('.profesionBtnEditar').on('click',function () {
        var modal = $('#profesionModal');
        modal.find('.modal-header #profesionModalTitle').text('Editar Profesión u Oficio');
        modal.find('.modal-body #idProfesionInput').attr('disabled',false);
        modal.find('.modal-body #profesionInput').attr('disabled',false);
        modal.find('.modal-body #esProfesionInput').attr('disabled',false);
        modal.find('.modal-footer #profesionSubmitBtn').text('Editar');
        modal.find('.modal-footer #profesionSubmitBtn').attr('class','btn btn-primary');
        modal.find('.modal-footer #profesionCancelarBtn').text('Cancelar');

    });

    $('.profesionBtnVer').on('click',function () {
        var modal = $('#profesionModal');
        modal.find('.modal-header #profesionModalTitle').text('Información de Profesión u Oficio');
        modal.find('.modal-body #profesionInput').attr('disabled',true);
        modal.find('.modal-body #esProfesionInput').attr('disabled',true);
        modal.find('.modal-footer #profesionSubmitBtn').attr('class','d-none');
        modal.find('.modal-footer #profesionCancelarBtn').text('Aceptar');
    });

    $('#profesionSubmitBtn').click(function (e) {

        e.preventDefault();

        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({

            type: 'POST',
            url: url,
            data: form.serialize(),

            success: function (response) {

                if (response.status=="SUCCESS"){

                    $('#profesionSubmitBtn').attr('disabled',true);
                    $('#errorDiv').hide();

                    if($('#idProfesionInput').val()!=''){
                        window.location.href = document.location.origin + "/planilla/profesion/index?update_success=true";
                    }else{
                        window.location.href = document.location.origin + "/planilla/profesion/index?store_success=true";
                    }

                }else{
                    formularioError(response);
                }
            },

            error: conexionError()

        });
    });

    $('#profesionModalDisable').on('show.bs.modal', function(event){

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idProfesion = link.data('id-profesion');
        var profesion = link.data('profesion');
        var profesionHabilitada = link.data('profesion-habilitada');

        modal.find('.modal-body #idProfesionInputDisable').val(idProfesion);

        if(profesionHabilitada==1){
            modal.find('.modal-header #disableModalTitle').text('Deshabilitar Profesión');
            modal.find('.modal-body #disableMessage').text("¿Está seguro que desea deshabilitar la profesión '"+profesion+"'?");
            modal.find('.modal-body #disableInfoMessage').text(
                'Esta acción no eliminará la profesión, pero no será posible asignarla a más empleados. ' +
                'Sin embargo, aquellos empleados a quienes ya se les asigno esta profesión no sufriran ningún cambio.'
            );
            modal.find('.modal-footer #profesionDeshabilitarSubmitBtn').text('Deshabilitar');
            modal.find('.modal-footer #profesionDeshabilitarSubmitBtn').attr('class','btn btn-danger');
        }else{
            modal.find('.modal-header #disableModalTitle').text('Habilitar Profesión');
            modal.find('.modal-body #disableMessage').text("¿Está seguro que desea habilitar la profesión '"+profesion+"'?");
            modal.find('.modal-body #disableInfoMessage').text('Esta acción permitirá que sea posible asignar la profesión a los empleados.');
            modal.find('.modal-footer #profesionDeshabilitarSubmitBtn').text('Habilitar');
            modal.find('.modal-footer #profesionDeshabilitarSubmitBtn').attr('class','btn btn-info');
        }
    });

    $('#profesionModalDestroy').on('show.bs.modal', function(event){
        var modal = $(this);
        var link = $(event.relatedTarget);
        var idProfesion = link.data('id-profesion');
        var profesion = link.data('profesion');
        modal.find('.modal-body #idProfesionInputDestroy').val(idProfesion);
        modal.find('.modal-body #message').text("¿Está seguro que desea eliminar la profesión de "+String(profesion)+"?");
    });

    var store = $('#store').val();
    var update = $('#update').val();
    var enable = $('#enable').val();
    var disable = $('#disable').val();
    var delet = $('#delete').val();

    setTimeout(function() {$(".alert").fadeOut();},3000);

    if(store === 'true'){
        toastr.success("Profesión creada con éxito");
    }
    if(update === 'true'){
        toastr.success("Profesión editada con éxito");
    }
    if(enable === 'true'){
        toastr.success("Profesión habilitada");
    }
    if(disable === 'true'){
        toastr.success("Profesión deshabilitada");
    }
    if(delet === 'true'){
        toastr.success("Profesión eliminada con éxito");
    }
    if(delet === 'false'){
        toastr.warning("Profesión no se puede eliminar. Ya ha sido asignada a uno o más empleados");
    }

});