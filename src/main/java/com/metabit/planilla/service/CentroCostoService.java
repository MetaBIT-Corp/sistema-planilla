package com.metabit.planilla.service;

import com.metabit.planilla.entity.CentroCosto;

public interface CentroCostoService {
    public abstract CentroCosto getAllCentroByUnidad(int id);
    public abstract CentroCosto creatOrUpdate(CentroCosto centroCosto);
    public abstract void deleteCentroCosto(CentroCosto centroCosto);
}
