package com.metabit.planilla.service;
import java.util.List;

import com.metabit.planilla.entity.Rol;

public interface RolService {
	
	public abstract List<Rol> getAllRoles();
	public abstract Rol getByIdRol(Integer idRol);
	public abstract List<Rol> getAvailableRoles(int id_usuario);
	public abstract List<Rol> getUserRoles(int id_usuario);

    public abstract Rol storeRol(Rol rol);
    public abstract Rol updateRol(Rol rol);
    public abstract void deleteRol(int idRol);
    public abstract List<Rol> getAllUsedRoles();

}
