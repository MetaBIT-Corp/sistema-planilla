$(document).ready(function(){
	  var lock = $('#lock_success').val();
	  var unlock = $('#unlock_success').val();
	 console.log(lock);
	  
	 
	  setTimeout(function() {
	    $(".alert").fadeOut();           
	  },3000);
	  
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