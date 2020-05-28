package com.metabit.planilla.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Rol;

@Repository("rolJpaRepository")
public interface RolJpaRepository extends JpaRepository<Rol, Serializable>{
	public abstract Rol findByIdRol(Integer idRol);

}
