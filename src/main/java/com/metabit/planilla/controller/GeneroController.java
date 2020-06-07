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
@RequestMapping("/genero")
public class GeneroController {
	
	@Autowired
	@Qualifier("generoServiceImpl")
	private GeneroService generoService; 

	@Autowired
	@Qualifier("empleadoServiceImpl")
	private EmpleadoService empleadoService;
	
	@PreAuthorize("hasAuthority('GENERO_INDEX')")
	@GetMapping("/index")
	public String index(Model model, @RequestParam(name="store_success", required=false) String store_success, 
			@RequestParam(name="update_success", required=false) String update_success,
			@RequestParam(name="delete_success", required=false) String delete_success,
			@RequestParam(name="delete_restricted", required=false) String delete_restricted,
			@RequestParam(name="update_restricted", required=false) String update_restricted,
			@RequestParam(name="store_restricted", required=false) String store_restricted) {
		
		//Recibiendo el posible param de Exito en actualizacion
		model.addAttribute("update_success", update_success);
		
		//Recibiendo el posible param de Exito en el almacenamiento
		model.addAttribute("store_success", store_success);
		
		//Recibiendo el posible param de Exito en el eliminacion
		model.addAttribute("delete_success", delete_success);
		
		//Recibiendo el posible param de Restriccion en la eliminacion
		model.addAttribute("delete_restricted", delete_restricted);
		
		//Recibiendo el posible param de Restriccion en la update
		model.addAttribute("update_restricted", update_restricted);
		
		//Recibiendo el posible param de Restriccion en la creación
		model.addAttribute("store_restricted", store_restricted);
		
		//Mandando el objeto de Genero, el cual se utilizara en caso de insertar uno nuevo con el formulario respectivo
		model.addAttribute("generoEntity", new Genero());
		
		//Mandando el listado de Generos
		model.addAttribute("generos", generoService.getAllGeneros());
		
		//Retornar a la vista del listado de Generos
		return "genero/index";
	}
	
	@PreAuthorize("hasAuthority('GENERO_CREATE')")
	@PostMapping("/store")
	public String store(@ModelAttribute(name="generoEntity") Genero genero) {
		
		//Validamos que el genero no sea espacios en blanco
		if (!(genero.getGenero().trim().length() > 0))
			return "redirect:/genero/index?store_restricted";
		
		//Obteniendo el objeto de Genero del formulario para creacion de Genero
		generoService.addGenero(genero);
		
		//Retornando al listado con la variable de exito en el almacenamiento
		return "redirect:/genero/index?store_success";
	}

	@PreAuthorize("hasAuthority('GENERO_EDIT')")
	@PostMapping("/update")
	public String update(@RequestParam("idGenero") int idGenero, @RequestParam("genero") String genero) {
		//Validamos que el genero no sea espacios en blanco
		if (!(genero.trim().length() > 0))
			return "redirect:/genero/index?update_restricted";
		
		//Obteniendo el Genero a ser actualizado
		Genero generoSelected = generoService.getGenero(idGenero);
		//Cambiamos el atributo de genero, con el nuevo brindado en el formulario de actualizacion
		generoSelected.setGenero(genero);
		
		//Guardando el objeto de Genero actualizado, con el Repository
		generoService.updateGenero(generoSelected);
		
		//Retornando al listado con la variable de exito en actualizacion
		return "redirect:/genero/index?update_success";
	}

	@PreAuthorize("hasAuthority('GENERO_DELETE')")
	@PostMapping("/destroy")
	public String destroy(@RequestParam("idGeneroDestroy") int idGenero) {
		//Validacion, si hay empleados con este genero, no se debe eliminar.
		Genero genero_selected = generoService.getGenero(idGenero);
		
		//Obtenemos la cantidad de empleados con este genero
		int cant_empleados = empleadoService.findByGenero(genero_selected).size();
	
		//Si la cantidad es mayor a cero, significa que no podemos eliminar el genero
		if(cant_empleados > 0 )
			return "redirect:/genero/index?delete_restricted";
		
		//eliminar el genero por el idGenero
		generoService.deleteGenero(idGenero);
		
		return "redirect:/genero/index?delete_success";
	}
}
