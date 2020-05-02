package com.metabit.planilla.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Genero;

@Repository("generoRepository")
public interface GeneroRepository extends JpaRepository<Genero, Serializable>{

}
