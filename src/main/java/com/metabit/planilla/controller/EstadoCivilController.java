package com.metabit.planilla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EstadoCivil;
import com.metabit.planilla.service.EmpleadoService;
import com.metabit.planilla.service.EstadoCivilService;

@Controller
@RequestMapping("/estados-civiles")
public class EstadoCivilController {

	private static String INDEX_VIEW = "estado-civil/index";
	@Autowired
	@Qualifier("estadoCivilServiceImpl")
	private EstadoCivilService estadoCivilService;
	
	@Autowired
	@Qualifier("empleadoServiceImpl")
	private EmpleadoService empleadoService;
	
	@GetMapping("/index")
	public String index(Model model, @RequestParam(name = "store_success", required = false) String store_success,
			@RequestParam(name = "store_restricted", required = false) String store_restricted,
			@RequestParam(name = "update_success", required = false) String update_success,
			@RequestParam(name = "update_restricted", required = false) String update_restricted,
			@RequestParam(name = "delete_success", required = false) String delete_success,
			@RequestParam(name = "delete_restricted", required = false) String delete_restricted) {
		
		List<EstadoCivil> estadosCiviles = estadoCivilService.getAllCivilStates();
		model.addAttribute("estadosCiviles", estadosCiviles);
		model.addAttribute("estadoCivilEntity", new EstadoCivil());
		model.addAttribute("store_success", store_success);
		model.addAttribute("store_restricted", store_restricted);
		model.addAttribute("update_success", update_success);
		model.addAttribute("update_restricted", update_restricted);
		model.addAttribute("delete_success", delete_success);
		model.addAttribute("delete_restricted", delete_restricted);
		
		return INDEX_VIEW;
	}
	
	@PostMapping("/store")
	public String store(@ModelAttribute("estadoCivilEntity") EstadoCivil estadoCivil) {
		
		//Validamos que el Estado Civil no sea espacios en blanco
		if (!(estadoCivil.getEstadoCivil().trim().length() > 0))
			return "redirect:/estados-civiles/index?store_restricted";
		
		//Guardamos el Estado Civil
		estadoCivilService.addCivilState(estadoCivil);
		
		//Retornando al listado con la variable de exito en el almacenamiento
		return "redirect:/estados-civiles/index?store_success";
	}
	
	@PostMapping("/update")
	public String update(@RequestParam(name = "idEstadoCivil", required = true) int idEstadoCivil,
			@RequestParam(name = "estadoCivil", required = true) String estadoCivilStr) {
		
		//Validamos que el Estado Civil no sea espacios en blanco
		if (!(estadoCivilStr.trim().length() > 0))
			return "redirect:/estados-civiles/index?update_restricted";
				
		//Obtenemos el Estado Civil
		EstadoCivil estadoCivil = estadoCivilService.getCivilState(idEstadoCivil);
		
		//Actualizamos su nombre
		estadoCivil.setEstadoCivil(estadoCivilStr);
		
		//Procedemos a guardar los cambios en la BD
		estadoCivilService.updateCivilState(estadoCivil);
		
		//Retornando al listado con la variable de exito en la edicion
		return "redirect:/estados-civiles/index?update_success";
	}
	
	@PostMapping("/destroy")
	public String destroy(@RequestParam(name = "idEstadoCivilDestroy", required = true) int idEstadoCivil) {
		
		//Obtenemos el Estado Civil que se desea Eliminar
		EstadoCivil estadoCivil = estadoCivilService.getCivilState(idEstadoCivil);
		
		//Obtendremos la lista de Empleados que poseen este Estado Civil
		List<Empleado> empleadosEstadoCivil = empleadoService.findByEstadoCivil(estadoCivil);
		
		//Comprobaremos si hay empleados que poseen este Estado Civil
		//Si hay Empleados, entonces, no se permitira Eliminar el Estado Civil
		if(empleadosEstadoCivil.size() > 0)
			return "redirect:/estados-civiles/index?delete_restricted";
		
		//En caso contrario, se procede a Eliminar
		estadoCivilService.deleteEstadoCivil(idEstadoCivil);
		
		//Retornando al listado con la variable de exito en la Eliminacion
		return "redirect:/estados-civiles/index?delete_success";
	}
}
