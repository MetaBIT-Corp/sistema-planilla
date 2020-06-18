package com.metabit.planilla.controller;

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
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(INDEX_VIEW);
		
		mav.addObject("rangos_renta", rangoRentaService.getAllRangosRenta());
		
		return mav;
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
		
		if (bindingResult.hasErrors()) {
			redirAttrs.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/rango-renta/edit/" + rango_renta.getIdRangoRenta();
		}
		
		RangoRenta rg = rangoRentaService.getOne(rango_renta.getIdRangoRenta());
		
		rg.setCuotaFija(rango_renta.getCuotaFija());
		rg.setExceso(rango_renta.getExceso());
		rg.setPorcentajeRenta(rango_renta.getPorcentajeRenta());
		rg.setSalarioMax(rango_renta.getSalarioMax());
		rg.setSalarioMin(rango_renta.getSalarioMin());
		
		rangoRentaService.update(rg);
		
		return "redirect:/rango-renta/index";
	}
}
