package com.metabit.planilla.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metabit.planilla.entity.TipoUnidadOrganizacional;
import com.metabit.planilla.service.TipoUnidadOrganizacionalService;

@Controller
@RequestMapping("/tipo-unidad-organizacional")
public class TipoUnidadOrganizacionalController {

	private static final String INDEX_VIEW = "tipo-unidad-organizacional/index";
	
	@Autowired
	@Qualifier("tipoUnidadOrganizacionalServiceImpl")
	private TipoUnidadOrganizacionalService tipoUnidadOrganizacionalService;
	
	@PreAuthorize("hasAuthority('TIPOUNIDADORGANIZACIONAL_INDEX')")
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		
		mav.addObject("tipos_unidad_organizacional", tipoUnidadOrganizacionalService.getAll());
		mav.addObject("tipo_unidad_organizacional", new TipoUnidadOrganizacional());
		
		return mav;
	}
	
	@PreAuthorize("hasAuthority('TIPOUNIDADORGANIZACIONAL_CREATE')")
	@PostMapping("/store")
	public String store(@Valid @ModelAttribute("tipo_unidad_organizacional") TipoUnidadOrganizacional tuo, 
			BindingResult bindingResult, RedirectAttributes redirAttrs) {
			TipoUnidadOrganizacional tuo_nivel = tipoUnidadOrganizacionalService.getByNivelJerarquico(tuo.getNivelJerarquico());
			
			if(bindingResult.hasErrors() || tuo_nivel != null) {
				if(tuo_nivel != null) {
					bindingResult.reject("nivel_duplicado", "Ya existe un tipo de unidad organizacional con este nivel jerarquico.");
				}
				redirAttrs.addFlashAttribute("errors", bindingResult.getAllErrors());
				return "redirect:/tipo-unidad-organizacional/index";
			}
			
			tuo.setTipoUnidadOrganizacionalHabilitado(true);
			tipoUnidadOrganizacionalService.store(tuo);
			
			redirAttrs.addFlashAttribute("success_store", "success");
			return "redirect:/tipo-unidad-organizacional/index";
	}
	
	@PreAuthorize("hasAuthority('TIPOUNIDADORGANIZACIONAL_EDIT')")
	@PostMapping("/update")
	public String update(@RequestParam Map<String,String> requestParams, RedirectAttributes redirAttrs) throws Exception {
		List<String> errors = new ArrayList();
		int id_tuo = Integer.parseInt(requestParams.get("id-edit"));
		int nivel_tuo = Integer.parseInt(requestParams.get("nivel"));
		String tipo = requestParams.get("tipo");
		TipoUnidadOrganizacional nivel_verify = tipoUnidadOrganizacionalService.getByNivelJerarquico(nivel_tuo);
		
		if(nivel_tuo < 1 || tipo == "" || tipo == null || nivel_verify != null) {
			if(nivel_tuo < 1) {
				errors.add("El nivel jerarquico debe ser igual o mayor a 1");
			}
			if(tipo == "" || tipo == null) {
				errors.add("Debe ingresar el nombre del tipo de unidad a crear");
			}
			if(nivel_verify != null && id_tuo != nivel_verify.getIdTipoUnidadOrganizacional()) {
				errors.add("Ya existe un tipo de unidad organizacional con este nivel jerarquico.");
			}
			
			redirAttrs.addFlashAttribute("errors_edit", errors);
			return "redirect:/tipo-unidad-organizacional/index";
		}
		
		TipoUnidadOrganizacional tuo = tipoUnidadOrganizacionalService.getOne(id_tuo);
		tuo.setNivelJerarquico(nivel_tuo);
		tuo.setTipoUnidadOrganizacional(tipo);
		tipoUnidadOrganizacionalService.update(tuo);
		
		redirAttrs.addFlashAttribute("success_update", "success");
		return "redirect:/tipo-unidad-organizacional/index";
	}
	
	@PreAuthorize("hasAuthority('TIPOUNIDADORGANIZACIONAL_DELETE')")
	@PostMapping("/destroy")
	public String destroy(@RequestParam Map<String,String> requestParams, RedirectAttributes redirAttrs) {
		
		int id_tuo = Integer.parseInt(requestParams.get("id-tuo"));
		boolean state = Boolean.parseBoolean(requestParams.get("state"));
		TipoUnidadOrganizacional tuo = tipoUnidadOrganizacionalService.getOne(id_tuo);

		tuo.setTipoUnidadOrganizacionalHabilitado(state);
		tipoUnidadOrganizacionalService.update(tuo);
		
		redirAttrs.addFlashAttribute("success_state", String.valueOf(state));
		return "redirect:/tipo-unidad-organizacional/index";
	}
}
