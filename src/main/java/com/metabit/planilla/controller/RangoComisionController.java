package com.metabit.planilla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metabit.planilla.service.RangoComisionService;

@Controller
@RequestMapping("/rango-comision")
public class RangoComisionController {

	private static String INDEX_VIEW = "rango-comision/index";
	
	@Autowired
	@Qualifier("rangoComisionServiceImpl")
	private RangoComisionService rangoComisionService;
	
	@GetMapping("/index")
	public String index(Model model) {
		
		model.addAttribute("rangosComision", rangoComisionService.getAllRangoComision());
		return INDEX_VIEW;
	}
	
	
	public String destroy(@RequestParam("idRangoComisionDestroy") int idRangoComision) {
		
		rangoComisionService.deleteRangoComision(idRangoComision);
		
		return "redirect: /rango-comision/index?delete_success";
	}
}
