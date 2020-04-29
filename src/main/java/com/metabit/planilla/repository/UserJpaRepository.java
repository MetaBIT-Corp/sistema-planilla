package com.metabit.planilla.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.User;

@Repository("userJpaRepository")
public interface UserJpaRepository extends JpaRepository<User, Serializable>{

	public abstract User findByUsername(String username);
}
