package com.metabit.planilla.controller;

import java.time.LocalDate;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.planilla.service.AnioLaboralService;

@Controller
@RequestMapping("/anio-laboral")
public class AnioLaboralController {
	
	@Autowired
	@Qualifier("anioLaboralServiceImpl")
	private AnioLaboralService anioLaboralService;
	
	private static final String INDEX = "anio-laboral/index";
	
	@GetMapping(path = {"/index","/index/{year}"})
	public ModelAndView index(@PathVariable(value = "year", required=false) Integer year) {
		ModelAndView mav = new ModelAndView(INDEX);
		
		if(year==null) {
			year=Calendar.getInstance().get(Calendar.YEAR);
		}
		
		mav.addObject("years", anioLaboralService.getAllAniosLaborales());
		mav.addObject("selected_year", anioLaboralService.getAnioLaboral(year));
		mav.addObject("local_date", LocalDate.now());
		
		return mav;
	}
}
