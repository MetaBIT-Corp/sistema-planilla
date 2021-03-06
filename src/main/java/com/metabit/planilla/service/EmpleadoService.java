package com.metabit.planilla.service;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EstadoCivil;
import com.metabit.planilla.entity.Genero;
import com.metabit.planilla.entity.Usuario;

import java.util.List;

public interface EmpleadoService {

    public abstract List<Empleado>getAllEmployees();
    public abstract Empleado addEmployee(Empleado e);
    public abstract Empleado updateEmployee(Empleado e);
    public abstract Empleado  findEmployeeById(int id);
    public abstract List<Empleado> findByGenero(Genero genero);
    public abstract List<Empleado> findByEstadoCivil(EstadoCivil estadoCivil);
    public abstract Boolean existEmployeeCode(String codigo,int idEmp);
    public abstract Boolean existInstitucionalEmail(String correo,int idEmp);
    public abstract Boolean existPersonalEmail(String correo,int idEmp);
    public abstract Empleado findByUsuario (Usuario usuario);
    public abstract List<Empleado> getAllHabilitados();
}
