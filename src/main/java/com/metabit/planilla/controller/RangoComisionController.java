package com.metabit.planilla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public String index(Model model, @RequestParam(name="delete_success", required=false) String delete_success) {
		
		//Recibiendo el posible param de Exito en la eliminacion
		model.addAttribute("delete_success", delete_success);
		
		//Agregando al modelo el listado de objetos
		model.addAttribute("rangosComision", rangoComisionService.getAllRangoComision());
		
		//System.out.println("Recibiendo: " + rangoComisionService.getAllRangoComision().size());
		return INDEX_VIEW;
	}
	
	@PostMapping("/destroy")
	public String destroy(@RequestParam("idRangoComisionDestroy") int idRangoComision) {
		
		rangoComisionService.deleteRangoComision(idRangoComision);
		
		return "redirect:/rango-comision/index?delete_success";
	}
}
