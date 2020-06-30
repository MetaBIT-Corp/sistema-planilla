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
import com.metabit.planilla.entity.TipoMovimiento;
import com.metabit.planilla.service.TipoMovimientoService;

@Controller
@RequestMapping("/tipo-movimiento")
public class TipoMovimientoController {
	
	@Autowired
	@Qualifier("tipoMovimientoServiceImpl")
	private TipoMovimientoService tipoMovimientoService;
	
	private static final String INDEX_VIEW = "tipo-movimiento/index";
	private static final String CREATE_VIEW = "tipo-movimiento/create";
	
	// LOG
	private static final Log LOGGER = LogFactory.getLog(TipoMovimientoController.class);
	
	/**
	 * Método para listar los tipos de movimiento 
	 * @author Edwin Palacios
	 * @param Model model: permite enviar variables al contexto, 
	 * @param String *_success: permite mostrar los toast-mensajes según la accion ejecutada anteriormente 
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAuthority('TIPOMOVIMIENTO_INDEX')")
	@GetMapping("/index")
	public ModelAndView index(Model model,
			@RequestParam(name="store_success", required=false) String store_success, 
			@RequestParam(name="update_success", required=false) String update_success,
			@RequestParam(name="delete_success", required=false) String delete_success,
			@RequestParam(name="enable_success", required=false) String enable_success,
			@RequestParam(name="disable_success", required=false) String disable_success
			) {
		ModelAndView mav = new ModelAndView(INDEX_VIEW); 
		mav.addObject("tiposMovimiento", tipoMovimientoService.getTiposMovimiento());
		model.addAttribute("store_success", store_success);
		model.addAttribute("update_success", update_success);
		model.addAttribute("delete_success", delete_success);
		model.addAttribute("enable_success", enable_success);
		model.addAttribute("disable_success", disable_success);
		return mav;
	}
	
	/**
	 * Método para desplegar el formulario de crear/editar 
	 * @author Edwin Palacios
	 * @param Optional<Integer> id: id del tipo de movimiento. Opcional, utilizado unicamente en el caso de edición
	 * @return ModelAndView
	 */
	@PreAuthorize("hasAuthority('TIPOMOVIMIENTO_CREATE') or hasAuthority('TIPOMOVIMIENTO_EDIT')")
	@GetMapping(path ={"/form-tipo-movimiento" ,"/form-tipo-movimiento/{id}"})
	public ModelAndView createUpdateGet(@PathVariable("id") Optional<Integer> id) {
		ModelAndView mav = new ModelAndView(CREATE_VIEW);
		if(id.isPresent()) {
			//actualizamos 
			Integer idTipoMovimiento = Integer.valueOf(id.get());
			TipoMovimiento tipoMovimiento = tipoMovimientoService.getTipoMovimiento(idTipoMovimiento);
			mav.addObject("tipoMovimientoEntity", tipoMovimiento);
		}else {
			// tipoMovimiento empty para crear un nuevo tipoMovimiento
			mav.addObject("tipoMovimientoEntity", new TipoMovimiento());
		}
		return mav;
	}
	
	/**
	 * Método para recibir el post del formulario de crear/editar 
	 * @author Edwin Palacios
	 * @param modelAttribute: modelo del formulario, Validaciones
	 * @return String
	 */
	@PreAuthorize("hasAuthority('TIPOMOVIMIENTO_CREATE') or hasAuthority('TIPOMOVIMIENTO_EDIT')")
	@PostMapping("/form-post")
	public String createUpdatePost(@Valid @ModelAttribute("tipoMovimientoEntity") TipoMovimiento tipoMovimiento, BindingResult bindingResult) {
		LOGGER.info("TIPO MOVIMIENTO : " + tipoMovimiento);
		if (bindingResult.hasErrors()) {
			//retornamos al layout
			return CREATE_VIEW;
		} else {
			//creamos y retornamos a listado de tipos de movimiento
			if(tipoMovimiento.getIdMovimiento() == 0) {
				tipoMovimiento.setTipoMovimientoHabilitado(true);
				tipoMovimientoService.storeTipoMovimiento(tipoMovimiento);
				return "redirect:/tipo-movimiento/index?store_success=true";
			}else {
				tipoMovimientoService.storeTipoMovimiento(tipoMovimiento);
				return "redirect:/tipo-movimiento/index?update_success=true";
			}
			
		}
	}
	
	/**
 	 * Metodo que permite eliminar un tipo de movimiento
 	 * @author Edwin Palacios
 	 * @param id: id del tipo de movimiento
 	 * @return String
 	 */
	@PreAuthorize("hasAuthority('TIPOMOVIMIENTO_DELETE')")
	@PostMapping("/destroy")
	public String destroyTipoMovimiento(@RequestParam("idTipoMovimientoDestroy") int id) {
		TipoMovimiento tipoMovimiento = tipoMovimientoService.getTipoMovimiento(id);
		//valicación, no se puede eliminar si ha sido asignado a uno o más movimientos
		if(tipoMovimiento.getPlanillaMovimientos().size() > 0) {
			return "redirect:/tipo-movimiento/index?delete_success=false";
		}
		tipoMovimientoService.destroyTipoMovimiento(id);
		return "redirect:/tipo-movimiento/index?delete_success=true";
	}
	
	/**
 	 * Metodo que permite habilitar un tipo de movimiento
 	 * @author Edwin Palacios
 	 * @param id: id del tipo de movimiento
 	 * @return String
 	 */
	@PreAuthorize("hasAuthority('TIPOMOVIMIENTO_EDIT')")
	@PostMapping("/enable")
	public String enableTipoMovimiento(@RequestParam("idTipoMovimientoEnable") int id) {
		TipoMovimiento tipoMovimiento = tipoMovimientoService.getTipoMovimiento(id);
		tipoMovimiento.setTipoMovimientoHabilitado(true);
		tipoMovimientoService.updateTipoMovimiento(tipoMovimiento);
		return "redirect:/tipo-movimiento/index?enable_success=true";
	}
	
	/**
 	 * Metodo que permite deshabilitar un tipo de movimiento
 	 * @author Edwin Palacios
 	 * @param id: id del tipo de movimiento
 	 * @return String
 	 */
	@PreAuthorize("hasAuthority('TIPOMOVIMIENTO_EDIT')")
	@PostMapping("/disable")
	public String disableTipoMovimiento(@RequestParam("idTipoMovimientoDisable") int id) {
		TipoMovimiento tipoMovimiento = tipoMovimientoService.getTipoMovimiento(id);
		tipoMovimiento.setTipoMovimientoHabilitado(false);
		tipoMovimientoService.updateTipoMovimiento(tipoMovimiento);
		return "redirect:/tipo-movimiento/index?disable_success=true";
	}
}
