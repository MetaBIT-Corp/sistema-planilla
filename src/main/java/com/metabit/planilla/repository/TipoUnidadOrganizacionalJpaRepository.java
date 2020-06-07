package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.TipoUnidadOrganizacional;

@Repository("tipoUnidadOrganizacionalJpaRepository")
public interface TipoUnidadOrganizacionalJpaRepository extends JpaRepository<TipoUnidadOrganizacional, Serializable> {
	
	public abstract List<TipoUnidadOrganizacional> findByTipoUnidadOrganizacionalHabilitado(Boolean estado);
}
