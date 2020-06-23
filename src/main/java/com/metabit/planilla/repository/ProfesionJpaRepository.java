package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.metabit.planilla.entity.Profesion;

@Repository("profesionJpaRepository")
public interface ProfesionJpaRepository extends JpaRepository<Profesion, Serializable> {
    public abstract List<Profesion> findAllByProfesionHabilitadaIsTrue();
}