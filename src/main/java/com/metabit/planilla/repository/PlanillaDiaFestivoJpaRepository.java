package com.metabit.planilla.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.PlanillaDiaFestivo;

@Repository("planillaDiaFestivoJpaRepository")
public interface PlanillaDiaFestivoJpaRepository extends JpaRepository<PlanillaDiaFestivo, Serializable>{

}
