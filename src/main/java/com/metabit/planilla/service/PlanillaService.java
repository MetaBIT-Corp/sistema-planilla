package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.Periodo;
import com.metabit.planilla.entity.Planilla;

public interface PlanillaService {
	
	public abstract Planilla storePlanilla(Planilla planilla);
	public abstract Planilla updatePlanilla(Planilla planilla);
	public abstract void updatePlanillaMovimientos(int id_planilla);
	public abstract List<Planilla> getPlanillasByPeriodo(Periodo periodo);

}
