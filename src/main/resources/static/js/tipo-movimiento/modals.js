function activateModalDestroy(param){  
  var id_tipo_movimiento = $(param).attr("data-idTipoMovimiento");
  var movimiento = $(param).attr("data-movimiento");
   
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

