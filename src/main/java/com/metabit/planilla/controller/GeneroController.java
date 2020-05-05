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

import com.metabit.planilla.entity.Genero;
import com.metabit.planilla.service.EmpleadoService;
import com.metabit.planilla.service.GeneroService;

@Controller
@RequestMapping("/planilla/genero")
public class GeneroController {
	
	@Autowired
	@Qualifier("generoServiceImpl")
	private GeneroService generoService; 

	@Autowired
	@Qualifier("empleadoServiceImpl")
	private EmpleadoService empleadoService;
	
	//@PreAuthorize("hasAuthority('GENERO_INDEX')")
	@GetMapping("/index")
	private String index(Model model, @RequestParam(name="store_success", required=false) String store_success, 
			@RequestParam(name="update_success", required=false) String update_success,
			@RequestParam(name="delete_success", required=false) String delete_success,
			@RequestParam(name="delete_restricted", required=false) String delete_restricted) {
		
		//Recibiendo el posible param de Exito en actualizacion
		model.addAttribute("update_success", update_success);
		
		//Recibiendo el posible param de Exito en el almacenamiento
		model.addAttribute("store_success", store_success);
		
		//Recibiendo el posible param de Exito en el eliminacion
		model.addAttribute("delete_success", delete_success);
		
		//Recibiendo el posible param de Restriccion en la eliminacion
		model.addAttribute("delete_restricted", delete_restricted);
		
		//Mandando el objeto de Genero, el cual se utilizara en caso de insertar uno nuevo con el formulario respectivo
		model.addAttribute("generoEntity", new Genero());
		
		//Mandando el listado de Generos
		model.addAttribute("generos", generoService.getAllGeneros());
		
		//Retornar a la vista del listado de Generos
		return "genero/index";
	}
	
	@PostMapping("/store")
	private String store(@ModelAttribute(name="generoEntity") Genero genero) {
		
		//Obteniendo el objeto de Genero del formulario para creacion de Genero
		generoService.addGenero(genero);
		
		//Retornando al listado con la variable de exito en el almacenamiento
		return "redirect:/planilla/genero/index?store_success";
	}
	
	@PostMapping("/update")
	private String update(@RequestParam("idGenero") int idGenero, @RequestParam("genero") String genero) {
		
		//Obteniendo el Genero a ser actualizado
		Genero generoSelected = generoService.getGenero(idGenero);
		//Cambiamos el atributo de genero, con el nuevo brindado en el formulario de actualizacion
		generoSelected.setGenero(genero);
		
		//Guardando el objeto de Genero actualizado, con el Repository
		generoService.updateGenero(generoSelected);
		
		//Retornando al listado con la variable de exito en actualizacion
		return "redirect:/planilla/genero/index?update_success";
	}
	
	@PostMapping("/destroy")
	private String destroy(@RequestParam("idGeneroDestroy") int idGenero) {
		//Validacion, si hay empleados con este genero, no se debe eliminar.
		Genero genero_selected = generoService.getGenero(idGenero);
		
		//Obtenemos la cantidad de empleados con este genero
		int cant_empleados = empleadoService.findByGenero(genero_selected).size();
	
		//Si la cantidad es mayor a cero, significa que no podemos eliminar el genero
		if(cant_empleados > 0 )
			return "redirect:/planilla/genero/index?delete_restricted";
		
		//eliminar el genero por el idGenero
		generoService.deleteGenero(idGenero);
		
		return "redirect:/planilla/genero/index?delete_success";
	}
}
