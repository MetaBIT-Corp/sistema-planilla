package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.Plan;

@Repository("planJpaRepository")
public interface PlanJpaRepository extends JpaRepository<Plan, Serializable> {

	public List<Plan> findByEmpleado(Empleado empleado);

	@Query(value = "SELECT * FROM PLANES WHERE ID_EMPLEADO = ?1 AND ES_ACTIVO = 1 AND ES_EGRESO = 0", nativeQuery = true)
	public List<Plan> getPlanesIngresoActivosByEmpleado(int idEmpleado);

	@Query(value = "SELECT * FROM PLANES WHERE ID_EMPLEADO = ?1 AND ES_ACTIVO = 1 AND ES_EGRESO = 1", nativeQuery = true)
	public List<Plan> getPlanesEgresoActivosByEmpleado(int idEmpleado);


}
