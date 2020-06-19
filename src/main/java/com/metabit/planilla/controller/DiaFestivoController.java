package com.metabit.planilla.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dia-festivo")
public class DiaFestivoController {
	private static final String INDEX_VIEW = "dia-festivo/index";
	
	
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

}
