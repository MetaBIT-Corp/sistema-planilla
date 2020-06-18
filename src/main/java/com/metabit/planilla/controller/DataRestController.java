package com.metabit.planilla.controller;

import java.util.ArrayList;
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
import com.metabit.planilla.entity.EmpleadosPuestosUnidades;
import com.metabit.planilla.entity.Municipio;
import com.metabit.planilla.entity.Planilla;
import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.UnidadOrganizacional;
import com.metabit.planilla.service.DepartamentoService;
import com.metabit.planilla.service.MunicipioService;
import com.metabit.planilla.service.RolService;
import com.metabit.planilla.service.UnidadOrganizacionalService;

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
		List<UnidadOrganizacional> unidadesAll = unidadOrganizacionalService.getAllUnidadesOrganizacionales();
		List<UnidadOrganizacional> unidadesSinPagar = new ArrayList<UnidadOrganizacional>();
		Boolean unidadPagada = true;
		
		for(int i = 0; i < unidadesAll.size(); i++) {
			unidadPagada = true;
			List<EmpleadosPuestosUnidades> epu = unidadesAll.get(i).getEmpleadosPuestosUnidades();
			for(int j = 0; j < epu.size(); j++) {
				List<Planilla> planillas = epu.get(j).getEmpleado().getPlanillasEmpleado();
				for(int k = 0; k < planillas.size(); k++) {
					if(planillas.get(k).getFechaEmision() == null) unidadPagada = false;
				}
			}
			if(!unidadPagada) {
				unidadesSinPagar.add(unidadesAll.get(i));
			}
		}
		return unidadesSinPagar;
	}
}
