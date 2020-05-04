package com.metabit.planilla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metabit.planilla.entity.Direccion;
import com.metabit.planilla.entity.Empresa;
import com.metabit.planilla.service.DireccionService;
import com.metabit.planilla.service.EmpresaService;

@Controller
@RequestMapping("/empresa/perfil")
public class EmpresaController {

	@Autowired
	@Qualifier("empresaServiceImpl")
	private EmpresaService empresaService;
	
	@Autowired
	@Qualifier("direccionServiceImpl")
	private DireccionService direccionService;
	
	@GetMapping("/show")
	public String show(Model model, @RequestParam(name="update_success", required=false) String update_success) {
		model.addAttribute("update_success", update_success);
		System.out.println(empresaService.getEmpresa());
		model.addAttribute("empresaEntity", empresaService.getEmpresa());
		return "empresa/show";
	}
	
	@GetMapping("/edit")
	public String edit(Model model) {
		model.addAttribute("empresaEntity", empresaService.getEmpresa());
		return "empresa/edit";
	}
	
	@PreAuthorize("permitAll()")
	@PostMapping("/update")
	public String update(@ModelAttribute(name="empresaEntity") Empresa empresa) {
		empresa.getDireccion().setIdDireccion(empresaService.getEmpresa().getDireccion().getIdDireccion());
		
		empresa.setDireccion(direccionService.updateDirection(empresa.getDireccion()));
		empresaService.updateEmpresa(empresa);
		return "redirect:/empresa/perfil/show?update_success";
	}
}
