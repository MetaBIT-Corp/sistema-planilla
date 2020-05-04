$(document).ready(function() {

	$('#salariominimo').keyup(function() {

		var salariominimo = $('#salariominimo').val();
		var salariomaximo = $('#salariomaximo').val();
		
		
		if(salariominimo.length > 0 && salariomaximo.length > 0){
			$('#salariomaximo').attr("min",parseInt(salariominimo));
			
			if ( parseInt(salariominimo)<parseInt(salariomaximo) && parseInt(salariominimo) >= 300) {
				$("#messageError").hide();
			} else {
				$("#messageError").show();
				
				if(parseInt(salariominimo)>parseInt(salariomaximo)){
					$("#mayor").show();
				}else{
					$("#mayor").hide();
				}
				if(parseInt(salariominimo) < 300){
					$("#minimo").show();
				}else{
					$("#minimo").hide();
				}	
			}
		}
	});

	$('#salariomaximo').keyup(function() {

		var salariominimo = $('#salariominimo').val();
		var salariomaximo = $('#salariomaximo').val();

		if(salariominimo.length > 0 && salariomaximo.length > 0){
			if ( parseInt(salariominimo)<parseInt(salariomaximo) && parseInt(salariominimo) >= 300) {
				$("#messageError").hide();
			} else {
				$("#messageError").show();
				
				if(parseInt(salariominimo)>=parseInt(salariomaximo)){
					$("#mayor").show();
				}else{
					$("#mayor").hide();
				}
				if(parseInt(salariominimo) < 300){
					$("#minimo").show();
				}else{
					$("#minimo").hide();
				}	
			}
		}

	});

});