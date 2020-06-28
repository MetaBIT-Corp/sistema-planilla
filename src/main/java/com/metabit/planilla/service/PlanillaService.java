package com.metabit.planilla.service;

import java.util.List;
import java.util.Optional;

import com.metabit.planilla.entity.Periodo;
import com.metabit.planilla.entity.Planilla;

public interface PlanillaService {
	
	public abstract Optional<Planilla> getPlanillaById(int id_planilla);
	public abstract Planilla getPlanilla(int id_planilla);
	public abstract Planilla storePlanilla(Planilla planilla);
	public abstract Planilla updatePlanilla(Planilla planilla);
	public abstract void updatePlanillaMovimientos(int id_planilla);
	public abstract void generarPlanillas(int id_periodo);
	public abstract List<Planilla> getPlanillasByPeriodo(Periodo periodo);
	public abstract List<Planilla> getPlanillasUnidad(int id_unidad, int id_periodo);
	public abstract String showMessage(String p_message);
	public abstract String pagarPlanilla(int idUnidadOrganizacional);
	public abstract List<Planilla> findByPeriodo(Periodo periodo);

}
