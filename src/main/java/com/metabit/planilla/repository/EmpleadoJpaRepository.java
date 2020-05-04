package com.metabit.planilla.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Empleado;

@Repository("empleadoJpaRepository")
public interface EmpleadoJpaRepository extends JpaRepository<Empleado,Serializable>{

}
