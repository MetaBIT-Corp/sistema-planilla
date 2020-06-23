package com.metabit.planilla.service;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadosPuestosUnidades;
import com.metabit.planilla.entity.UnidadOrganizacional;

import java.util.List;

public interface EmpleadosPuestosUnidadesService {
    public abstract List<EmpleadosPuestosUnidades> getByEmployee(Empleado empleado);
    public abstract EmpleadosPuestosUnidades createOrUpdate(EmpleadosPuestosUnidades empleadosPuestosUnidades);
    public abstract EmpleadosPuestosUnidades getByEmpleadoAndFechaFinIsNull(Empleado empleado);
    public abstract List<EmpleadosPuestosUnidades> getAllByUnidadAndPuestoVigente(UnidadOrganizacional unidad);
}
