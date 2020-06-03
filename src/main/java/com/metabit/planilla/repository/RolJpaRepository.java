package com.metabit.planilla.repository;


import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.Usuario;

@Repository("rolJpaRepository")
public interface RolJpaRepository extends JpaRepository<Rol, Serializable>{

	public abstract Rol findByIdRol(Integer idRol);

	/*
	* René Martínez
	* Método para recuperar roles asignados a usuarios.
	*/
	@Query(value = "SELECT ID_ROL, AUTHORITY FROM ROLES NATURAL JOIN USUARIOS_ROLES", nativeQuery = true)
	public abstract List<Rol> getAllUsedRoles();

	@Query(value = "SELECT * FROM ROLES MINUS SELECT * FROM ROLES WHERE ID_ROL IN (SELECT ID_ROL FROM USUARIOS_ROLES WHERE ID_USUARIO=?1)", nativeQuery = true)
	public abstract List<Rol> findAvailableRoles(int id_usuario);
	@Query(value = "SELECT * FROM ROLES WHERE ID_ROL IN (SELECT ID_ROL FROM USUARIOS_ROLES WHERE ID_USUARIO=?1)", nativeQuery = true)
	public abstract List<Rol> findUserRoles(int id_usuario);

}
