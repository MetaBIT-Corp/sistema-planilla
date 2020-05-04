package com.metabit.planilla.repository;

import com.metabit.planilla.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("direccionJpaRepository")
public interface DireccionJpaRepository extends JpaRepository<Direccion, Serializable> {
}
