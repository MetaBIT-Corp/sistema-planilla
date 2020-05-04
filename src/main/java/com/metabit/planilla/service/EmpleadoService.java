package com.metabit.planilla.service;

import com.metabit.planilla.entity.Empleado;

import java.util.List;

public interface EmpleadoService {

    public abstract List<Empleado>getAllEmployees();
    public abstract Empleado addEmployee(Empleado e);
    public abstract Empleado updateEmployee(Empleado e);
    public abstract Empleado  findEmployeeById(int id);
}
