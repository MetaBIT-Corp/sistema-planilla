package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.Plan;

public interface PlanService {

	public Plan createPlan(Plan plan);
	public Plan updatePlan(Plan plan);
	public void deletePlan(int idPlan);
	public List<Plan> getAllPlansByEmpleado(Empleado empleado);
}
