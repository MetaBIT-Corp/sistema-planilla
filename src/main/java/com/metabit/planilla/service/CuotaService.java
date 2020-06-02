package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.Cuota;
import com.metabit.planilla.entity.Plan;

public interface CuotaService {

	public Cuota createCuota(Cuota cuota);
	public Cuota updateCuota(Cuota cuota);
	public void deleteCuota(int idCuota);
	public List<Cuota> getAllCuotasByPlan(Plan plan);
}
