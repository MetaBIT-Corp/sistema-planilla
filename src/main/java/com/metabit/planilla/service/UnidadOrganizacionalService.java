package com.metabit.planilla.service;

import com.metabit.planilla.entity.UnidadOrganizacional;

import java.util.List;

public interface UnidadOrganizacionalService {

    public abstract List<UnidadOrganizacional> getAllUnidadesOrganizacionales();
    public abstract UnidadOrganizacional getOneUnidadOrganizacional(int id);
    public abstract List<UnidadOrganizacional> getAllHijas(UnidadOrganizacional unidad);
    public abstract UnidadOrganizacional addOrUpdateUnidadOrganizaional(UnidadOrganizacional unidadOrganizacional);
    public abstract void deleteUnidad(UnidadOrganizacional unidadOrganizacional);
}
