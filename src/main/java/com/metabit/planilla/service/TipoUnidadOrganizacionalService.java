package com.metabit.planilla.service;

import com.metabit.planilla.entity.TipoUnidadOrganizacional;

import java.util.List;

public interface TipoUnidadOrganizacionalService {
    public abstract List<TipoUnidadOrganizacional> getAll();
    public abstract List<TipoUnidadOrganizacional> getByTipoUnidadOrganizacionalHabilitado(boolean estado);
    public abstract TipoUnidadOrganizacional getOne(int id);
    public abstract TipoUnidadOrganizacional store(TipoUnidadOrganizacional tuo);
    public abstract TipoUnidadOrganizacional update(TipoUnidadOrganizacional tuo);
    public abstract void destroy(int id_tuo);
}
