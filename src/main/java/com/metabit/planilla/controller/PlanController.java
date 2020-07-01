package com.metabit.planilla.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metabit.planilla.entity.Cuota;
import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.Plan;
import com.metabit.planilla.service.CuotaService;
import com.metabit.planilla.service.EmpleadoService;
import com.metabit.planilla.service.PlanService;

@Controller
@RequestMapping("/plan")
public class PlanController {

	private static String INDEX_VIEW = "plan/index";
	
	@Autowired
	@Qualifier("planServiceImpl")
	private PlanService planService;
	
	@Autowired
	@Qualifier("empleadoServiceImpl")
	private EmpleadoService empleadoService;
	
	@Autowired
	@Qualifier("cuotaServiceImpl")
	private CuotaService cuotaService;
	
	@PreAuthorize("hasAuthority('PLAN_INDEX')")
	@GetMapping("/index")
	public String index(Model model, @RequestParam(value = "idEmpleado", required = true) int idEmpleado,
			@RequestParam(name="store_success", required=false) String store_success,
			@RequestParam(name="delete_success", required=false) String delete_success,
			@RequestParam(name="delete_failed", required=false) String delete_failed) {
		
		//Recibiendo el posible param de Exito en el almacenamiento
		model.addAttribute("store_success", store_success);
		
		//Recibiendo el posible param de Exito en la eliminacion 
		model.addAttribute("delete_success", delete_success);
		
		//Recibiendo el posible param de Error en la eliminacion 
		model.addAttribute("delete_failed", delete_failed);
				
		//Obtenemos el empleado
		Empleado empleado = empleadoService.findEmployeeById(idEmpleado);
		//Obtenemos los planes del empleado
		List<Plan> planes = planService.getAllPlansByEmpleado(empleado);
		//Agregamos la lista de planes al modelo, para posteriormente mostrarlos en la vista
		model.addAttribute("planes", planes);
		//Agregamos al objeto empleado a la vista
		model.addAttribute("empleado", empleado);
		//Creamos un objeto Plan y le seteamos al empleado
		Plan plan = new Plan();
		plan.setEmpleado(empleado);
		//Agregamos el objeto Plan al model
		model.addAttribute("planEntity", plan);
		
		return INDEX_VIEW;
	}
	
	@PreAuthorize("hasAuthority('PLAN_CREATE')")
	@PostMapping("/store")
	public String store(@Valid @ModelAttribute("planEntity") Plan plan, BindingResult bindingResult) {
		//Recuperamos al empleado
		Empleado empleado = empleadoService.findEmployeeById(plan.getEmpleado().getIdEmpleado());
		//Setiamos el empleado al Plan
		plan.setEmpleado(empleado);
		//Establecemos el plan a activo
		plan.setEsActivo(true);
		//Si es ingreso, la Tasa de Interes se debe establecer a 0
		if(!plan.isEsEgreso())
			plan.setTasaInteres(0);
		//Procedemos a almacenar el Plan
		planService.createPlan(plan);
		//System.out.println(plan.toString());
		
		return "redirect:/plan/index?idEmpleado=" + empleado.getIdEmpleado() + "&store_success";
	}
	
	@PreAuthorize("hasAuthority('PLAN_DELETE')")
	@PostMapping("/destroy")
	public String destroy(@RequestParam("idPlanDestroy") int idPlan) {
		Plan plan = planService.getPlan(idPlan);
		Empleado empleado = plan.getEmpleado();
		Cuota primera_cuota;
		
		//Validaremos que el Plan no posea cuotas pagadas, si no ha pagado la primera, NO ha pagado ninguna Cuota
		if(cuotaService.getAllCuotasByPlan(plan).size() > 0) {
			
			primera_cuota = cuotaService.getAllCuotasByPlan(plan).get(0);
			
			//Si el MontoCancelado es mayor a cero, entonces, no eliminamos el Plan
			if(primera_cuota.getMontoCancelado() > 0.00)
				return "redirect:/plan/index?idEmpleado=" + empleado.getIdEmpleado() + "&delete_failed";
			
		}
		
		planService.deletePlan(idPlan);
		
		return "redirect:/plan/index?idEmpleado=" + empleado.getIdEmpleado() + "&delete_success";
	}
	
}
