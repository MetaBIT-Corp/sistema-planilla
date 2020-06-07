package com.metabit.planilla.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.RangoRenta;

@Repository("rangoRentaJpaRepository")
public interface RangoRentaJpaRepository extends JpaRepository<RangoRenta, Serializable>{

}
