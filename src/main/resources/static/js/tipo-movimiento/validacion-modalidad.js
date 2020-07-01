// Inicialización al desplegar el formulario
$(document).ready(function() {
	var montobase = $("#montobase").val();
	var porcentajemovimiento = $("#porcentajemovimiento").val();
	var idMovimientoHidden = $("#idMovimientoHidden").val();
				
	/* si el id es diferente de cero es porque se está en modo de edicion, 
	 * por lo que se cargara la caja de text que tenga valor diferente de cero
	 */
	if(idMovimientoHidden != 0){
		
		if(montobase !=0){
			$("#modalidad").val('0');
			$("#divmonto").show();
		}
		
		if(porcentajemovimiento != 0){
			$("#modalidad").val('1');
			$("#divpm").show();
			$("#divMontoMaximo").show();
		}	
	}
});

/* dinamica al cambiar en el selector de Modalidad
 * 0: Monto Fijo
 * 1: Porcentaje
 */
$("#modalidad").change(function(){
	var opcion = $("#modalidad").val();
	$("#montobase").val('0.00');
	$("#porcentajemovimiento").val('0.00');
	$("#montomaximo").val('0.00');
	
	if(opcion === '0'){
		$("#divpm").hide();
		$("#divMontoMaximo").hide();
		$("#divmonto").show();	
	}else{
		$("#divmonto").hide();
		$("#divpm").show();
		$("#divMontoMaximo").show();
	}
});

//Cambia el porcentaje máximo con el 20% al seleccionar el movimiento como descuento
$("#esdescuento").change(function(){
	var opcion = $("#esdescuento").val();

	if(opcion === '1'){
		$("#porcentajemovimiento").prop('max', '20.0');
		if($("#porcentajemovimiento").val() > 20){
			$("#porcentajemovimiento").val('20');
		}
	}else{
		$("#porcentajemovimiento").prop('max', ' ');
	}
});

// Se valida que al enviar un formulario se haya ingresado de forma correcta los valores según modalidad
$("#formTipoMovimiento").on('submit', function(evt){
	var opcion = $("#modalidad").val();
	switch(opcion) {
	  case '0': //Si la modalidad es Monto fijo
		  if($("#montobase").val() <= 0){
			  	console.log('Monto fijo');
			  	$("#divMensajesError").html(`<i class="icon fas fa-ban mr-2" style="color:red;"></i>
					<span>El monto fijo debe ser mayor a cero</span>`);
			  	$("#divMensajesError").show();
			  	$("#montobase").focus();
				evt.preventDefault(); 
			}
	    break;
	  case '1': //Si la modalidad es base porcentaje
		  if($("#porcentajemovimiento").val() <= 0 && $("#montomaximo").val() <= 0){
			  	console.log('Monto porcentaje');
			  	
			  	$("#divMensajesError").html(`<i class="icon fas fa-ban mr-2" style="color:red;"></i>
			  								<span>El porcentaje y el monto máximo deben ser mayores a cero</span>`);
			  	$("#divMensajesError").show();
			  	$("#porcentajemovimiento").focus();
			  	evt.preventDefault(); 
			}
	    break;
	  default:
		 console.log('No ha seleccionado')
		 $("#divMensajesError").html(`<i class="icon fas fa-ban mr-2" style="color:red;"></i>
					<span>Debe seleccionar la modalidad del movimiento</span>`);
		 $("#divMensajesError").show();
		 $("#modalidad").focus();
		 evt.preventDefault();
	}
 });

