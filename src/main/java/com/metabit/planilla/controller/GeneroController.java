package com.metabit.planilla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metabit.planilla.entity.Genero;
import com.metabit.planilla.service.GeneroService;

@Controller
@RequestMapping("/generos")
public class GeneroController {

	@Autowired
	@Qualifier("generoServiceImpl")
	private GeneroService generoService;
	
	@PreAuthorize("permitAll()")
	@GetMapping("/index")
	private String index(Model model, @RequestParam(name="store_success", required=false) String store_success) {
		
		model.addAttribute("store_success", store_success);
		model.addAttribute("generoEntity", new Genero());
		model.addAttribute("generos", generoService.getAllGeneros());
		
		return "genero/index";
	}
	
	@PreAuthorize("permitAll()")
	@PostMapping("/store")
	private String store(@ModelAttribute(name="generoEntity") Genero genero) {
		
		generoService.addGenero(genero);
		
		return "redirect:/generos/index?store_success";
	}
	
	@PreAuthorize("permitAll()")
	@PostMapping("/update/{idGenero}/{genero}")
	private String update(@PathVariable("idGenero") int idGenero, @PathVariable("genero") String genero) {
		
		
		return "redirect:/generos/index?update_success";
	}

}
