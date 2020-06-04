package com.metabit.planilla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metabit.planilla.entity.Cuota;
import com.metabit.planilla.entity.Plan;
import com.metabit.planilla.service.CuotaService;
import com.metabit.planilla.service.PlanService;

@Controller
@RequestMapping("/cuota")
public class CuotaController {

	private static String INDEX_VIEW = "cuota/index";
	
	@Autowired
	@Qualifier("planServiceImpl")
	private PlanService planService;
	
	@Autowired
	@Qualifier("cuotaServiceImpl")
	private CuotaService cuotaService;
	
	@GetMapping("/index")
	public String index(Model model, @RequestParam(value = "idPlan", required = true) int idPlan) {
		Plan plan = planService.getPlan(idPlan);
		List<Cuota> cuotas = cuotaService.getAllCuotasByPlan(plan);
		model.addAttribute("cuotas", cuotas);
		model.addAttribute("idEmpleado", plan.getEmpleado().getIdEmpleado());
		
		return INDEX_VIEW;
	}
}
