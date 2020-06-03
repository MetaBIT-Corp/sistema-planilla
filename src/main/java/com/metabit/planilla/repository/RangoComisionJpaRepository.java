package com.metabit.planilla.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.RangoComision;

@Repository("rangoComisionJpaRepository")
public interface RangoComisionJpaRepository extends JpaRepository<RangoComision, Serializable>{
    public abstract RangoComision getById(int id);
}
