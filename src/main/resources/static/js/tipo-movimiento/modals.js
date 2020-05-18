function activateModalDestroy(param){  
  var id_tipo_movimiento = $(param).attr("data-idTipoMovimiento");
  var movimiento = $(param).attr("data-movimiento");
   
  document.getElementById("divMessage").innerHTML = "<p>Esta seguro que desea eliminar el tipo de movimiento: <b>" + movimiento + "</b>?</p>";
  $("#idTipoMovimientoInputDestroy").val(id_tipo_movimiento); 
  $("#tipoMovimientoModalDestroy").modal();
}

