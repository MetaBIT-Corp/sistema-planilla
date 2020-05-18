function activateModalDestroy(param){
	  restablecer();
	  
	  var id_puesto = $(param).attr("data-idPuesto");
	  var puesto = $(param).attr("data-puesto");
	  var epu = $(param).attr("data-epu");
	   
	  //si es mayor a cero no se puede eliminar, por lo que mostraremos el mensaje de validacion y ocultaremos el boton de eliminar
	  if(epu > 0){
		  $("#validacionEliminacion").show(); 
		  $("#submitEliminacion").hide();
	  }
	  
	  document.getElementById("divMessage").innerHTML = "<p>Esta seguro que desea eliminar el puesto: <b>" + puesto + "</b>?</p>";
	  $("#idPuestoInputDestroy").val(id_puesto); 
	  $("#puestoModalDestroy").modal();
  }

function restablecer(){
    $("#validacionEliminacion").hide();
	$("#submitEliminacion").show();
}
