$(document).ready(function(){
	  
  var store = $('#store').val();
  var update = $('#update').val();
  var delet = $('#delete').val();
  
  if(store === 'true'){
	  toastr.success("Día festivo creado con éxito"); 
  }
  if(update === 'true'){
	  toastr.success("Día festivo editado con éxito"); 
  }
  if(delet === 'true'){
	  toastr.success("Día festivo eliminado con éxito"); 
  }
  if(delet === 'false'){
	  toastr.warning("No se ha podido eliminar el día festivo. Ya ha sido asignado.");
  }
  if(store === 'false'){
	  toastr.warning("No se ha podido guardar el registro. Por favor intente de nuevo."); 
  }
})