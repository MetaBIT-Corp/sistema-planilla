package com.metabit.planilla.service;


import com.metabit.planilla.entity.Departamento;
import java.util.List;

public interface DepartamentoService {
    public abstract List<Departamento> getAllDepartamentos();
    public abstract Departamento getDepartamento(int idDepartamento);

}
