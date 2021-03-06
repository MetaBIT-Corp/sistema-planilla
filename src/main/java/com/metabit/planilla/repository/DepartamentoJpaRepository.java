package com.metabit.planilla.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.metabit.planilla.entity.Departamento;

@Repository("departamentoJpaRepository")
public interface DepartamentoJpaRepository extends JpaRepository<Departamento, Serializable> {

    public abstract Departamento findByIdDepartamento(int id);
}
