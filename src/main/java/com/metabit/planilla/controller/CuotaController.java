package com.metabit.planilla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metabit.planilla.service.CuotaService;

@Controller
@RequestMapping("/cuota")
public class CuotaController {

	private static String INDEX_VIEW = "cuota/index";
	
	@Autowired
	@Qualifier("cuotaServiceImpl")
	private CuotaService cuotaService;
	
	@GetMapping("/index")
	public String index(Model model) {
		return INDEX_VIEW;
	}
}
