package com.metabit.planilla.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Usuario;

@Repository("userJpaRepository")
public interface UserJpaRepository extends JpaRepository<Usuario, Serializable>{
	public abstract Usuario findByUsername(String username);
}
