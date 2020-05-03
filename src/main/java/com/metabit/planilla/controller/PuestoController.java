package com.metabit.planilla.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.planilla.entity.Puesto;
import com.metabit.planilla.service.PuestoService;

@Controller
@RequestMapping("/planilla/puesto")
public class PuestoController {
	
	@Autowired
	@Qualifier("puestoServiceImpl")
	private PuestoService puestoService;
	
	private static final String INDEX_VIEW = "puesto/index";
	private static final String CREATE_VIEW = "puesto/create";
	
	// LOG
	private static final Log LOGGER = LogFactory.getLog(PuestoController.class);

	
	//listar puestos
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(INDEX_VIEW); 
		mav.addObject("puestos", puestoService.getPuestos());
		return mav;
	}

	//Desplegar formulario de Crear y editar
	
	@RequestMapping(path = {"/form-puesto", "/form-puesto/{id}"})
	public ModelAndView create(@PathVariable("id") Optional<Integer> id) {
		ModelAndView mav = new ModelAndView(CREATE_VIEW);
		if(id.isPresent()) {
			//actualizamos 
			Integer idPuesto = Integer.valueOf(id.get());
			Puesto puesto = puestoService.getPuesto(idPuesto);
			mav.addObject("puestoEntity", puesto);
		}else {
			// puesto empty para crear un nuevo puesto
			mav.addObject("puestoEntity", new Puesto());
		}
		return mav;
	}
	
	// método para recibir el post del formulario de crear/editar puesto
	
	@PostMapping("/form-post")
	public String createUpdatePost(@Valid @ModelAttribute("puestoEntity") Puesto puesto, BindingResult bindingResult) {
		LOGGER.info("PUESTO: " + puesto);
		if (bindingResult.hasErrors()) {
			//retornamos al layout
			return "/puesto/create";
		} else {
			//creamos y retornamos a listado de puestos
			puestoService.storePuesto(puesto);
			return "redirect:/planilla/puesto/index";
		}
	}

	
}
