$(function () {
	
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
				start: '2020-06-01',
				end: '2020-07-01',
				overlap: false,
				rendering: 'background'
		  	}
		diasFestivosList.push(periodo);
		
		fetch(window.location.origin + '/api/dias-festivos')
		.then(response => response.json())
		.then(data => {
			console.log(data);
			for(var i=0; i < data.length; ++i){
				console.log(data[i].idDiaFestivo);
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
		console.log(diasFestivosList)
		var calendar = new Calendar(calendarEl, {
			  plugins: [ 'bootstrap', 'interaction', 'dayGrid', 'timeGrid' ],
			  header    : {
			    left  : 'prev,next today',
			    center: 'title',
			    right : 'dayGridMonth,timeGridWeek,timeGridDay'
			  },
			  'themeSystem': 'bootstrap', 
			  events: diasFestivosList,
			  dateClick: function(info) {
				  //Se creará un nuevo dia festivo
				  document.getElementById("divFecha").innerHTML = "<h5>Fecha: <b>" + info.dateStr + "</b></h5>";
				  document.getElementById("modalTitulo").innerHTML = "Nuevo Día Festivo";
				  var elementosFecha = info.dateStr.split("-");
				  console.log(elementosFecha);
				  $("#mes").val(elementosFecha[1]); 
				  $("#dia").val(elementosFecha[2]); 
				  $("#diaFestivoModal").modal();
			  },
			  eventClick: function(info) {
				//Se edita dia festivo
				if(info.event.title != ' ' && info.view.type === 'dayGridMonth'){
					document.getElementById("divFecha").innerHTML = `<h5>Fecha: <b>  ${y}-${info.event.extendedProps.mes}-${info.event.extendedProps.dia}</b></h5>`;
					document.getElementById("modalTitulo").innerHTML = "Día Festivo";
					$("#descripcionDiaFestivo").val(info.event.title);
					$("#mes").val(info.event.extendedProps.mes); 
					$("#dia").val(info.event.extendedProps.dia);
					$("#idDiaFestivo").val(info.event.extendedProps.id);
			    	$("#diaFestivoModal").modal();
			    }
				  
			    
			  }
			  
			});
			
		calendar.render();
	}
})