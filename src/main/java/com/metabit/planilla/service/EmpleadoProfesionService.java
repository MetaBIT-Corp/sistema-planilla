package com.metabit.planilla.service;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadoProfesion;

import java.util.List;

public interface EmpleadoProfesionService {

    public abstract List<EmpleadoProfesion> getAllProfessionsEmployee(Empleado e);
    public abstract EmpleadoProfesion createOrUpdateProfessionsEmployee(EmpleadoProfesion ep);
}
