function activateModalPago(param){  
  var id_periodo = $(param).attr("data-idPeriodo"); 
  desplegarUnidades();
  $("#modalElegirUnidad").modal();
}

function desplegarUnidades(){
	fetch(window.location.origin + '/api/unidades-organizacionales')
	.then(response => response.json())
	.then(data => renderSelect(data))
}

function renderSelect(unidades){
	console.log(unidades);
	 $('#submitPago').show();
	 var html = '';
	 if(unidades.length>0){
		 html = `<label>Seleccione la Unidad que desea pagar planilla</label>
				 <select class="form-control select2" style="width: 100%;" aria-hidden="true" id="idUnidadOrganizacional" name="idUnidadOrganizacional">`;
		 for (var i = 0; i < unidades.length; ++i) {
			 html += `<option value=${unidades[i].idUnidadOrganizacional}>${unidades[i].unidadOrganizacional}</option>`;
	     }
		 html += `</select>`;
	 }else{
		 html = `<div class="callout callout-info" id="validacionEliminacion">
	                  <small id="emailHelp" class="form-text text-muted">
	                  	<i class="fas fa-info-circle mr-2"></i> 
	                  	No se encuentran unidades organizacionales disponibles.
	                  </small>
	              </div>`;
		 $('#submitPago').hide();
	 }
	 $('#divBodyDesplegarUnidades').html(html);
	 $('.select2').select2();
}


