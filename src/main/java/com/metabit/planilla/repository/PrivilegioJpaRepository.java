package com.metabit.planilla.repository;

import java.io.Serializable;

import com.metabit.planilla.entity.Privilegio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("privilegioJpaRepository")
public interface PrivilegioJpaRepository extends JpaRepository<Privilegio, Serializable> {
}
