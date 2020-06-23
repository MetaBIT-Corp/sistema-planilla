package com.metabit.planilla.repository;

import com.metabit.planilla.entity.UnidadOrganizacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("unidadOrganizacionalJpaRepository")
public interface UnidadOrganizacionalJpaRepository extends JpaRepository<UnidadOrganizacional, Serializable> {

    public abstract UnidadOrganizacional findByIdUnidadOrganizacional(int id);
    public abstract List<UnidadOrganizacional> findByUnidadPadreIs(UnidadOrganizacional unidad);
}
