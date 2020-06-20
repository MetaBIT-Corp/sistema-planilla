package com.metabit.planilla.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Periodo;

@Repository("periodoJpaRepository")
public interface PeriodoJpaRepository extends JpaRepository<Periodo, Serializable>{

	public abstract Periodo findByIdPeriodo(int id_periodo);
	public abstract Periodo findByActivo(boolean activo);
	
	@Query(value = "SELECT * FROM periodos\n" + 
			"WHERE fecha_final < ( SELECT sysdate FROM dual ) AND activo = 0\n" + 
			"ORDER BY fecha_final DESC\n" + 
			"FETCH FIRST ROWS ONLY", nativeQuery = true)
	public abstract Periodo findPeriodoPrevio();
}
