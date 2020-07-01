package com.metabit.planilla.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.metabit.planilla.entity.Cuota;
import com.metabit.planilla.entity.Plan;
import org.springframework.data.jpa.repository.Query;

public interface CuotaService {

	public Cuota createCuota(Cuota cuota);
	public Cuota updateCuota(Cuota cuota);
	public void deleteCuota(int idCuota);
	public List<Cuota> getAllCuotasByPlan(Plan plan);

	public List<Cuota> getCuotasPlanesIngresoActivosByEmpleado(int idEmpleado, LocalDate inicioPeriodo, LocalDate finPeriodo);
	public List<Cuota> getCuotasPlanesEgresoActivosByEmpleado(int idEmpleado, LocalDate inicioPeriodo, LocalDate finPeriodo);
}
