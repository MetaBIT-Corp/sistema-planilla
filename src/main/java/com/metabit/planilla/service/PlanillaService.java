package com.metabit.planilla.service;

import com.metabit.planilla.entity.Planilla;

public interface PlanillaService {
	
	public abstract Planilla storePlanilla(Planilla planilla);
	public abstract Planilla updatePlanilla(Planilla planilla);

}
