$(document).ready(function(){

    $('#privilegioModal').on('show.bs.modal', function(event){

        $('#errorDiv').hide();

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idPrivilegio = link.data('id-privilegio');
        var privilegio = link.data('privilegio');

        modal.find('.modal-body #idPrivilegioInput').val(idPrivilegio);
        modal.find('.modal-body #privilegioInput').val(privilegio);

    });

    $('#privilegioNuevoBtn').on('click',function () {
        var modal = $('#privilegioModal');
        modal.find('.modal-header #privilegioModalTitle').text('Crear Privilegio');
        modal.find('.modal-body #idPrivilegioInput').attr('disabled',true);
        modal.find('.modal-body #privilegioInput').attr('disabled',false);
        modal.find('.modal-footer #privilegioSubmitBtn').attr('class','btn btn-primary');
        modal.find('.modal-footer #privilegioSubmitBtn').text('Crear');
        modal.find('.modal-footer #privilegioCancelarBtn').text('Cancelar');
    });

    $('.privilegioEditarBtn').on('click',function () {
        var modal = $('#privilegioModal');
        modal.find('.modal-header #privilegioModalTitle').text('Editar Privilegio');
        modal.find('.modal-body #idPrivilegioInput').attr('disabled',false);
        modal.find('.modal-body #privilegioInput').attr('disabled',false);
        modal.find('.modal-footer #privilegioSubmitBtn').text('Editar');
        modal.find('.modal-footer #privilegioSubmitBtn').attr('class','btn btn-primary');
        modal.find('.modal-footer #privilegioCancelarBtn').text('Cancelar');

    });

    $('.privilegioVerBtn').on('click',function () {
        var modal = $('#privilegioModal');
        modal.find('.modal-header #privilegioModalTitle').text('Información del Privilegio');
        modal.find('.modal-body #privilegioInput').attr('disabled',true);
        modal.find('.modal-footer #privilegioSubmitBtn').attr('class','d-none');
        modal.find('.modal-footer #privilegioCancelarBtn').text('Aceptar');
    });

    $('#privilegioSubmitBtn').click(function (e) {

        e.preventDefault();

        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({

            type: 'POST',
            url: url,
            data: form.serialize(),

            success: function (response) {

                if (response.status=="SUCCESS"){

                    $('#privilegioSubmitBtn').attr('disabled',true);
                    $('#errorDiv').hide();

                    if($('#idPrivilegioInputInput').val()!=''){
                        window.location.href = document.location.origin + "/privilegio/index?update_success=true";
                    }else{
                        window.location.href = document.location.origin + "/privilegio/index?store_success=true";
                    }

                }else{

                    $('#errorDiv').show();
                    var child = document.getElementById("errorUl").lastElementChild;

                    while (child) {
                        document.getElementById("errorUl").removeChild(child);
                        child = document.getElementById("errorUl").lastElementChild;
                    }

                    for(i=0;i<response.result.length;i++){
                        var li = document.createElement('li');
                        var contentLi = document.createTextNode(response.result[i].code);
                        li.appendChild(contentLi);
                        document.getElementById("errorUl").appendChild(li);
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
        modal.find('.modal-body #destroyInfoMessage').text("El privilegio no podrá ser eliminado si ya ha sido asignado a uno o más roles");
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