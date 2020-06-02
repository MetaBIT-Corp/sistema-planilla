package com.metabit.planilla.repository;

import java.io.Serializable;

import com.metabit.planilla.entity.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("recursoJpaRepository")
public interface RecursoJpaRepository extends JpaRepository<Recurso, Serializable> {
}
