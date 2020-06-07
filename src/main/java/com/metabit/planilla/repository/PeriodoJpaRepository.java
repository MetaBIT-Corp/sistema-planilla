package com.metabit.planilla.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Periodo;

@Repository("periodoJpaRepository")
public interface PeriodoJpaRepository extends JpaRepository<Periodo, Serializable>{

	public abstract Periodo findByIdPeriodo(int id_periodo);
	public abstract Periodo findByActivo(boolean activo);
}
