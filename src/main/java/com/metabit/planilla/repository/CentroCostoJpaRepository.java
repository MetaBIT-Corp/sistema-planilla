package com.metabit.planilla.repository;

import com.metabit.planilla.entity.CentroCosto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("centroCostoJpaRepository")
public interface CentroCostoJpaRepository extends JpaRepository<CentroCosto, Serializable> {
}
