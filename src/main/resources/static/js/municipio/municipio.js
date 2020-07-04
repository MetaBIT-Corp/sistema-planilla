$(document).ready(function(){

    $('#municipioModal').on('show.bs.modal', function(event){

        $('#errorDiv').hide();

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idMunicipio = link.data('id-municipio');
        var municipio = link.data('municipio');

        modal.find('.modal-body #idMunicipioInput').val(idMunicipio);
        modal.find('.modal-body #municipioInput').val(municipio);

    });

    $('#municipioBtnNuevo').on('click',function () {
        var modal = $('#municipioModal');
        modal.find('.modal-header #municipioModalTitle').text('Crear Municipio');
        modal.find('.modal-body #idMunicipioInput').attr('disabled',true);
        modal.find('.modal-body #municipioInput').attr('disabled',false);
        modal.find('.modal-footer #municipioSubmitBtn').attr('class','btn btn-primary');
        modal.find('.modal-footer #municipioSubmitBtn').text('Crear');
        modal.find('.modal-footer #municipioCancelarBtn').text('Cancelar');
    });

    $('.municipioBtnEditar').on('click',function () {
        var modal = $('#municipioModal');
        modal.find('.modal-header #municipioModalTitle').text('Editar Municipio');
        modal.find('.modal-body #idMunicipioInput').attr('disabled',false);
        modal.find('.modal-body #municipioInput').attr('disabled',false);
        modal.find('.modal-footer #municipioSubmitBtn').text('Editar');
        modal.find('.modal-footer #municipioSubmitBtn').attr('class','btn btn-primary');
        modal.find('.modal-footer #municipioCancelarBtn').text('Cancelar');

    });

    $('.municipioBtnVer').on('click',function () {
        var modal = $('#municipioModal');
        modal.find('.modal-header #municipioModalTitle').text('Información de Municipio');
        modal.find('.modal-body #municipioInput').attr('disabled',true);
        modal.find('.modal-footer #municipioSubmitBtn').attr('class','d-none');
        modal.find('.modal-footer #municipioCancelarBtn').text('Aceptar');
    });

    $('#municipioSubmitBtn').click(function (e) {

        e.preventDefault();

        var modal = $('#municipioModal');

        var idMunicipio = modal.find('.modal-body #idMunicipioInput').val();
        var idDepartamento = modal.find('.modal-body #idDepartamentoInput').val();

        var form = $(this).parents('form');
        var url = form.attr('action');

        $.ajax({

            type: 'POST',
            url: url,
            data: form.serialize(),

            success: function (response) {

                if (response.status=="SUCCESS"){

                    $('#municipioSubmitBtn').attr('disabled',true);
                    $('#errorDiv').hide();

                    if($('#idMunicipioInput').val()!=''){
                        window.location.href = document.location.origin + "/planilla/municipio/index/"+idDepartamento+"?update_success=true";
                    }else{
                        window.location.href = document.location.origin + "/planilla/municipio/index/"+idDepartamento+"?store_success=true";
                    }

                }else{
                    formularioError(response);
                }
            },

        });
    });

    $('#municipioModalDestroy').on('show.bs.modal', function(event){
        var modal = $(this);
        var link = $(event.relatedTarget);
        var idMunicipio = link.data('id-municipio');
        var municipio = link.data('municipio');
        modal.find('.modal-body #idMunicipioInputDestroy').val(idMunicipio);
        modal.find('.modal-body #destroyMessage').text("¿Está seguro que desea eliminar el municipio de "+String(municipio)+"?");
        modal.find('.modal-body #destroyInfoMessage').text("Esta acción es irreversible");
    });

    var store = $('#store').val();
    var update = $('#update').val();
    var delet = $('#delete').val();

    setTimeout(function() {$(".alert").fadeOut();},3000);

    if(store === 'true'){
        toastr.success("Municipio creado con éxito");
    }
    if(update === 'true'){
        toastr.success("Municipio editado con éxito");
    }
    if(delet === 'true'){
        toastr.success("Municipio eliminado con éxito");
    }

});