package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.Plan;

@Repository("planJpaRepository")
public interface PlanJpaRepository extends JpaRepository<Plan, Serializable> {

	public List<Plan> findByEmpleado(Empleado empleado);
}
