$(document).ready(function(){
	  var lock = $('#lock_success').val();
	  var unlock = $('#unlock_success').val();
	  var enable = $('#enable_success').val();
	 console.log(lock);
	  
	 
	  setTimeout(function() {
	    $(".alert").fadeOut();           
	  },3000);

	  if(enable === 'true'){
		toastr.success("Empleado HABILITADO, exitosamente."); 
	  }
	  if(enable === 'false'){
		toastr.success("Empleado DESHABILITADO, exitosamente."); 
	  }
	  
	  if(lock === 'true'){
		  console.log("entre");
		  toastr.success("Usuario bloqueado"); 
	  }
	  if(lock === 'false'){
		  toastr.error("No se ha podido bloquear el usuario. Por favor intente de nuevo."); 
	  }
	  if(unlock === 'true'){
		  toastr.success("Usuario desbloqueado"); 
	  }
	  if(unlock === 'false'){
		  toastr.error("No se ha podido desbloquear el usuario. Por favor intente de nuevo.");
	  }
})