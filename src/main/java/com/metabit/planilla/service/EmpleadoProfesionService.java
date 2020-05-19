package com.metabit.planilla.service;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadoProfesion;
import com.metabit.planilla.entity.Profesion;

import java.util.List;

public interface EmpleadoProfesionService {

    public abstract List<EmpleadoProfesion> getAllProfessionsEmployee(Empleado e);
    public abstract EmpleadoProfesion createOrUpdateProfessionsEmployee(EmpleadoProfesion ep);
    public void deleteProfesionEmpleado(int id);
    public abstract List<EmpleadoProfesion> findByProfesion(Profesion profesion);
}
