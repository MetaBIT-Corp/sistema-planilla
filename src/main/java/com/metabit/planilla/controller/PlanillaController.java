package com.metabit.planilla.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.Periodo;
import com.metabit.planilla.entity.Planilla;
import com.metabit.planilla.entity.PlanillaMovimiento;
import com.metabit.planilla.entity.TipoMovimiento;
import com.metabit.planilla.service.EmpleadoService;
import com.metabit.planilla.service.PeriodoService;
import com.metabit.planilla.service.PlanillaMovimientosService;
import com.metabit.planilla.service.PlanillaService;
import com.metabit.planilla.service.TipoMovimientoService;

@Controller
@RequestMapping("/planilla")
public class PlanillaController {
	
	private static String INDEX_VIEW = "planilla/index";
	private static String SHOW_VIEW = "planilla/show";
	
	@Autowired
    @Qualifier("periodoServiceImpl")
    private PeriodoService periodoService;
	
	@Autowired
    @Qualifier("planillaServiceImpl")
    private PlanillaService planillaService;
	
	@Autowired
    @Qualifier("empleadoServiceImpl")
    private EmpleadoService empleadoService;
	
	@Autowired
    @Qualifier("planillaMovimientosServiceImpl")
    private PlanillaMovimientosService planillaMovimientosService;
	
	@Autowired
    @Qualifier("tipoMovimientoServiceImpl")
    private TipoMovimientoService tipoMovimientoService;
	
	@GetMapping("/index")
	public String index(Model model) {
		Periodo periodo_activo = periodoService.getPeriodoActivo();
		List<Planilla> planillas = planillaService.getPlanillasByPeriodo(periodo_activo);
		
		model.addAttribute("planillas", planillas);
		
		return INDEX_VIEW;
	}
	
	@GetMapping("/show")
	public String show(Model model, @RequestParam(name = "planilla", required = true) int id_planilla) {
		Optional<Planilla> planilla = planillaService.getPlanillaById(id_planilla);
		
		List<PlanillaMovimiento> ingresos = null;
		List<PlanillaMovimiento> descuentos = null;
		
		if(planilla.isPresent()) {
			model.addAttribute("planilla", planilla.get());
			List<PlanillaMovimiento> planillaMovimientos = planillaMovimientosService.getPlanillaMovimientosByPlanilla(planilla.get());
			
			for (PlanillaMovimiento planillaMovimiento : planillaMovimientos) {
				
				if(planillaMovimiento.getTipoMovimiento().isEsDescuento())
					descuentos.add(planillaMovimiento);
				else
					ingresos.add(planillaMovimiento);
			}
			
			model.addAttribute("ingresos", ingresos);
			model.addAttribute("descuentos", descuentos);
			
			List<TipoMovimiento> allDescuentos = tipoMovimientoService.getByEsDescuento(true);
			List<TipoMovimiento> allIngresos = tipoMovimientoService.getByEsDescuento(false);
			
			List<TipoMovimiento> descuentosPendientes = obtenerPendientes(descuentos, allDescuentos);
			List<TipoMovimiento> ingresosPendientes = obtenerPendientes(ingresos, allIngresos);
			
			model.addAttribute("descuentosPendientes", descuentosPendientes);
			model.addAttribute("ingresosPendientes", ingresosPendientes);
			model.addAttribute("planilla", id_planilla);
			
		}
		
		return SHOW_VIEW;
	}
	
	private List<TipoMovimiento> obtenerPendientes(List<PlanillaMovimiento> asignados,
			List<TipoMovimiento> allTiposMovimiento) {
		
		List<TipoMovimiento> pendientes = null;
		
		for (TipoMovimiento tipoMovimiento: allTiposMovimiento) {
			
			boolean tipoMovimientoPendiente = true;
			
			for (PlanillaMovimiento asignado : asignados) {
				
				if(tipoMovimiento == asignado.getTipoMovimiento())
					tipoMovimientoPendiente = false;
				
			}
			
			if(tipoMovimientoPendiente)
				pendientes.add(tipoMovimiento);
			
		}
		
		return pendientes;
	}
	
	@PostMapping("/agregar-movimientos")
	public String agregarMovimientos(@RequestParam(name =  "id_planilla", required = true) Integer id_planilla,
			@RequestParam(name =  "movimientos_seleccionados", required = true) List<String> idMovimientos) {
		
		String tipo = null;
		int i = 0;
		
		Planilla planilla = planillaService.getPlanillaById(id_planilla).isPresent()? planillaService.getPlanillaById(id_planilla).get() : null;
		
		for (String idMovimiento : idMovimientos) {
			
			float montoMovimiento = 0;
			TipoMovimiento movimiento = tipoMovimientoService.getTipoMovimiento(Integer.parseInt(idMovimiento));
			
			if(i == 0) {
				if(movimiento.isEsDescuento())
					tipo = "descuentos";
				else
					tipo = "ingresos";
			}
			
			if(movimiento.getMontoBase() > 0)
				montoMovimiento = (float) movimiento.getMontoBase();
			else //Posiblemente aqui se debe validar si el periodo es Mensual o Quincenal, con base a eso, el salario base del empleado debe cambiar (a la mitad si es quincenal)
				montoMovimiento = (float) (planilla.getEmpleado().getSalarioBaseMensual() * movimiento.getPorcentajeMovimiento());
			
			PlanillaMovimiento planillaMovimiento = new PlanillaMovimiento();
			planillaMovimiento.setPlanilla(planilla);
			planillaMovimiento.setTipoMovimiento(movimiento);
			planillaMovimiento.setMontoMovimiento(montoMovimiento);
			
			planillaMovimientosService.storePlanillaMovimiento(planillaMovimiento);
			
			i++;
		}
		
		return "redirect:/planilla/show?planilla=" + planilla.getIdPlanilla();
	}

	@PostMapping("/store")
	public String store(@RequestParam(name =  "id_periodo", required = false) Integer id_periodo, RedirectAttributes redirAttrs) {
		List<Empleado> empleados = empleadoService.getAllEmployees();
		List<TipoMovimiento> tiposMovimiento = tipoMovimientoService.getTipoMovimientosFijos(true);
		Periodo periodo = periodoService.getByIdPeriodo(id_periodo);
		List<String> errors = new ArrayList<String>();
		
		if(periodo.getFechaInicio().isAfter(LocalDate.now()) || periodo.getFechaFinal().isBefore(LocalDate.now()) || !periodo.getPlanillas().isEmpty()) {
			if(periodo.getFechaInicio().isAfter(LocalDate.now()))
				errors.add("No se pueden generar las planillas de este periodo, esperar hasta "+periodo.getFechaInicio());
			if(periodo.getFechaFinal().isBefore(LocalDate.now()) || !periodo.getPlanillas().isEmpty())
				errors.add("Las planillas de este periodo ya fueron generadas.");
			
			redirAttrs.addFlashAttribute("errors", errors);
			return "redirect:/anio-laboral/index";
		}
		
		periodo.setActivo(true);
		periodoService.storePeriodo(periodo);
		
		for (Empleado empleado : empleados) {
			Planilla planilla = new Planilla();
			planilla.setEmpleado(empleado);
			planilla.setPeriodo(periodo);
			planillaService.storePlanilla(planilla);
			
			for(TipoMovimiento tm : tiposMovimiento) {
				PlanillaMovimiento pm = new PlanillaMovimiento();
				float movimiento=0;
				
				if(periodo.getAnioLaboral().getPeriodicidad()==30) {
					movimiento=(float) (tm.getMontoBase()+(tm.getPorcentajeMovimiento()/100)*empleado.getSalarioBaseMensual());
				}else {
					movimiento=(float) ((tm.getMontoBase()+(tm.getPorcentajeMovimiento()/100)*empleado.getSalarioBaseMensual())/2);
				}
				
				pm.setMontoMovimiento(movimiento);
				pm.setTipoMovimiento(tm);
				pm.setPlanilla(planilla);
				planillaMovimientosService.storePlanillaMovimiento(pm);
				
			}
			planillaService.updatePlanillaMovimientos(planilla.getIdPlanilla());
		}
		
		redirAttrs.addFlashAttribute("success_planilla", "success");
		return "redirect:/anio-laboral/index";
	}
}
