package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Departamento;
import com.metabit.planilla.entity.Municipio;

@Repository("municipioJpaRepository")
public interface MunicipioJpaRepository extends JpaRepository<Municipio, Serializable>{

	public abstract List<Municipio> findByDepartamento(Departamento departamento);
}
