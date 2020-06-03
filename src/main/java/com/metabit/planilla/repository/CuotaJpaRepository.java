package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Cuota;
import com.metabit.planilla.entity.Plan;

@Repository("cuotaJpaRepository")
public interface CuotaJpaRepository extends JpaRepository<Cuota, Serializable>{

	public List<Cuota> findByPlan(Plan plan);
}
