package com.metabit.planilla.service;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadosPuestosUnidades;

public interface EmpleadosPuestosUnidadesService {
    public abstract EmpleadosPuestosUnidades getByEmployee(Empleado empleado);
    public abstract EmpleadosPuestosUnidades createOrUpdate(EmpleadosPuestosUnidades empleadosPuestosUnidades);
}
