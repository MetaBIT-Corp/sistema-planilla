$(document).ready(function(){
	var store = $('#store').val();
	var update = $('#update').val();
	var delet = $('#delete').val();
	var enable = $('#enable').val();
	var disable = $('#disable').val();
  
 
  setTimeout(function() {
    $(".alert").fadeOut();           
  },3000);
  
  if(store === 'true'){
	  toastr.success("Puesto creado con éxito"); 
  }
  if(update === 'true'){
	  toastr.success("Puesto editado con éxito"); 
  }
  if(delet === 'true'){
	  toastr.success("Puesto eliminado con éxito"); 
  }
  if(delet === 'false'){
	  toastr.warning("No se ha podido eliminar el puesto. Por favor intente de nuevo.");
  }
  if(enable === 'true'){
	  toastr.success("Puesto habilitado con éxito"); 
  }
  if(disable === 'true'){
	  toastr.success("Puesto deshabilitado con éxito"); 
  }
})

