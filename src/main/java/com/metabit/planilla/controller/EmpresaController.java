package com.metabit.planilla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metabit.planilla.service.EmpresaService;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

	@Autowired
	@Qualifier("empresaServiceImpl")
	private EmpresaService empresaService;
	
	@GetMapping("/show")
	public String show(Model model) {
		model.addAttribute("empresa", empresaService.getEmpresa());
		System.out.println("Calle:" + empresaService.getEmpresa().getDireccion().getCalle());
		return "empresa/show";
	}
}
