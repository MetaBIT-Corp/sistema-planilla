$(function () {
	//Fecha del periodo
	var fechaInicio = $("#fechaInicio").val();
	var fechaFinal = $("#fechaFinal").val();
	var fechaPago = $("#fechaPago").val();
	console.log(fechaFinal);
	/* initialize the calendar
	 -----------------------------------------------------------------*/
	//Date for the calendar events (dummy data)
	var date = new Date()
	var d    = date.getDate(),
	    m    = date.getMonth(),
	    y    = date.getFullYear()
	var Calendar = FullCalendar.Calendar;
	var calendarEl = document.getElementById('calendar');
	var diasFestivosList = [];
	
	iniciarCalendar()
	
	function iniciarCalendar(){
		var periodo = {
				start: fechaInicio,
				end: fechaFinal,
				overlap: false,
				rendering: 'background'
		  	}
		var pago = {
				title          : 'Fecha de pago',
				start          : fechaPago,
				backgroundColor: '#00acc1', 
				borderColor    : '#0097a7', 
				extendedProps: {
				    id: 0  
				},
				allDay         : true
		  	}
		diasFestivosList.push(periodo);
		diasFestivosList.push(pago);
		
		fetch(window.location.origin + '/planilla/api/dias-festivos')
		.then(response => response.json())
		.then(data => {
			for(var i=0; i < data.length; ++i){
				var diaFestivo = {
					title          : data[i].diaDescripcion,
					start          : new Date(y, data[i].mes - 1, data[i].dia),
					backgroundColor: '#f56954', //red
					borderColor    : '#f56954', //red
					extendedProps: {
					    id: data[i].idDiaFestivo,
					    dia : data[i].dia,
					    mes: data[i].mes
					},
					allDay         : true
				}
				
				diasFestivosList.push(diaFestivo);
			}
			
			showCalendar(diasFestivosList);	
		});
	}
	
	function showCalendar(diasFestivosList){
		var calendar = new Calendar(calendarEl, {
			  plugins: [ 'bootstrap', 'interaction', 'dayGrid', 'timeGrid' ],
			  header    : {
			    left  : 'prev,next ',
			    center: 'title',
			    right : ' today'
			  },
			  'themeSystem': 'bootstrap', 
			  events: diasFestivosList,
			  dateClick: function(info) {
				  //Se creará un nuevo dia festivo
				  
				  document.getElementById("divFecha").innerHTML = info.dateStr;
				  document.getElementById("modalTitulo").innerHTML = "Nuevo Día Festivo";
				  var elementosFecha = info.dateStr.split("-");
				  $("#mes").val(elementosFecha[1]); 
				  $("#dia").val(elementosFecha[2]); 
				  $("#idDiaFestivo").val(0);
				  
				  $("#btnEliminarDia").hide();
				  $("#diaFestivoModal").modal();
			  },
			  eventClick: function(info) {
				//Se edita dia festivo
				if(info.event.title != ' ' && info.view.type === 'dayGridMonth' && info.event.extendedProps.id !== 0){
					
					document.getElementById("divFecha").innerHTML = `${y}-${info.event.extendedProps.mes}-${info.event.extendedProps.dia}`;
					document.getElementById("modalTitulo").innerHTML = "Día Festivo";
					$("#descripcionDiaFestivo").val(info.event.title);
					$("#mes").val(info.event.extendedProps.mes); 
					$("#dia").val(info.event.extendedProps.dia);
					$("#idDiaFestivo").val(info.event.extendedProps.id);
					
					$("#btnEliminarDia").show();
					$("#idDiaFestivoEliminar").val(info.event.extendedProps.id);
			    	$("#diaFestivoModal").modal();
			    }  
			  }
			});
			
		calendar.render();
	}
})