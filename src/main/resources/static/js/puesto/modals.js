function activateModalDestroy(param){	  
	  var id_puesto = $(param).attr("data-idPuesto");
	  var puesto = $(param).attr("data-puesto");
	  document.getElementById("divMessage").innerHTML = "<p>Esta seguro que desea eliminar el puesto: <b>" + puesto + "</b>?</p>";
	  $("#idPuestoInputDestroy").val(id_puesto); 
	  $("#puestoModalDestroy").modal();
  }