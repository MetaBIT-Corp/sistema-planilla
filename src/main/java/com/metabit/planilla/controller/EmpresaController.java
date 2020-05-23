package com.metabit.planilla.controller;

import com.metabit.planilla.service.*;
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

import com.metabit.planilla.entity.Empresa;
import com.metabit.planilla.entity.Municipio;

@Controller
@RequestMapping("/empresa/perfil")
public class EmpresaController {

	@Autowired
	@Qualifier("empresaServiceImpl")
	private EmpresaService empresaService;
	
	@Autowired
	@Qualifier("departamentoServiceImpl")
	private DepartamentoService departmentoService;
	
	@Autowired
	@Qualifier("municipioServiceImpl")
	private MunicipioService municipioService;
	
	@Autowired
	@Qualifier("direccionServiceImpl")
	private DireccionService direccionService;

	@PreAuthorize("hasAuthority('EMPRESA_SHOW')")
	@GetMapping("/show")
	public String show(Model model, @RequestParam(name="update_success", required=false) String update_success) {
		model.addAttribute("update_success", update_success);
		System.out.println(empresaService.getEmpresa());
		model.addAttribute("empresaEntity", empresaService.getEmpresa());
		return "empresa/show";
	}
	
	@PreAuthorize("hasAuthority('EMPRESA_EDIT')")
	@GetMapping("/edit")
	public String edit(Model model) {
		model.addAttribute("departamentos", departmentoService.getAllDepartamentos());
		model.addAttribute("municipios", municipioService.getMunicipiosByDepartamento(empresaService.getEmpresa().getDireccion().getMunicipio().getDepartamento()));
		model.addAttribute("empresaEntity", empresaService.getEmpresa());
		return "empresa/edit";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute(name="empresaEntity") Empresa empresa, @RequestParam("municipio") int idMunicipio) {
		empresa.getDireccion().setIdDireccion(empresaService.getEmpresa().getDireccion().getIdDireccion());
		Municipio municipio_seleccionado = municipioService.getMunicipio(idMunicipio);
		empresa.getDireccion().setMunicipio(municipio_seleccionado);
		
		empresa.setDireccion(direccionService.updateDirection(empresa.getDireccion()));
		empresaService.updateEmpresa(empresa);
		return "redirect:/empresa/perfil/show?update_success";
	}
}
