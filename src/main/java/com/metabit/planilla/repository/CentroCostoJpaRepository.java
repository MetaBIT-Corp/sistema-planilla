package com.metabit.planilla.repository;

import com.metabit.planilla.entity.AnioLaboral;
import com.metabit.planilla.entity.CentroCosto;
import com.metabit.planilla.entity.UnidadOrganizacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("centroCostoJpaRepository")
public interface CentroCostoJpaRepository extends JpaRepository<CentroCosto, Serializable> {

    public abstract CentroCosto findByAnioLaboralAndUnidadOrganizacional(AnioLaboral anio, UnidadOrganizacional unidad);
}
