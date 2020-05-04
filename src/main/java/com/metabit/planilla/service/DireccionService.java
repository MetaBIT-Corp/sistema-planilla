package com.metabit.planilla.service;

import com.metabit.planilla.entity.Direccion;

public interface DireccionService {

    public abstract Direccion addDirection(Direccion d);
    public abstract Direccion updateDirection(Direccion d);
    public abstract Direccion getDirection(int id);

}
