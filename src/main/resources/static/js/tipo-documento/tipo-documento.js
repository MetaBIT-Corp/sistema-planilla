$(document).ready(function(){

    function activateModalDisable(param){

        var id_tipo_documento = $(param).attr("data-id-tipo-documento");
        var tipo_documento = $(param).attr("data-tipo-documento");
        var tipo_documento_habilitado = $(param).attr("data-tipo-documento-habilitado");

        if(tipo_documento_habilitado=="true"){
            document.getElementById("divMessage").innerHTML = "<p>¿Est&aacute; seguro que desea deshabilitar el Tipo de Documento <b>" + tipo_documento + "</b>?</p>";
            $('#modal-tittle-disable').text("Deshabilitar Tipo de Documento")
            $('#btn_deshabilitar').html("Deshabilitar")
            $('#btn_deshabilitar').attr('class','btn btn-danger')

        }else{
            document.getElementById("divMessage").innerHTML = "<p>¿Est&aacute; seguro que desea habilitar el Tipo de Documento <b>" + tipo_documento + "</b>?</p>";
            $('#modal-tittle-disable').text("Habilitar Tipo de Documento")
            $('#btn_deshabilitar').html("Habilitar")
            $('#btn_deshabilitar').attr('class','btn btn-info')
        }

        $("#idTipoDocumentoInputDisable").val(id_tipo_documento);
        $("#tipoDocumentoModalDisable").modal();
    }

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