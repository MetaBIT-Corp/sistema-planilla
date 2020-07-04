$(document).ready(function(){

    $('#recursoModal').on('show.bs.modal', function(event){

        $('#errorDiv').hide();

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idRecurso = link.data('id-recurso');
        var recurso = link.data('recurso');

        modal.find('.modal-body #idRecursoInput').val(idRecurso);
        modal.find('.modal-body #recursoInput').val(recurso);

    });

    $('#recursoNuevoBtn').on('click',function () {
        var modal = $('#recursoModal');
        modal.find('.modal-header #recursoModalTitle').text('Crear Recurso');
        modal.find('.modal-body #idRecursoInput').attr('disabled',true);
        modal.find('.modal-body #recursoInput').attr('disabled',false);
        modal.find('.modal-footer #recursoSubmitBtn').attr('class','btn btn-primary');
        modal.find('.modal-footer #recursoSubmitBtn').text('Crear');
        modal.find('.modal-footer #recursoCancelarBtn').text('Cancelar');
    });

    $('.recursoEditarBtn').on('click',function () {
        var modal = $('#recursoModal');
        modal.find('.modal-header #recursoModalTitle').text('Editar Recurso');
        modal.find('.modal-body #idRecursoInput').attr('disabled',false);
        modal.find('.modal-body #recursoInput').attr('disabled',false);
        modal.find('.modal-footer #recursoSubmitBtn').text('Editar');
        modal.find('.modal-footer #recursoSubmitBtn').attr('class','btn btn-primary');
        modal.find('.modal-footer #recursoCancelarBtn').text('Cancelar');

    });

    $('.recursoVerBtn').on('click',function () {
        var modal = $('#recursoModal');
        modal.find('.modal-header #recursoModalTitle').text('Información del Recurso');
        modal.find('.modal-body #recursoInput').attr('disabled',true);
        modal.find('.modal-footer #recursoSubmitBtn').attr('class','d-none');
        modal.find('.modal-footer #recursoCancelarBtn').text('Aceptar');
    });

    $('#recursoSubmitBtn').click(function (e) {

        e.preventDefault();

        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({

            type: 'POST',
            url: url,
            data: form.serialize(),

            success: function (response) {

                if (response.status=="SUCCESS"){

                    $('#recursoSubmitBtn').attr('disabled',true);
                    $('#errorDiv').hide();

                    if($('#idRecursoInput').val()!=''){
                        window.location.href = document.location.origin + "/planilla/recurso/index?update_success=true";
                    }else{
                        window.location.href = document.location.origin + "/planilla/recurso/index?store_success=true";
                    }

                }else{
                    if(response.status=="NOEDITABLE"){
                        window.location.href = document.location.origin + "/planilla/recurso/index?update_success=false";
                    }else{
                        formularioError(response);
                    }
                }
            },
        });
    });

    $('#recursoModalDestroy').on('show.bs.modal', function(event){
        var modal = $(this);
        var link = $(event.relatedTarget);
        var idRecurso = link.data('id-recurso');
        var recurso = link.data('recurso');
        modal.find('.modal-body #idRecursoInputDestroy').val(idRecurso);
        modal.find('.modal-body #destroyMessage').text("¿Está seguro que desea eliminar el recurso "+String(recurso)+"?");
        modal.find('.modal-body #destroyInfoMessage').text("El recurso no podrá ser eliminado si ya ha sido asignado a uno o más roles");
    });

    var store = $('#store').val();
    var update = $('#update').val();
    var enable = $('#enable').val();
    var delet = $('#delete').val();

    setTimeout(function() {$(".alert").fadeOut();},3000);

    if(store === 'true'){
        toastr.success("Recurso creado con éxito");
    }
    if(update === 'true'){
        toastr.success("Recurso editado con éxito");
    }
    if(update === 'false'){
        toastr.warning("Recurso no se puede editar. Ya ha sido asignado a uno o más roles");
    }
    if(delet === 'true'){
        toastr.success("Recurso eliminado con éxito");
    }
    if(delet === 'false'){
        toastr.warning("Recurso no se puede eliminar. Ya ha sido asignado a uno o más roles");
    }

});