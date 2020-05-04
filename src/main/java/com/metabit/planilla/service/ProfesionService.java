package com.metabit.planilla.service;

import java.util.List;
import com.metabit.planilla.entity.Profesion;

public interface ProfesionService {
    public abstract List<Profesion> getProfesiones();
    public abstract Profesion getProfesion(int id);
    public abstract Profesion storeProfesion(Profesion profesion);
    public abstract Profesion updateProfesion(Profesion profesion);
    public abstract void deleteProfesion(int idProfesion);
}
