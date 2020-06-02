package com.metabit.planilla.service;

import com.metabit.planilla.entity.Rol;

import java.util.List;

public interface RolService {
    public abstract List<Rol> getRoles();
    public abstract Rol getRol(int idRol);
    public abstract Rol storeRol(Rol rol);
    public abstract Rol updateRol(Rol rol);
    public abstract void deleteRol(int idRol);
}
