package com.metabit.planilla.repository;

import com.metabit.planilla.entity.Privilegio;
import com.metabit.planilla.entity.Recurso;
import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.RolesRecursosPrivilegios;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("rolRecursoPrivilegioJpaRepository")
public interface RolRecursoPrivilegioRepository extends JpaRepository<RolesRecursosPrivilegios, Serializable> {
    public abstract List<RolesRecursosPrivilegios> findByRol(Rol rol);
    public abstract List<RolesRecursosPrivilegios> findByRecurso(Recurso recurso);
    public abstract List<RolesRecursosPrivilegios> findByPrivilegio(Privilegio privilegio);
}
