$(document).ready(function(){
  var store = $('#store').val();
  var update = $('#update').val();
  var delet = $('#delete').val();
  
 
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
 toastr.error("Tipo de movimiento eliminado"); 
  }
  if(delet === 'false'){
 toastr.warning("No se ha podido eliminar el tipo de movimiento. Por favor intente de nuevo.");
  }
})