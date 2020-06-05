package com.metabit.planilla.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metabit.planilla.entity.AnioLaboral;
import com.metabit.planilla.service.AnioLaboralService;

@Controller
@RequestMapping("/anio-laboral")
public class AnioLaboralController {
	
	@Autowired
	@Qualifier("anioLaboralServiceImpl")
	private AnioLaboralService anioLaboralService;
	
	private static final String INDEX_VIEW = "anio-laboral/index";
	
	@GetMapping(path = {"/index","/index/{year}"})
	public ModelAndView index(@PathVariable(value = "year", required=false) Integer year) {
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		
		if(year==null) {
			year=Calendar.getInstance().get(Calendar.YEAR);
		}
		
		mav.addObject("years", anioLaboralService.getAllAniosLaborales());
		mav.addObject("selected_year", anioLaboralService.getAnioLaboral(year));
		mav.addObject("local_date", LocalDate.now());
		
		return mav;
	}
	
	@PostMapping("/store")
	public String store(@RequestParam("anioLaboral") int al, @RequestParam("periodicidad") int periodicidad, RedirectAttributes redirAttrs) {
		int current_year = Calendar.getInstance().get(Calendar.YEAR);
		List<String> errors = new ArrayList<String>();
		AnioLaboral anio = anioLaboralService.getAnioLaboral(al);
		
		if(al != current_year || periodicidad != 15 && periodicidad != 30 || anio != null) {
			if(al != current_year) {
				errors.add("El año laboral debe ser el año actual");
			}
			if(periodicidad != 15 && periodicidad != 30) {
				errors.add("La periodicidad seleccionada no es válida");
			}
			if(anio != null) {
				errors.add("Este año laboral ya fue registrado");
			}
			
			redirAttrs.addFlashAttribute("errors", errors);
			return "redirect:/anio-laboral/index";
		}
		
		AnioLaboral anioLaboral = new AnioLaboral();
		anioLaboral.setAnioLaboral(al);
		anioLaboral.setPeriodicidad(periodicidad);
		anioLaboralService.addAnioLaboral(anioLaboral);
		
		redirAttrs.addFlashAttribute("success_anio", "success");
		return "redirect:/anio-laboral/index";
	}
}
