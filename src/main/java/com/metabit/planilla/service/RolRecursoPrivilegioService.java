package com.metabit.planilla.service;

import com.metabit.planilla.entity.Privilegio;
import com.metabit.planilla.entity.Recurso;
import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.RolRecursoPrivilegio;

import java.util.List;

public interface RolRecursoPrivilegioService {

    public abstract List<RolRecursoPrivilegio> getRolesRecursosPrivilegios();
    public abstract RolRecursoPrivilegio getRolRecursoPrivilegio(int idRolRecursoPrivilegio);
    public abstract RolRecursoPrivilegio storeRolRecursoPrivilegio(RolRecursoPrivilegio rolRecursoPrivilegio);
    public abstract RolRecursoPrivilegio updateRolRecursoPrivilegio(RolRecursoPrivilegio rolRecursoPrivilegio);
    public abstract void deleteRolRecursoPrivilegio(RolRecursoPrivilegio rolRecursoPrivilegio);

    public abstract List<RolRecursoPrivilegio> findByRol(Rol rol);
    public abstract List<RolRecursoPrivilegio> findByRecurso(Recurso recurso);
    public abstract List<RolRecursoPrivilegio> findByPrivilegio(Privilegio privilegio);
    public abstract List<RolRecursoPrivilegio> findByRolAndRecurso(Rol rol, Recurso recurso);

    public abstract RolRecursoPrivilegio getRolRecursoPrivilegioByRolAndRecursoAndPrivilegio(Rol rol, Recurso recurso, Privilegio privilegio);

}