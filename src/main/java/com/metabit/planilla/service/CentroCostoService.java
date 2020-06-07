package com.metabit.planilla.service;

import com.metabit.planilla.entity.AnioLaboral;
import com.metabit.planilla.entity.CentroCosto;
import com.metabit.planilla.entity.UnidadOrganizacional;

public interface CentroCostoService {
    public abstract CentroCosto getAllCentroByUnidad(int id);
    public abstract CentroCosto creatOrUpdate(CentroCosto centroCosto);
    public abstract CentroCosto findByAnioAndUnidad(AnioLaboral anio, UnidadOrganizacional unidad);
    public abstract CentroCosto getOneCentroCosto(int id);
    public abstract void deleteCentroCosto(CentroCosto centroCosto);
}
