package com.metabit.planilla.controller;

import java.util.ArrayList;
import java.util.List;

import com.metabit.planilla.entity.*;
import com.metabit.planilla.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Autowired
	@Qualifier("unidadOrganizacionalServiceImpl")
	private UnidadOrganizacionalService unidadOrganizacionalService;

	@Autowired
	@Qualifier("empleadosPuestosUnidadesServiceImpl")
	private EmpleadosPuestosUnidadesService empleadosPuestosUnidadesService;

	
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
	
	@GetMapping("/unidades-organizacionales")
	public List<UnidadOrganizacional> getUnidadesOrganizacionales(){
		return unidadOrganizacionalService.getAllUnidadesOrganizacionales();
	}

	@GetMapping("/empleados-unidad/{id}")
	public List<Empleado> getEmpleadosByUnidad(@PathVariable("id") int idUnidad){
		UnidadOrganizacional u = unidadOrganizacionalService.getOneUnidadOrganizacional(idUnidad);
		List<EmpleadosPuestosUnidades> empleadosPuestosUnidades = empleadosPuestosUnidadesService.getAllByUnidadAndPuestoVigente(u);
		List<Empleado> empleadoList = new ArrayList<>();
		for (EmpleadosPuestosUnidades e: empleadosPuestosUnidades) {
			if (e.getEmpleado().getEmpleadoHabilitado()){
				empleadoList.add(e.getEmpleado());
			}
		}
		return empleadoList;
	}
}
