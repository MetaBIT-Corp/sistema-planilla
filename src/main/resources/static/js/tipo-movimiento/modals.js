function activateModalDestroy(param){  
  var id_tipo_movimiento = $(param).attr("data-idTipoMovimiento");
  var movimiento = $(param).attr("data-movimiento");
  var cantidad_movimientos = $(param).attr("data-movimientos");
  
//si es mayor a cero no se puede eliminar, por lo que mostraremos el mensaje de validacion y ocultaremos el boton de eliminar
  if(cantidad_movimientos > 0){
	  $("#validacionEliminacion").show(); 
	  $("#submitEliminacion").hide();
  }
   
  document.getElementById("divMessage").innerHTML = "<p>Esta seguro que desea eliminar el tipo de movimiento: <b>" + movimiento + "</b>?</p>";
  $("#idTipoMovimientoInputDestroy").val(id_tipo_movimiento); 
  $("#tipoMovimientoModalDestroy").modal();
}

function activateModalEnable(param){  
  var id_tipo_movimiento = $(param).attr("data-idTipoMovimiento");
  var movimiento = $(param).attr("data-movimiento");
   
  document.getElementById("divMessageEnable").innerHTML = "<p>Esta seguro que desea habilitar el tipo de movimiento: <b>" + movimiento + "</b>?</p>";
  $("#idTipoMovimientoInputEnable").val(id_tipo_movimiento); 
  $("#tipoMovimientoModalEnable").modal();
}

function activateModalDisable(param){  
  var id_tipo_movimiento = $(param).attr("data-idTipoMovimiento");
  var movimiento = $(param).attr("data-movimiento");
   
  document.getElementById("divMessageDisable").innerHTML = "<p>Esta seguro que desea deshabilitar el tipo de movimiento: <b>" + movimiento + "</b>?</p>";
  $("#idTipoMovimientoInputDisable").val(id_tipo_movimiento); 
  $("#tipoMovimientoModalDisable").modal();
}

