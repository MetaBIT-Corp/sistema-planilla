package com.metabit.planilla.controller;

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
import com.metabit.planilla.entity.Puesto;
import com.metabit.planilla.service.DiaFestivoService;

@Controller
@RequestMapping("/dia-festivo")
public class DiaFestivoController {
	private static final String INDEX_VIEW = "dia-festivo/index";
	
	@Autowired
	@Qualifier("diaFestivoServiceImpl")
	DiaFestivoService diaFestivoService;
	
	/**
	 * Método para visualizar el calendario de días festivos
	 * @author Edwin Palacios
	 * @param String *_success: permite mostrar los toast-mensajes según la accion ejecutada anteriormente 
	 * @return ModelAndView
	 */
	@PreAuthorize("permitAll()")
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		return mav;
	}
	
	/**
 	 * Método para recibir el post del formulario de crear/editar día festivo
 	 * @author Edwin Palacios
 	 * @param  RequestParam de atributos de dia festivo
 	 * @return String
 	 */
	@PreAuthorize("permitAll()")
	@PostMapping("/form-post")
	public String createUpdatePost(
			@RequestParam("idDiaFestivo") int idDiaFestivo,
			@RequestParam("mes") int mes,
			@RequestParam("dia") int dia,
			@RequestParam("descripcionDiaFestivo") String descripcionDiaFestivo) {
		if(descripcionDiaFestivo != null) {
			DiaFestivo diaFestivo = new DiaFestivo();
			diaFestivo.setDiaDescripcion(descripcionDiaFestivo);
			diaFestivo.setMes(mes);
			diaFestivo.setDia(dia);
			//Si es cero significa que se está creando
			if(idDiaFestivo == 0) {
				diaFestivoService.storeDiaFestivo(diaFestivo);
				return "redirect:/dia-festivo/index?store_success=true";
			}else {
				diaFestivo.setIdDiaFestivo(idDiaFestivo);
				diaFestivoService.updateDiaFestivo(diaFestivo);
				return "redirect:/dia-festivo/index?update_success=true";
			}
		}else {
			return "redirect:/dia-festivo/index?store_success=false";
		}
	}
	
	/**
 	 * Metodo que permite eliminar un dia festivo
 	 * @author Edwin Palacios
 	 * @param int id: id del dia festivo
 	 * @return String
 	 */
	@PreAuthorize("permiteAll()")
	@PostMapping("/destroy")
	public String destroyPuesto(@RequestParam("idDiaFestivo") int idDiaFestivo) {
		//obtenemos el dia festivo para verificar que no este asignado a ningun empleado
		DiaFestivo diaFestivo = diaFestivoService.getDiaFestivo(idDiaFestivo); 
		if(diaFestivo.getPlanillaDiaFestivo().size() > 0) {
			return "redirect:/dia-festivo/index?delete_success=false";
		}else {
			diaFestivoService.deleteDiaFestivo(idDiaFestivo);
			return "redirect:/dia-festivo/index?delete_success=true";
		}
	}
}
