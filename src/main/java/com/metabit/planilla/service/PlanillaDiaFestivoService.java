package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.DiaFestivo;
import com.metabit.planilla.entity.Planilla;
import com.metabit.planilla.entity.PlanillaDiaFestivo;

public interface PlanillaDiaFestivoService {

	public abstract void deleteAllPlanillaDiasFestivos(List<PlanillaDiaFestivo> planillaDiasFestivos);
	public abstract List<PlanillaDiaFestivo> addAllPlanillaDiasFestivos(List<PlanillaDiaFestivo> planillaDiasFestivos);
	public abstract List<PlanillaDiaFestivo> findByPlanilla(Planilla planilla);
}
