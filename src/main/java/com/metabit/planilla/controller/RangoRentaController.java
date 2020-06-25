package com.metabit.planilla.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.metabit.planilla.entity.RangoRenta;
import com.metabit.planilla.service.RangoRentaService;

@Controller
@RequestMapping("/rango-renta")
public class RangoRentaController {

	@Autowired
	@Qualifier("rangoRentaServiceImpl")
	private RangoRentaService rangoRentaService;
	
	private static final String INDEX_VIEW = "rango-renta/index";
	private static final String EDIT_VIEW = "rango-renta/edit";
	private static final String CREATE_VIEW = "rango-renta/create";
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		
		mav.addObject("rangos_renta", rangoRentaService.getAllRangosRenta());
		
		return mav;
	}
	
	@GetMapping("/create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView(CREATE_VIEW);
		
		mav.addObject("rango_renta", new RangoRenta());
		
		return mav;
	}
	
	@PostMapping("/store")
	public String store(@Valid @ModelAttribute("rango_renta") RangoRenta rango_renta, BindingResult bindingResult, /*@RequestParam("sin_limite") Integer sinLimite,*/ RedirectAttributes redirAttrs) {
		List<RangoRenta> rangos_renta = rangoRentaService.getByPeriodicidad(rango_renta.getPeriodicidadRenta());
		boolean valid_range = rangesValidate(rangos_renta, rango_renta);
		boolean is_greater = rango_renta.getSalarioMax() > rango_renta.getSalarioMin();
		
		if (bindingResult.hasErrors() || valid_range || !is_greater) {
			if(valid_range) {
				bindingResult.reject("sal_range", "Los salarios ingresados no pueden estar comprendidos dentro de un rango existente");
			}
			if(!is_greater) {
				bindingResult.reject("sal_greater", "El salario mínimo no puede ser mayor o igual al salario máximo");
			}
			redirAttrs.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/rango-renta/create/";
		}
		
		/*if(sinLimite != null) {
			rango_renta.setSalarioMax(0);
		}*/
		
		rango_renta.setRangoRentaHabilitado(true);
		rangoRentaService.store(rango_renta);
		
		redirAttrs.addFlashAttribute("success_store", "success");
		return "redirect:/rango-renta/index";
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable int id) {
		ModelAndView mav = new ModelAndView(EDIT_VIEW);
		
		RangoRenta rango_renta = rangoRentaService.getOne(id);
		
		mav.addObject("rango_renta", rango_renta);
		
		return mav;
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("rango_renta") RangoRenta rango_renta, BindingResult bindingResult, RedirectAttributes redirAttrs) {
		List<RangoRenta> rangos_renta = rangoRentaService.getByPeriodicidad(rango_renta.getPeriodicidadRenta());
		boolean valid_range = rangesValidate(rangos_renta, rango_renta);
		boolean is_greater = rango_renta.getSalarioMax() > rango_renta.getSalarioMin();
		
		if (bindingResult.hasErrors() || valid_range || !is_greater) {
			if(valid_range) {
				bindingResult.reject("sal_range", "Los salarios ingresados no pueden estar comprendidos dentro de un rango existente");
			}
			if(!is_greater) {
				bindingResult.reject("sal_greater", "El salario mínimo no puede ser mayor o igual al salario máximo");
			}
			redirAttrs.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/rango-renta/edit/" + rango_renta.getIdRangoRenta();
		}
		
		RangoRenta rr = rangoRentaService.getOne(rango_renta.getIdRangoRenta());
		
		rr.setCuotaFija(rango_renta.getCuotaFija());
		rr.setExceso(rango_renta.getExceso());
		rr.setPorcentajeRenta(rango_renta.getPorcentajeRenta());
		rr.setSalarioMax(rango_renta.getSalarioMax());
		rr.setSalarioMin(rango_renta.getSalarioMin());
		
		rangoRentaService.update(rr);
		
		redirAttrs.addFlashAttribute("success_update", "success");
		return "redirect:/rango-renta/index";
	}
	
	@PostMapping("/destroy")
	public String destroy(@RequestParam Map<String,String> requestParams, RedirectAttributes redirAttrs) {
		

		int id_rango_renta = Integer.parseInt(requestParams.get("id_rango_renta"));
		boolean state = Boolean.parseBoolean(requestParams.get("state"));
		RangoRenta rango_renta = rangoRentaService.getOne(id_rango_renta);

		rango_renta.setRangoRentaHabilitado(state);
		rangoRentaService.update(rango_renta);
		
		redirAttrs.addFlashAttribute("success_state", String.valueOf(state));
		return "redirect:/rango-renta/index";
	}
	
	public boolean rangesValidate(List<RangoRenta> rangos_renta, RangoRenta rr_new) {
		
		for(RangoRenta rr : rangos_renta) {
			if( (rr_new.getSalarioMin() >= rr.getSalarioMin() && rr_new.getSalarioMin() <= rr.getSalarioMax() || 
					rr_new.getSalarioMax() >= rr.getSalarioMin() && rr_new.getSalarioMax() <= rr.getSalarioMax()) && 
					rr.getIdRangoRenta() != rr_new.getIdRangoRenta()) {
				
				return true;
			}
		}
		
		return false;
	}
}
