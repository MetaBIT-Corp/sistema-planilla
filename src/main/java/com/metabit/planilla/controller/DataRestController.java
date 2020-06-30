package com.metabit.planilla.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metabit.planilla.entity.AnioLaboral;
import com.metabit.planilla.entity.Departamento;
import com.metabit.planilla.entity.DiaFestivo;
import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadosPuestosUnidades;
import com.metabit.planilla.entity.Genero;
import com.metabit.planilla.entity.Municipio;
import com.metabit.planilla.entity.Periodo;
import com.metabit.planilla.entity.Planilla;
import com.metabit.planilla.entity.TipoUnidadOrganizacional;
import com.metabit.planilla.entity.UnidadOrganizacional;
import com.metabit.planilla.entity.Usuario;
import com.metabit.planilla.repository.UserJpaRepository;
import com.metabit.planilla.service.AnioLaboralService;
import com.metabit.planilla.service.DepartamentoService;
import com.metabit.planilla.service.DiaFestivoService;
import com.metabit.planilla.service.EmpleadosPuestosUnidadesService;
import com.metabit.planilla.service.GeneroService;
import com.metabit.planilla.service.MunicipioService;
import com.metabit.planilla.service.PuestoService;
import com.metabit.planilla.service.TipoUnidadOrganizacionalService;
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

	@Autowired
	@Qualifier("empleadosPuestosUnidadesServiceImpl")
	private EmpleadosPuestosUnidadesService empleadosPuestosUnidadesService;

	
	@Autowired
	@Qualifier("diaFestivoServiceImpl")
	private DiaFestivoService diaFestivoService;
	
	@Autowired
    @Qualifier("userJpaRepository")
    private UserJpaRepository userJpaRepository;
	
	@Autowired
	@Qualifier("anioLaboralServiceImpl")
	private AnioLaboralService anioLaboralService;
	
	@Autowired
	@Qualifier("tipoUnidadOrganizacionalServiceImpl")
	private TipoUnidadOrganizacionalService tipoUnidadOrganizacionalService;
	
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
		List<UnidadOrganizacional> unidadesSinPagar = unidadOrganizacionalService.getAllUnidadesOrganizacionalesSinPagar();
		return unidadesSinPagar;
	}

		
	@GetMapping("/dias-festivos")
	public List<DiaFestivo> getDiasFestivos(){
		List<DiaFestivo> diaFestivoList = diaFestivoService.getDiasFestivos();
		return diaFestivoList;
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
	
	@GetMapping("/user/{username}")
	public Usuario getUsername(@PathVariable("username") String username) {
		return userJpaRepository.findByUsername(username);
	}
	
	@GetMapping("/anio-laboral/{id_anio}/periodos")
	public List<Periodo> periodosAnio(@PathVariable("id_anio") Integer id_anio) {
		AnioLaboral anio_laboral = anioLaboralService.getByIdAnioLaboral(id_anio);
		
		return anio_laboral.getPeriodos();
	}
	
	@GetMapping("/tipo-unidad/{id_tipo}/unidades")
	public List<UnidadOrganizacional> unidadesTipoUnidad(@PathVariable("id_tipo") Integer id_tipo){
		TipoUnidadOrganizacional tuo = tipoUnidadOrganizacionalService.getById(id_tipo);
		List<UnidadOrganizacional> uos = new ArrayList<UnidadOrganizacional>();
		
		for (UnidadOrganizacional unidadOrganizacional : tuo.getUnidades_organizacional()) {
			unidadOrganizacional.setUnidadPadre(null);
			uos.add(unidadOrganizacional);
		}
		
		return uos;
	}
}
