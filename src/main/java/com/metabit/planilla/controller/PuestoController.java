package com.metabit.planilla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.planilla.service.PuestoService;

@Controller
@RequestMapping("/planilla/puesto")
public class PuestoController {
	
	@Autowired
	@Qualifier("puestoServiceImpl")
	private PuestoService puestoService;
	
	private static final String INDEX_VIEW = "puesto/index";
	private static final String EDIT_VIEW = "puesto/edit";
	private static final String CREATE_VIEW = "puesto/create";
	
	//listar
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(INDEX_VIEW); 
		mav.addObject("puestos", puestoService.getPuestos());
		return mav;
	}
	
	//editar (GET)
	@GetMapping("/edit")
	public ModelAndView edit() {
		ModelAndView mav = new ModelAndView(EDIT_VIEW); 
		return mav;
	}

	//Crear (GET)
	@GetMapping("/create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView(CREATE_VIEW); 
		return mav;
	}

	
}
