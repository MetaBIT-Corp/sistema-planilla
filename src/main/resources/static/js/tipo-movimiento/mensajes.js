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
	  toastr.success("Tipo de movimiento creado con éxito"); 
  }
  if(update === 'true'){
	  toastr.success("Tipo de movimiento editado con éxito"); 
  }
  if(delet === 'true'){
	  toastr.success("Tipo de movimiento eliminado con éxito"); 
  }
  if(delet === 'false'){
	  toastr.warning("No se ha podido eliminar el tipo de movimiento. Por favor intente de nuevo.");
  }
  if(enable === 'true'){
	  toastr.success("Tipo de movimiento habilitado con éxito"); 
  }
  if(disable === 'true'){
	  toastr.success("Tipo de movimiento deshabilitado con éxito"); 
  }
})