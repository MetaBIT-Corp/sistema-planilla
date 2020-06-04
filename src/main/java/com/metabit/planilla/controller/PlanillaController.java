package com.metabit.planilla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@PostMapping("/store")
	public String store(@RequestParam(name =  "id_perido", required = false) Integer id_periodo) {
		List<Empleado> empleados = empleadoService.getAllEmployees();
		List<TipoMovimiento> tiposMovimiento = tipoMovimientoService.getTipoMovimientosFijos(true);
		Periodo periodo = periodoService.getByIdPerido(id_periodo);
		
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
				
			}
		}
		
		return "redirect:/anio-laboral/index";
	}
}
