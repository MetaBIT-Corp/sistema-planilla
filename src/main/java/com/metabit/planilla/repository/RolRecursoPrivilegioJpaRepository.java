package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Privilegio;
import com.metabit.planilla.entity.Recurso;
import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.RolRecursoPrivilegio;

@Repository("rolRecursoPrivilegioJpaRepository")
public interface RolRecursoPrivilegioJpaRepository extends JpaRepository<RolRecursoPrivilegio, Serializable> {

    public abstract List<RolRecursoPrivilegio> findByRol(Rol rol);
    public abstract List<RolRecursoPrivilegio> findByRecurso(Recurso recurso);
    public abstract List<RolRecursoPrivilegio> findByPrivilegio(Privilegio privilegio);
    public abstract List<RolRecursoPrivilegio> findByRecursoAndPrivilegio(Recurso recurso, Privilegio privilegio);
    public abstract List<RolRecursoPrivilegio> findByRolAndRecurso(Rol rol, Recurso recurso);
    public abstract RolRecursoPrivilegio getRolRecursoPrivilegioByRolAndRecursoAndPrivilegio(Rol rol, Recurso recurso, Privilegio privilegio);

}