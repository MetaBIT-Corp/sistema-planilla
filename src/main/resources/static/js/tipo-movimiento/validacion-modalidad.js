$(document).ready(function() {
	var montobase = $("#montobase").val();
	var porcentajemovimiento = $("#porcentajemovimiento").val();
	var idMovimientoHidden = $("#idMovimientoHidden").val();
				
	//si el id es diferente de cero es porque se está en modo de edicion, por lo ques se cargara la caja de text que tenga valor diferente de cero
	if(idMovimientoHidden != 0){
		
		if(montobase !=0){
			$("#modalidad").val('0');
			$("#divmonto").show();
		}
		
		if(porcentajemovimiento != 0){
			console.log(porcentajemovimiento);
			$("#modalidad").val('1');
			$("#divpm").show();
		}
		
	}
	
	//dinamica al cambiar en el selector
	// 0: Monto Fijo
	// 1: Porcentaje
	
	$("#modalidad").change(function(){
		var opcion = $("#modalidad").val();
		$("#montobase").val('0.00');
		$("#porcentajemovimiento").val('0.00');
		
		if(opcion === '0'){
			$("#divpm").hide();
			$("#divmonto").show();
			
			
		}else{
			$("#divmonto").hide();
			$("#divpm").show();						
			}
		});
	
	});