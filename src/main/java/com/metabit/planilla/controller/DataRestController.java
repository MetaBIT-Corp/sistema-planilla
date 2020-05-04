package com.metabit.planilla.controller;

import java.util.List;

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
import com.metabit.planilla.service.DepartamentoService;
import com.metabit.planilla.service.MunicipioService;

@RestController
@RequestMapping("/api")
public class DataRestController {

	@Autowired
	@Qualifier("departamentoServiceImpl")
	private DepartamentoService departmentoService;
	
	@Autowired
	@Qualifier("municipioServiceImpl")
	private MunicipioService municipioService;
	
	@GetMapping("/municipios/{idDepartamento}")
	public List<Municipio> getMunicipiosByDepto(@PathVariable("idDepartamento") int idDepartamento){
		Departamento depto = departmentoService.getDepartamento(idDepartamento);
		return municipioService.getMunicipiosByDepartamento(depto);
	}
}
