package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Planilla;
import com.metabit.planilla.entity.PlanillaMovimiento;

@Repository("planillaMovimientosJpaRepository")
public interface PlanillaMovimientosJpaRepository extends JpaRepository<PlanillaMovimiento, Serializable>{

	public abstract List<PlanillaMovimiento> findByPlanilla(Planilla planilla);
}
