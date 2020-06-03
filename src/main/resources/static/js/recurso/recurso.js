$(document).ready(function(){

    $('#recursoModal').on('show.bs.modal', function(event){

        $('#divError').hide();

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idRecurso = link.data('id-recurso');
        var recurso = link.data('recurso');

        modal.find('.card-body #idRecursoInput').val(idRecurso);
        modal.find('.card-body #recursoInput').val(recurso);

    });

    $('#recursoBtnNuevo').on('click',function () {
        var modal = $('#recursoModal');
        modal.find('.modal-header #recursoModalTitle').text('Crear Recurso');
        modal.find('.card-body #idRecursoInput').attr('disabled',true);
        modal.find('.card-body #recursoInput').attr('disabled',false);
        modal.find('.card-footer #btnSubmitRecurso').attr('class','btn btn-primary');
        modal.find('.card-footer #btnSubmitRecurso').text('Crear');
        modal.find('.card-footer #btnCancelarRecurso').text('Cancelar');
    });

    $('.recursoBtnEditar').on('click',function () {
        var modal = $('#recursoModal');
        modal.find('.modal-header #recursoModalTitle').text('Editar Recurso');
        modal.find('.card-body #idRecursoInput').attr('disabled',false);
        modal.find('.card-body #recursoInput').attr('disabled',false);
        modal.find('.card-footer #btnSubmitRecurso').text('Editar');
        modal.find('.card-footer #btnSubmitRecurso').attr('class','btn btn-primary');
        modal.find('.card-footer #btnCancelarRecurso').text('Cancelar');

    });

    $('.recursoBtnVer').on('click',function () {
        var modal = $('#recursoModal');
        modal.find('.modal-header #recursoModalTitle').text('Información del Recurso');
        modal.find('.card-body #recursoInput').attr('disabled',true);
        modal.find('.card-footer #btnSubmitRecurso').attr('class','d-none');
        modal.find('.card-footer #btnCancelarRecurso').text('Aceptar');
    });

    $('#btnSubmitRecurso').click(function (e) {

        e.preventDefault();

        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({

            type: 'POST',
            url: url,
            data: form.serialize(),

            success: function (response) {

                if (response.status=="SUCCESS"){

                    $('#btnSubmitRecurso').attr('disabled',true);
                    $('#divError').hide();

                    if($('#idRecursoInput').val()!=''){
                        window.location.href = document.location.origin + "/recurso/index?update_success=true";
                    }else{
                        window.location.href = document.location.origin + "/recurso/index?store_success=true";
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

    $('#recursoModalDestroy').on('show.bs.modal', function(event){
        var modal = $(this);
        var link = $(event.relatedTarget);
        var idRecurso = link.data('id-recurso');
        var recurso = link.data('recurso');
        modal.find('.modal-body #idRecursoInputDestroy').val(idRecurso);
        modal.find('.modal-body #destroyMessage').text("¿Está seguro que desea eliminar el recurso "+String(recurso)+"?");
        modal.find('.modal-body #destroyMessageInfo').text("El recurso no podrá ser eliminado si ya ha sido asignado a uno o más roles");
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
    if(delet === 'true'){
        toastr.error("Recurso eliminado con éxito");
    }
    if(delet === 'false'){
        toastr.warning("Recurso no se puede eliminar. Ya ha sido asignado a uno o más roles");
    }

});