$(document).ready(function(){

    $('#profesionModal').on('show.bs.modal', function(event){

        $('#divError').hide();

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idProfesion = link.data('id-profesion');
        var profesion = link.data('profesion');
        var esProfesion = link.data('es-profesion');
        var profesionHabilitada = link.data('profesion-habilitada');

        modal.find('.card-body #idProfesionInput').val(idProfesion);
        modal.find('.card-body #profesionInput').val(profesion);
        if(esProfesion==1){
            modal.find('.card-body #esProfesionInput').prop("checked",true);
        }else{
            modal.find('.card-body #esProfesionInput').prop("checked",false);
        }
        if(profesionHabilitada==1){
            modal.find('.card-body #profesionHabilitadaInput').prop("checked",true);
        }else{
            modal.find('.card-body #profesionHabilitadaInput').prop("checked",false);
        }

    });

    $('#profesionBtnNueva').on('click',function () {
        var modal = $('#profesionModal');
        modal.find('.modal-header #profesionModalTitle').text('Crear Profesión u Oficio');
        modal.find('.card-body #idProfesionInput').attr('disabled',true);
        modal.find('.card-body #profesionInput').attr('disabled',false);
        modal.find('.card-body #esProfesionInput').attr('disabled',false);
        modal.find('.card-footer #btnSubmitProfesion').attr('class','btn btn-primary');
        modal.find('.card-footer #btnSubmitProfesion').text('Crear');
        modal.find('.card-footer #btnCancelarProfesion').text('Cancelar');
    });

    $('.profesionBtnEditar').on('click',function () {
        var modal = $('#profesionModal');
        modal.find('.modal-header #profesionModalTitle').text('Editar Profesión u Oficio');
        modal.find('.card-body #idProfesionInput').attr('disabled',false);
        modal.find('.card-body #profesionInput').attr('disabled',false);
        modal.find('.card-body #esProfesionInput').attr('disabled',false);
        modal.find('.card-footer #btnSubmitProfesion').text('Editar');
        modal.find('.card-footer #btnSubmitProfesion').attr('class','btn btn-primary');
        modal.find('.card-footer #btnCancelarProfesion').text('Cancelar');

    });

    $('.profesionBtnVer').on('click',function () {
        var modal = $('#profesionModal');
        modal.find('.card-body #profesionInput').attr('disabled',true);
        modal.find('.card-body #esProfesionInput').attr('disabled',true);
        modal.find('.card-footer #btnSubmitProfesion').attr('class','d-none');
        modal.find('.card-footer #btnCancelarProfesion').text('Aceptar');
        modal.find('.modal-header #modalTitleProfesion').text('Información de Profesión u Oficio');
    });

    $('#btnSubmitProfesion').click(function (e) {

        e.preventDefault();

        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({

            type: 'POST',
            url: url,
            data: form.serialize(),

            success: function (response) {

                if (response.status=="SUCCESS"){

                    $('#btnSubmitProfesion').attr('disabled',true);
                    $('#divError').hide();

                    if($('#idProfesionInput').val()!=''){
                        window.location.href = document.location.origin + "/profesion/index?update_success=true";

                    }else{
                        window.location.href = document.location.origin + "/profesion/index?store_success=true";
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
            modal.find('.modal-body #disableMessageInfo').text(
                'Esta acción no eliminará la profesión, pero no será posible asignarla a más empleados. ' +
                'Sin embargo, aquellos empleados a quienes ya se les asigno esta profesión no sufriran ningún cambio.'
            );
            modal.find('.modal-footer #profesionBtnSubmitDeshabilitar').text('Deshabilitar');
            modal.find('.modal-footer #profesionBtnSubmitDeshabilitar').attr('class','btn btn-danger');
        }else{
            modal.find('.modal-header #disableModalTitle').text('Habilitar Profesión');
            modal.find('.modal-body #disableMessage').text("¿Está seguro que desea habilitar la profesión '"+profesion+"'?");
            modal.find('.modal-body #disableMessageInfo').text('Esta acción permitirá que sea posible asignar la profesión a los empleados.');
            modal.find('.modal-footer #profesionBtnSubmitDeshabilitar').text('Habilitar');
            modal.find('.modal-footer #profesionBtnSubmitDeshabilitar').attr('class','btn btn-info');
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
        toastr.info("Profesión habilitada");
    }
    if(disable === 'true'){
        toastr.error("Profesión deshabilitada");
    }
    if(delet === 'true'){
        toastr.error("Profesión eliminada con éxito");
    }
    if(delet === 'false'){
        toastr.warning("Profesión no se puede eliminar. Ya ha sido asignada a uno o más empleados");
    }

});