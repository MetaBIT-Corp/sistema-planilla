$(document).ready(function(){

    $('#privilegioModal').on('show.bs.modal', function(event){

        $('#divError').hide();

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idPrivilegio = link.data('id-privilegio');
        var privilegio = link.data('privilegio');

        modal.find('.card-body #idPrivilegioInput').val(idPrivilegio);
        modal.find('.card-body #privilegioInput').val(privilegio);

    });

    $('#privilegioBtnNuevo').on('click',function () {
        var modal = $('#privilegioModal');
        modal.find('.modal-header #privilegioModalTitle').text('Crear Privilegio');
        modal.find('.card-body #idPrivilegioInput').attr('disabled',true);
        modal.find('.card-body #privilegioInput').attr('disabled',false);
        modal.find('.card-footer #btnSubmitPrivilegio').attr('class','btn btn-primary');
        modal.find('.card-footer #btnSubmitPrivilegio').text('Crear');
        modal.find('.card-footer #btnCancelarPrivilegio').text('Cancelar');
    });

    $('.privilegioBtnEditar').on('click',function () {
        var modal = $('#privilegioModal');
        modal.find('.modal-header #privilegioModalTitle').text('Editar Privilegio');
        modal.find('.card-body #idPrivilegioInput').attr('disabled',false);
        modal.find('.card-body #privilegioInput').attr('disabled',false);
        modal.find('.card-footer #btnSubmitPrivilegio').text('Editar');
        modal.find('.card-footer #btnSubmitPrivilegio').attr('class','btn btn-primary');
        modal.find('.card-footer #btnCancelarPrivilegio').text('Cancelar');

    });

    $('.privilegioBtnVer').on('click',function () {
        var modal = $('#privilegioModal');
        modal.find('.modal-header #privilegioModalTitle').text('Información del Privilegio');
        modal.find('.card-body #privilegioInput').attr('disabled',true);
        modal.find('.card-footer #btnSubmitPrivilegio').attr('class','d-none');
        modal.find('.card-footer #btnCancelarPrivilegio').text('Aceptar');
    });

    $('#btnSubmitPrivilegio').click(function (e) {

        e.preventDefault();

        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({

            type: 'POST',
            url: url,
            data: form.serialize(),

            success: function (response) {

                if (response.status=="SUCCESS"){

                    $('#btnSubmitPrivilegio').attr('disabled',true);
                    $('#divError').hide();

                    if($('#idPrivilegioInputInput').val()!=''){
                        window.location.href = document.location.origin + "/privilegio/index?update_success=true";
                    }else{
                        window.location.href = document.location.origin + "/privilegio/index?store_success=true";
                    }

                }else{

                    $('#divError').show();
                    var child = document.getElementById("ulError").lastElementChild;

                    while (child) {
                        document.getElementById("ulError").removeChild(child);
                        child = document.getElementById("ulError").lastElementChild;
                    }

                    for(i=0;i<response.result.length;i++){
                        var li = document.createElement('li');
                        var liContent = document.createTextNode(response.result[i].code);
                        li.appendChild(liContent);
                        document.getElementById("ulError").appendChild(li);
                    }

                }

            },

            error: function (e) {
                alert('Error: '+e);
            }

        });
    });

    $('#privilegioModalDestroy').on('show.bs.modal', function(event){
        var modal = $(this);
        var link = $(event.relatedTarget);
        var idPrivilegio = link.data('id-privilegio');
        var privilegio = link.data('privilegio');
        modal.find('.modal-body #idPrivilegioInputDestroy').val(idPrivilegio);
        modal.find('.modal-body #destroyMessage').text("¿Está seguro que desea eliminar el privilegio "+String(privilegio)+"?");
        modal.find('.modal-body #destroyMessageInfo').text("El privilegio no podrá ser eliminado si ya ha sido asignado a uno o más roles");
    });

    var store = $('#store').val();
    var update = $('#update').val();
    var enable = $('#enable').val();
    var delet = $('#delete').val();

    setTimeout(function() {$(".alert").fadeOut();},3000);

    if(store === 'true'){
        toastr.success("Privilegio creado con éxito");
    }
    if(update === 'true'){
        toastr.success("Privilegio editado con éxito");
    }
    if(delet === 'true'){
        toastr.error("Privilegio eliminado con éxito");
    }
    if(delet === 'false'){
        toastr.warning("Privilegio no se puede eliminar. Ya ha sido asignado a uno o más roles");
    }

});