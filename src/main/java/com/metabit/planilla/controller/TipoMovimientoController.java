package com.metabit.planilla.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	
	//listar tipos movimiento
	@PreAuthorize("hasAuthority('TIPOMOVIMIENTO_INDEX')")
	@GetMapping("/index")
	public ModelAndView index(Model model,
			@RequestParam(name="store_success", required=false) String store_success, 
			@RequestParam(name="update_success", required=false) String update_success,
			@RequestParam(name="delete_success", required=false) String delete_success
			) {
		ModelAndView mav = new ModelAndView(INDEX_VIEW); 
		mav.addObject("tiposMovimiento", tipoMovimientoService.getTiposMovimiento());
		model.addAttribute("store_success", store_success);
		model.addAttribute("update_success", update_success);
		model.addAttribute("delete_success", delete_success);
		return mav;
	}

}
