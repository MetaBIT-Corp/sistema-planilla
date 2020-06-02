package com.metabit.planilla.repository;

import com.metabit.planilla.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("rolJpaRepository")
public interface RolJpaRepository extends JpaRepository<Rol, Serializable> {
}
