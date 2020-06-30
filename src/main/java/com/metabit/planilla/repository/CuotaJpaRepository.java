package com.metabit.planilla.repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Cuota;
import com.metabit.planilla.entity.Plan;

@Repository("cuotaJpaRepository")
public interface CuotaJpaRepository extends JpaRepository<Cuota, Serializable>{

	public List<Cuota> findByPlan(Plan plan);

	@Query(value = "SELECT * FROM CUOTAS WHERE ID_PLAN IN (SELECT ID_PLAN FROM PLANES WHERE ID_EMPLEADO = ?1 AND ES_ACTIVO = 1 AND ES_EGRESO = 0) AND (FECHA_PREVISTA_PAGO >= ?2) AND (FECHA_PREVISTA_PAGO <= ?3)", nativeQuery = true)
	public List<Cuota> getCuotasPlanesIngresoActivosByEmpleado(int idEmpleado, LocalDate inicioPeriodo, LocalDate finPeriodo);

	@Query(value = "SELECT * FROM CUOTAS WHERE ID_PLAN IN (SELECT ID_PLAN FROM PLANES WHERE ID_EMPLEADO = ?1 AND ES_ACTIVO = 1 AND ES_EGRESO = 1) AND (FECHA_PREVISTA_PAGO >= ?2) AND (FECHA_PREVISTA_PAGO <= ?3)", nativeQuery = true)
	public List<Cuota> getCuotasPlanesEgresoActivosByEmpleado(int idEmpleado, LocalDate inicioPeriodo, LocalDate finPeriodo);

}
