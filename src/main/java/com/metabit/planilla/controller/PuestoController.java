package com.metabit.planilla.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metabit.planilla.entity.Puesto;
import com.metabit.planilla.service.PuestoService;

@Controller
@RequestMapping("/puesto")
public class PuestoController {
	
	@Autowired
	@Qualifier("puestoServiceImpl")
	private PuestoService puestoService;
	
	private static final String INDEX_VIEW = "puesto/index";
	private static final String CREATE_VIEW = "puesto/create";
	
	// LOG
	private static final Log LOGGER = LogFactory.getLog(PuestoController.class);

	
	/**
	 * Método para listar los puestos
	 * @author Edwin Palacios
	 * @param Model model: permite enviar variables al contexto, 
	 * @param String *_success: permite mostrar los toast-mensajes según la accion ejecutada anteriormente 
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAuthority('PUESTO_INDEX')")
	@GetMapping("/index")
	public ModelAndView index(Model model,
			@RequestParam(name="store_success", required=false) String store_success, 
			@RequestParam(name="update_success", required=false) String update_success,
			@RequestParam(name="delete_success", required=false) String delete_success,
			@RequestParam(name="enable_success", required=false) String enable_success,
			@RequestParam(name="disable_success", required=false) String disable_success
			) {
		ModelAndView mav = new ModelAndView(INDEX_VIEW); 
		mav.addObject("puestos", puestoService.getPuestos());
		model.addAttribute("store_success", store_success);
		model.addAttribute("update_success", update_success);
		model.addAttribute("delete_success", delete_success);
		model.addAttribute("enable_success", enable_success);
		model.addAttribute("disable_success", disable_success);
		return mav;
	}
	
	/**
 	 * Método para Desplegar formulario de Crear y editar
 	 * @author Edwin Palacios
 	 * @param Optional<Integer> id: id del objeto puesto a editar
 	 * @return ModelAndView
 	 */
	@PreAuthorize("hasAuthority('PUESTO_CREATE') or hasAuthority('PUESTO_EDIT')")
	@RequestMapping(path = {"/form-puesto", "/form-puesto/{id}"})
	public ModelAndView create(@PathVariable("id") Optional<Integer> id) {
		ModelAndView mav = new ModelAndView(CREATE_VIEW);
		if(id.isPresent()) {
			//actualizamos 
			Integer idPuesto = Integer.valueOf(id.get());
			Puesto puesto = puestoService.getPuesto(idPuesto);
			mav.addObject("puestoEntity", puesto);
		}else {
			// puesto empty para crear un nuevo puesto
			mav.addObject("puestoEntity", new Puesto());
		}
		return mav;
	}
	
	/**
 	 * Método para recibir el post del formulario de crear/editar puesto
 	 * @author Edwin Palacios
 	 * @param ModelAttributte: objeto puesto vinculado al formulario
 	 * @param BindingResult: validaciones aplicadas al objeto
 	 * @return String
 	 */
	@PreAuthorize("hasAuthority('PUESTO_CREATE') or hasAuthority('PUESTO_EDIT')")
	@PostMapping("/form-post")
	public String createUpdatePost(@Valid @ModelAttribute("puestoEntity") Puesto puesto, BindingResult bindingResult) {
		LOGGER.info("PUESTO: " + puesto);
		if (bindingResult.hasErrors()) {
			//retornamos al layout
			return CREATE_VIEW;
		} else {
			//creamos y retornamos a listado de puestos
			if(puesto.getIdPuesto() == 0) {
				puestoService.storePuesto(puesto);
				return "redirect:/puesto/index?store_success=true";
			}else {
				puestoService.updatePuesto(puesto);
				return "redirect:/puesto/index?update_success=true";
			}
			
		}
	}
	
	/**
 	 * Metodo que permite eliminar un puesto
 	 * @author Edwin Palacios
 	 * @param int id: id del puesto
 	 * @return String
 	 */
	@PreAuthorize("hasAuthority('PUESTO_DELETE')")
	@PostMapping("/destroy")
	public String destroyPuesto(@RequestParam("idPuestoDestroy") int id) {
		LOGGER.info("PUESTO: " + id);
		//obtenemos el puesto para verificar que no este asignado a ningun empleado
		Puesto puesto = puestoService.getPuesto(id);
		if(puesto.getEpu().size() > 0) {
			return "redirect:/puesto/index?delete_success=false";
		}else {
			puestoService.destroyPuesto(id);
			return "redirect:/puesto/index?delete_success=true";
		}
	}
	
	
	/**
 	 * Metodo que permite habilitar un puesto
 	 * @author Edwin Palacios
 	 * @param int id: id del puesto
 	 * @return String
 	 */
	@PreAuthorize("permitAll()")
	@PostMapping("/enable")
	public String enablePuesto(@RequestParam("idPuestoEnable") int id) {
		Puesto puesto = puestoService.getPuesto(id);
		puesto.setPuestoHabilitado(true);
		puestoService.updatePuesto(puesto);
		return "redirect:/puesto/index?enable_success=true";
	}
	
	/**
 	 * Metodo que permite deshabilitar un puesto
 	 * @author Edwin Palacios
 	 * @param int id: id del puesto
 	 * @return String
 	 */
	@PreAuthorize("permitAll()")
	@PostMapping("/disable")
	public String disablePuesto(@RequestParam("idPuestoDisable") int id) {
		Puesto puesto = puestoService.getPuesto(id);
		puesto.setPuestoHabilitado(false);
		puestoService.updatePuesto(puesto);
		return "redirect:/puesto/index?disable_success=true";
	}
	
	
}