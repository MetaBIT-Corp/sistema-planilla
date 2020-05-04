package com.metabit.planilla.repository;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadoProfesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("empleadoProfesionJpaRepository")
public interface EmpleadoProfesionJpaRepository extends JpaRepository<EmpleadoProfesion, Serializable> {
    public abstract List<EmpleadoProfesion> findByEmpleado(Empleado e);
}
