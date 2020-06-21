package com.metabit.planilla.controller;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.planilla.entity.DiaFestivo;
import com.metabit.planilla.entity.Periodo;
import com.metabit.planilla.entity.Puesto;
import com.metabit.planilla.service.DiaFestivoService;
import com.metabit.planilla.service.PeriodoService;
import com.metabit.planilla.service.impl.PeriodoServiceImpl;

@Controller
@RequestMapping("/dia-festivo")
public class DiaFestivoController {
	private static final String INDEX_VIEW = "dia-festivo/index";

	@Autowired
	@Qualifier("diaFestivoServiceImpl")
	DiaFestivoService diaFestivoService;

	@Autowired
	@Qualifier("periodoServiceImpl")
	PeriodoService periodoService;

	// LOG
	private static final Log LOGGER = LogFactory.getLog(DiaFestivoController.class);

	/**
	 * Método para visualizar el calendario de días festivos
	 * @author Edwin Palacios
	 * @param String *_success: permite mostrar los toast-mensajes según la accion ejecutada anteriormente
	 * @return ModelAndView
	 */
	@PreAuthorize("permitAll()")
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(name = "store_success", required = false) String store_success,
			@RequestParam(name = "update_success", required = false) String update_success,
			@RequestParam(name = "delete_success", required = false) String delete_success) {
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		mav.addObject("store_success", store_success);
		mav.addObject("update_success", update_success);
		mav.addObject("delete_success", delete_success);
		//se valida de que exista un periodo
		if(periodoService.getPeriodoActivo() != null) {
			mav.addObject("fecha_inicio", periodoService.getPeriodoActivo().getFechaInicio());
			mav.addObject("fecha_pago", periodoService.getPeriodoActivo().getFechaFinal());
			//sumamos 1 a la fecha final para que se muestre todo el rango sombreado en el mapa, si no lo hacemos no se sombrea el ultimo dia
			mav.addObject("fecha_final", sumarRestarDiasFecha(periodoService.getPeriodoActivo().getFechaFinal(),1));
		}
		return mav;
	}

	/**
	 * Método para recibir el post del formulario de crear/editar día festivo
	 * 
	 * @author Edwin Palacios
	 * @param RequestParam de atributos de dia festivo
	 * @return String
	 */
	@PreAuthorize("permitAll()")
	@PostMapping("/form-post")
	public String createUpdatePost(@RequestParam("idDiaFestivo") int idDiaFestivo, @RequestParam("mes") int mes,
			@RequestParam("dia") int dia, @RequestParam("descripcionDiaFestivo") String descripcionDiaFestivo) {
		if (descripcionDiaFestivo != "") {
			DiaFestivo diaFestivo = new DiaFestivo();
			diaFestivo.setDiaDescripcion(descripcionDiaFestivo);
			diaFestivo.setMes(mes);
			diaFestivo.setDia(dia);
			// Si es cero significa que se está creando
			if (idDiaFestivo == 0) {
				diaFestivoService.storeDiaFestivo(diaFestivo);
				return "redirect:/dia-festivo/index?store_success=true";
			} else {
				diaFestivo.setIdDiaFestivo(idDiaFestivo);
				diaFestivoService.updateDiaFestivo(diaFestivo);
				return "redirect:/dia-festivo/index?update_success=true";
			}
		} else {
			return "redirect:/dia-festivo/index?store_success=false";
		}
	}

	/**
	 * Metodo que permite eliminar un dia festivo
	 * 
	 * @author Edwin Palacios
	 * @param int id: id del dia festivo
	 * @return String
	 */
	@PreAuthorize("permitAll()")
	@PostMapping("/destroy")
	public String destroyPuesto(@RequestParam("idDiaFestivoEliminar") int idDiaFestivo) {
		// obtenemos el dia festivo para verificar que no este asignado a ningun
		// empleado
		DiaFestivo diaFestivo = diaFestivoService.getDiaFestivo(idDiaFestivo);
		if (diaFestivo.getPlanillaDiaFestivo().size() > 0) {
			return "redirect:/dia-festivo/index?delete_success=false";
		} else {
			diaFestivoService.deleteDiaFestivo(idDiaFestivo);
			return "redirect:/dia-festivo/index?delete_success=true";
		}
	}

	public LocalDate sumarRestarDiasFecha(LocalDate localDate, int dias) {
		//default time zone
		ZoneId defaultZoneId = ZoneId.systemDefault();	
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, dias); // numero de días a añadir, o restar en caso de días<0
		return convertToLocalDateViaInstant(calendar.getTime()); // Devuelve el objeto Date con los nuevos días añadidos
	}
	
	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
}
