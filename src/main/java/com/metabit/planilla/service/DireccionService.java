package com.metabit.planilla.service;

import com.metabit.planilla.entity.Direccion;

import java.util.List;

public interface DireccionService {

    public abstract Direccion addDirection(Direccion d);
    public abstract Direccion updateDirection(Direccion d);
    public abstract Direccion getDirection(int id);
    public abstract List<Direccion> getAllDirecciones();
}
