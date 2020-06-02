package com.metabit.planilla.controller;

import java.util.List;

import com.metabit.planilla.entity.Genero;
import com.metabit.planilla.entity.Puesto;
import com.metabit.planilla.service.GeneroService;
import com.metabit.planilla.service.PuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.metabit.planilla.entity.Departamento;
import com.metabit.planilla.entity.Municipio;
import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.service.DepartamentoService;
import com.metabit.planilla.service.MunicipioService;
import com.metabit.planilla.service.RolService;

@RestController
@RequestMapping("/api")
public class DataRestController {

	@Autowired
	@Qualifier("departamentoServiceImpl")
	private DepartamentoService departmentoService;
	
	@Autowired
	@Qualifier("municipioServiceImpl")
	private MunicipioService municipioService;

	@Autowired
	@Qualifier("generoServiceImpl")
	private GeneroService generoService;

	@Autowired
	@Qualifier("puestoServiceImpl")
	private PuestoService puestoService;
	
	@GetMapping("/municipios/{idDepartamento}")
	public List<Municipio> getMunicipiosByDepto(@PathVariable("idDepartamento") int idDepartamento){
		Departamento depto = departmentoService.getDepartamento(idDepartamento);
		return municipioService.getMunicipiosByDepartamento(depto);
	}
	@GetMapping("/genero/{id}")
	public Genero getGenero(@PathVariable("id") int idGenero){
		return generoService.getGenero(idGenero);
	}

	@GetMapping("/required-user/{id}")
	public Boolean requiredUser(@PathVariable("id") int idPuesto){
		return puestoService.getPuesto(idPuesto).isUsuarioRequerido();
	}
}
