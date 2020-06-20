package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.RangoRenta;

@Repository("rangoRentaJpaRepository")
public interface RangoRentaJpaRepository extends JpaRepository<RangoRenta, Serializable>{
	public abstract List<RangoRenta> findByPeriodicidadRenta(int periodicidad);
	public abstract List<RangoRenta> findByRangoRentaHabilitado(Boolean estado);
}
