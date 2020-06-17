package com.metabit.planilla.repository;

import com.metabit.planilla.entity.AsignacionPresupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("asignacionPresupuestoJpaRepository")
public interface AsignacionPrespuestoJpaRepository extends JpaRepository<AsignacionPresupuesto, Serializable> {
}
