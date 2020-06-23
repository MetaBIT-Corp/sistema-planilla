package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.metabit.planilla.entity.Puesto;

@Repository("puestoJpaRepository")
public interface PuestoJpaRepository extends JpaRepository<Puesto, Serializable>{
    public abstract List<Puesto> findAllByPuestoHabilitadoIsTrue();
}
