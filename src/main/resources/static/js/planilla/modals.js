function activateModalPago(param){
  $('#btnSolicitarPago').html(
		    `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> 
		    <i class="fas fa-hand-holding-usd mr-1 text-white"></i>
			<span class="text-white">Pagar Planilla</span>`
		  );
  var id_periodo = $(param).attr("data-idPeriodo"); 
  desplegarUnidades();
}

function desplegarUnidades(){
	fetch(window.location.origin + '/planilla/api/unidades-organizacionales')
	.then(response => response.json())
	.then(data => renderSelect(data))
}

function renderSelect(unidades){
	 $('#submitPago').show();
	 var html = '';
	 if(unidades.length>0){
		 html = `<label>Seleccione la Unidad que desea pagar planilla</label>
				 <select class="form-control select2" style="width: 100%;" aria-hidden="true" id="idUnidadOrganizacional" name="idUnidadOrganizacional">
				 <option value="0">Pagar Todas las Unidades</option>`;
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
	 //quitamos spinner de boton
	 $('#btnSolicitarPago').html(
			    `<i class="fas fa-hand-holding-usd mr-1 text-white"></i>
				<span class="text-white">Pagar Planilla</span>`
			  );
	 //presentamos modal
	 $("#modalElegirUnidad").modal();
	 $('.select2').select2();
}

//Activar loading con overlay 

const $button = document.querySelector('#submitPago')
const $overlay = document.querySelector('#overlay')
const $loading = document.querySelector('#loading')
const $wrapLoad = document.querySelector('.wrap-load')
const $wrapper = document.querySelector('.wrapper')


$button.addEventListener('click', (event) => {
    $overlay.classList.add('is-active')
    $loading.classList.add('sk-chase')
    $wrapLoad.classList.add('is-active')
    $wrapper.classList.add('ocultar')
})






