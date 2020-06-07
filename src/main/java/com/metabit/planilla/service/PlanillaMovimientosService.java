package com.metabit.planilla.service;

import java.util.List;
import java.util.Optional;

import com.metabit.planilla.entity.Planilla;
import com.metabit.planilla.entity.PlanillaMovimiento;

public interface PlanillaMovimientosService {
	
	public abstract PlanillaMovimiento storePlanillaMovimiento(PlanillaMovimiento planillaMovimiento);
	public abstract List<PlanillaMovimiento> getPlanillaMovimientosByPlanilla(Planilla planilla);
	public abstract Optional<PlanillaMovimiento> getPlanillaMovimientosById(int id_planilla_movimiento);
	public abstract void deletePlanillaMovimientosById(int id_planilla_movimiento);
}
