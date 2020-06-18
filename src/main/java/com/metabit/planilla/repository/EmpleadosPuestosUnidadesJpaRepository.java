package com.metabit.planilla.repository;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadosPuestosUnidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("empleadosPuestosUnidadesJpaRepository")
public interface EmpleadosPuestosUnidadesJpaRepository extends JpaRepository<EmpleadosPuestosUnidades, Serializable> {
    public abstract List<EmpleadosPuestosUnidades> findByEmpleado(Empleado empleado);
    public abstract EmpleadosPuestosUnidades findByEmpleadoAndFechaFinIsNull(Empleado empleado);
}
