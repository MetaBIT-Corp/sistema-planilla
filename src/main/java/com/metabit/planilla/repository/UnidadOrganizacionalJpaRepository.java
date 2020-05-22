package com.metabit.planilla.repository;

import com.metabit.planilla.entity.UnidadOrganizacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("unidadOrganizacionalJpaRepository")
public interface UnidadOrganizacionalJpaRepository extends JpaRepository<UnidadOrganizacional, Serializable> {
}
