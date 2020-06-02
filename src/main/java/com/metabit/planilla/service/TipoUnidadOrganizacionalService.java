package com.metabit.planilla.service;

import com.metabit.planilla.entity.TipoUnidadOrganizacional;

import java.util.List;

public interface TipoUnidadOrganizacionalService {
    public abstract List<TipoUnidadOrganizacional> getAll();
    public abstract TipoUnidadOrganizacional getOne(int id);
}
