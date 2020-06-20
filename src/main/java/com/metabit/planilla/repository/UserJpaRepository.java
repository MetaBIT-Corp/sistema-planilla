package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Usuario;

@Repository("userJpaRepository")
public interface UserJpaRepository extends JpaRepository<Usuario, Serializable>{
	public abstract Usuario findByUsername(String username);
	
	@Query(value = "SELECT * FROM USUARIOS WHERE ID_USUARIO IN\n" + 
			"(SELECT ID_USUARIO FROM USUARIOS_ROLES NATURAL JOIN ROLES WHERE AUTHORITY='ROLE_ADMIN')", nativeQuery = true)
	public abstract List<Usuario> findAdminUsers();
	
	@Query(value = "SELECT USERNAME FROM USUARIOS NATURAL JOIN USUARIOS_ROLES NATURAL JOIN ROLES WHERE AUTHORITY='ROLE_ADMIN' AND USERNAME=?1", nativeQuery = true)
	public abstract String isAdminUser(String username);
}
