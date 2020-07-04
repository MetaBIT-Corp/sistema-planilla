$(document).ready(function(){

    $('#rolModal').on('show.bs.modal', function(event){

        $('#errorDiv').hide();

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idRol = link.data('id-rol');
        var rol = link.data('rol');

        modal.find('.modal-body #idRolInput').val(idRol);
        modal.find('.modal-body #rolInput').val(rol);

    });

    $('#rolNuevoBtn').on('click',function () {
        var modal = $('#rolModal');
        modal.find('.modal-header #rolModalTitle').text('Crear Rol');
        modal.find('.modal-body #idRolInput').attr('disabled',true);
        modal.find('.modal-body #rolInput').attr('disabled',false);
        modal.find('.modal-footer #rolSubmitBtn').attr('class','btn btn-primary');
        modal.find('.modal-footer #rolSubmitBtn').text('Crear');
        modal.find('.modal-footer #rolCancelarBtn').text('Cancelar');
    });

    $('.rolEditarBtn').on('click',function () {
        var modal = $('#rolModal');
        modal.find('.modal-header #rolModalTitle').text('Editar Rol');
        modal.find('.modal-body #idRolInput').attr('disabled',false);
        modal.find('.modal-body #rolInput').attr('disabled',false);
        modal.find('.modal-footer #rolSubmitBtn').text('Editar');
        modal.find('.modal-footer #rolSubmitBtn').attr('class','btn btn-primary');
        modal.find('.modal-footer #rolCancelarBtn').text('Cancelar');

    });

    $('.rolVerBtn').on('click',function () {
        var modal = $('#rolModal');
        modal.find('.modal-header #rolModalTitle').text('Información del Rol');
        modal.find('.modal-body #rolInput').attr('disabled',true);
        modal.find('.modal-footer #rolSubmitBtn').attr('class','d-none');
        modal.find('.modal-footer #rolCancelarBtn').text('Aceptar');
    });

    $('#rolSubmitBtn').click(function (e) {

        e.preventDefault();

        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({

            type: 'POST',
            url: url,
            data: form.serialize(),

            success: function (response) {

                if (response.status=="SUCCESS"){

                    $('#rolSubmitBtn').attr('disabled',true);
                    $('#errorDiv').hide();

                    if($('#idRolInput').val()!=''){
                        window.location.href = document.location.origin + "/planilla/rol/index?update_success=true";
                    }else{
                        window.location.href = document.location.origin + "/planilla/rol/index?store_success=true";
                    }

                }else{
                    if(response.status=="NOEDITABLE"){
                        window.location.href = document.location.origin + "/planilla/rol/index?update_success=false";
                    }else{
                        formularioError(response);
                    }
                }
            },
        });
    });

    $('#rolModalDestroy').on('show.bs.modal', function(event){
        var modal = $(this);
        var link = $(event.relatedTarget);
        var idRol = link.data('id-rol');
        var rol = link.data('rol');
        modal.find('.modal-body #idRolInputDestroy').val(idRol);
        modal.find('.modal-body #destroyMessage').text("¿Está seguro que desea eliminar el rol "+String(rol)+"?");
        modal.find('.modal-body #destroyInfoMessage').text("El rol no podrá ser eliminado si ya ha sido asignado a uno o más usuarios");
    });

    var store = $('#store').val();
    var update = $('#update').val();
    var delet = $('#delete').val();

    setTimeout(function() {$(".alert").fadeOut();},3000);

    if(store === 'true'){
        toastr.success("Rol creado con éxito");
    }
    if(update === 'true'){
        toastr.success("Rol editado con éxito");
    }
    if(update === 'false'){
        toastr.warning("Rol no se puede editar. Ya ha sido asignado a uno o más usuarios");
    }
    if(delet === 'true'){
        toastr.success("Rol eliminado con éxito");
    }
    if(delet === 'false'){
        toastr.warning("Rol no se puede eliminar. Ya ha sido asignado a uno o más usuarios");
    }

});