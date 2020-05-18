function activateModalLock(param){  
  var id_empleado = $(param).attr("data-idEmpleado");
  var codigo = $(param).attr("data-empleado");
   
  document.getElementById("divMessageLock").innerHTML = "<p>Esta seguro que desea bloquear el usuario del empleado: <b>" + codigo + "</b>?</p>";
  $("#idEmpleadoLock").val(id_empleado); 
  $("#usuarioModalLock").modal();
}

function activateModalUnlock(param){  
	  var id_empleado = $(param).attr("data-idEmpleado");
	  var codigo = $(param).attr("data-empleado");
	   
	  document.getElementById("divMessageUnlock").innerHTML = "<p>Esta seguro que desea desbloquear el usuario del empleado: <b>" + codigo + "</b>?</p>";
	  $("#idEmpleadoUnlock").val(id_empleado); 
	  $("#usuarioModalUnlock").modal();
	}

