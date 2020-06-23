package com.metabit.planilla.service;

import com.metabit.planilla.entity.EstadoCivil;

import java.util.List;

public interface EstadoCivilService {
    public abstract List<EstadoCivil> getAllCivilStates();
    public abstract EstadoCivil getCivilState(int id);
    public abstract EstadoCivil addCivilState(EstadoCivil estadoCivil);
    public abstract EstadoCivil updateCivilState(EstadoCivil estadoCivil); 
    public abstract void deleteEstadoCivil(int idEstadoCivil);
}
