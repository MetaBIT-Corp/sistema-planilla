$(document).ready(function(){

    $('#rolModal').on('show.bs.modal', function(event){

        $('#divError').hide();

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idRol = link.data('id-rol');
        var rol = link.data('rol');

        modal.find('.card-body #idRolInput').val(idRol);
        modal.find('.card-body #rolInput').val(rol);

    });

    $('#rolBtnNuevo').on('click',function () {
        var modal = $('#rolModal');
        modal.find('.modal-header #rolModalTitle').text('Crear Rol');
        modal.find('.card-body #idRolInput').attr('disabled',true);
        modal.find('.card-body #rolInput').attr('disabled',false);
        modal.find('.card-footer #btnSubmitRol').attr('class','btn btn-primary');
        modal.find('.card-footer #btnSubmitRol').text('Crear');
        modal.find('.card-footer #btnCancelarRol').text('Cancelar');
    });

    $('.rolBtnEditar').on('click',function () {
        var modal = $('#rolModal');
        modal.find('.modal-header #rolModalTitle').text('Editar Rol');
        modal.find('.card-body #idRolInput').attr('disabled',false);
        modal.find('.card-body #rolInput').attr('disabled',false);
        modal.find('.card-footer #btnSubmitRol').text('Editar');
        modal.find('.card-footer #btnSubmitRol').attr('class','btn btn-primary');
        modal.find('.card-footer #btnCancelarRol').text('Cancelar');

    });

    $('.rolBtnVer').on('click',function () {
        var modal = $('#rolModal');
        modal.find('.modal-header #rolModalTitle').text('Información del Rol');
        modal.find('.card-body #rolInput').attr('disabled',true);
        modal.find('.card-footer #btnSubmitRol').attr('class','d-none');
        modal.find('.card-footer #btnCancelarRol').text('Aceptar');
    });

    $('#btnSubmitRol').click(function (e) {

        e.preventDefault();

        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({

            type: 'POST',
            url: url,
            data: form.serialize(),

            success: function (response) {

                if (response.status=="SUCCESS"){

                    $('#btnSubmitRol').attr('disabled',true);
                    $('#divError').hide();

                    if($('#idRecursoInput').val()!=''){
                        window.location.href = document.location.origin + "/rol/index?update_success=true";
                    }else{
                        window.location.href = document.location.origin + "/rol/index?store_success=true";
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

    $('#rolModalDestroy').on('show.bs.modal', function(event){
        var modal = $(this);
        var link = $(event.relatedTarget);
        var idRol = link.data('id-rol');
        var rol = link.data('rol');
        modal.find('.modal-body #idRolInputDestroy').val(idRol);
        modal.find('.modal-body #destroyMessage').text("¿Está seguro que desea eliminar el rol "+String(rol)+"?");
        modal.find('.modal-body #destroyMessageInfo').text("El rol no podrá ser eliminado si ya ha sido asignado a uno o más usuarios");
    });

    var store = $('#store').val();
    var update = $('#update').val();
    var enable = $('#enable').val();
    var delet = $('#delete').val();

    setTimeout(function() {$(".alert").fadeOut();},3000);

    if(store === 'true'){
        toastr.success("Rol creado con éxito");
    }
    if(update === 'true'){
        toastr.success("Rol editado con éxito");
    }
    if(delet === 'true'){
        toastr.error("Rol eliminado con éxito");
    }
    if(delet === 'false'){
        toastr.warning("Rol no se puede eliminar. Ya ha sido asignado a uno o más roles");
    }

});