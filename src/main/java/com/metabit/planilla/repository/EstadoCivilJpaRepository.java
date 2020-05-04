package com.metabit.planilla.repository;

import com.metabit.planilla.entity.EstadoCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("estadoCivilJpaRespository")
public interface EstadoCivilJpaRepository extends JpaRepository<EstadoCivil, Serializable> {
}
