$(document).ready(function(){

    $('#tipoDocumentoModal').on('show.bs.modal', function(event){

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idTipoDocumento = link.data('id-tipo-documento');
        var tipoDocumento = link.data('tipo-documento');
        var formato = link.data('formato');

        modal.find('.card-body #idTipoDocumentoInput').val(idTipoDocumento);
        modal.find('.card-body #tipoDocumentoInput').val(tipoDocumento);
        modal.find('.card-body #formatoInput').val(formato);


    });

    $('#tipoDocumentoBtnNuevo').on('click',function () {

        var modal = $('#tipoDocumentoModal');

        modal.find('.modal-header #tipoDocumentoModalTitle').text('Crear Tipo de Documento');
        modal.find('.card-body #idTipoDocumentoInput').attr('disabled',true);

        modal.find('.card-body #tipoDocumentoInput').attr('disabled',false);
        modal.find('.card-body #tipoDocumentoInput').attr('readonly',false);
        modal.find('.card-body #tipoDocumentoInput').attr('class','form-control');

        modal.find('.card-body #formatoInput').attr('disabled',false);
        modal.find('.card-body #formatoInput').attr('readonly',false);
        modal.find('.card-body #formatoInput').attr('class','form-control');

        modal.find('.card-footer #tipoDocumentoBtnSubmit').text('Crear');
        modal.find('.card-footer #tipoDocumentoBtnSubmit').attr('class','btn btn-primary');
        modal.find('.card-footer #tipoDocumentoBtnCancelar').text('Cancelar');
        modal.find('.card-footer #tipoDocumentoBtnCancelar').attr('class','btn btn-secondary');

    });

    $('.tipoDocumentoBtnEditar').on('click',function () {

        var modal = $('#tipoDocumentoModal');

        modal.find('.modal-header #tipoDocumentoModalTitle').text('Editar Tipo de Documento');
        modal.find('.card-body #idTipoDocumentoInput').attr('disabled',false);

        modal.find('.card-body #tipoDocumentoInput').attr('disabled',false);
        modal.find('.card-body #tipoDocumentoInput').attr('readonly',false);
        modal.find('.card-body #tipoDocumentoInput').attr('class','form-control');

        modal.find('.card-body #formatoInput').attr('disabled',false);
        modal.find('.card-body #formatoInput').attr('readonly',false);
        modal.find('.card-body #formatoInput').attr('class','form-control');

        modal.find('.card-footer #tipoDocumentoBtnSubmit').text('Editar');
        modal.find('.card-footer #tipoDocumentoBtnSubmit').attr('class','btn btn-primary');
        modal.find('.card-footer #tipoDocumentoBtnCancelar').text('Cancelar');
        modal.find('.card-footer #tipoDocumentoBtnCancelar').attr('class','btn btn-secondary');

    });

    $('.tipoDocumentoBtnVer').on('click',function () {

        var modal = $('#tipoDocumentoModal');

        modal.find('.modal-header #tipoDocumentoModalTitle').text('Información de Tipo de Documento');

        modal.find('.card-body #tipoDocumentoInput').attr('readonly',true);
        modal.find('.card-body #tipoDocumentoInput').attr('class','form-control-plaintext');

        modal.find('.card-body #formatoInput').attr('readonly',true);
        modal.find('.card-body #formatoInput').attr('class','form-control-plaintext');

        modal.find('.card-footer #tipoDocumentoBtnSubmit').attr('class','d-none');
        modal.find('.card-footer #tipoDocumentoBtnCancelar').text('Aceptar');
        modal.find('.card-footer #tipoDocumentoBtnCancelar').attr('class','btn btn-info');

    });

    $('#tipoDocumentoModalDisable').on('show.bs.modal', function(event){

        var modal = $(this);
        var link = $(event.relatedTarget);

        var idTipoDocumento = link.data('id-tipo-documento');
        var tipoDocumento = link.data('tipo-documento');
        var tipoDocumentoHabilitado = link.data('tipo-documento-habilitado');

        modal.find('.modal-body #idTipoDocumentoInputDisable').val(idTipoDocumento);

        if(tipoDocumentoHabilitado==1){
            modal.find('.modal-header #disableModalTitle').text('Deshabilitar Tipo de Documento');
            modal.find('.modal-body #message').text('¿Está seguro que desea deshabilitar el Tipo de Documento '+tipoDocumento+'?');
            modal.find('.modal-footer #tipoDocumentoBtnSubmitDeshabilitar').text('Deshabilitar');
            modal.find('.modal-footer #tipoDocumentoBtnSubmitDeshabilitar').attr('class','btn btn-danger')
        }else{
            modal.find('.modal-header #disableModalTitle').text('Habilitar Tipo de Documento');
            modal.find('.modal-body #message').text('¿Está seguro que desea habilitar el Tipo de Documento '+tipoDocumento+'?');
            modal.find('.modal-footer #tipoDocumentoBtnSubmitDeshabilitar').text('Habilitar');
            modal.find('.modal-footer #tipoDocumentoBtnSubmitDeshabilitar').attr('class','btn btn-info')
        }
    });

    var store = $('#store').val();
    var update = $('#update').val();
    var enable = $('#enable').val();
    var disable = $('#disable').val();

    setTimeout(function() {$(".alert").fadeOut();},3000);

    if(store === 'true'){
        toastr.success("Tipo de Documento creado con éxito");
    }
    if(update === 'true'){
        toastr.success("Tipo de Documento editado con éxito");
    }
    if(enable === 'true'){
        toastr.info("Tipo Documento habilitado");
    }
    if(disable === 'true'){
        toastr.error("Tipo Documento deshabilitado");
    }

});