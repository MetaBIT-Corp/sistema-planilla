package com.metabit.planilla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.planilla.service.RangoRentaService;

@Controller
@RequestMapping("/rango-renta")
public class RangoRentaController {

	@Autowired
	@Qualifier("rangoRentaServiceImpl")
	private RangoRentaService rangoRentaService;
	
	private static final String INDEX_VIEW = "rango-renta/index";
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		
		mav.addObject("rangos_renta", rangoRentaService.getAllRangosRenta());
		
		return mav;
	}
}
