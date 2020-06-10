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
                        window.location.href = document.location.origin + "/rol/index?update_success=true";
                    }else{
                        window.location.href = document.location.origin + "/rol/index?store_success=true";
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
    if(delet === 'true'){
        toastr.error("Rol eliminado con éxito");
    }
    if(delet === 'false'){
        toastr.warning("Rol no se puede eliminar. Ya ha sido asignado a uno o más usuarios");
    }

});