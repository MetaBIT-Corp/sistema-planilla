package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.Planilla;
import com.metabit.planilla.entity.PlanillaMovimiento;

public interface PlanillaMovimientosService {
	
	public abstract PlanillaMovimiento storePlanillaMovimiento(PlanillaMovimiento planillaMovimiento);
	public abstract List<PlanillaMovimiento> getPlanillaMovimientosByPlanilla(Planilla planilla);
}
