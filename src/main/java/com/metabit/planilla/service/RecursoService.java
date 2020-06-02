package com.metabit.planilla.service;

import com.metabit.planilla.entity.Recurso;
import java.util.List;

public interface RecursoService {
    public abstract List<Recurso> getRecursos();
    public abstract Recurso getRecursos(int idRecurso);
    public abstract Recurso storeRecurso(Recurso recurso);
    public abstract Recurso updateRecurso(Recurso recurso);
    public abstract void deleteRecurso(int idRecurso);
}
