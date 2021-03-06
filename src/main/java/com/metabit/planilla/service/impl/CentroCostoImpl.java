package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.AnioLaboral;
import com.metabit.planilla.entity.CentroCosto;
import com.metabit.planilla.entity.UnidadOrganizacional;
import com.metabit.planilla.repository.CentroCostoJpaRepository;
import com.metabit.planilla.service.CentroCostoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("centroCostoServiceImpl")
public class CentroCostoImpl implements CentroCostoService {

    @Autowired
    @Qualifier("centroCostoJpaRepository")
    private CentroCostoJpaRepository centroCostoJpaRepository;

    @Override
    public CentroCosto getAllCentroByUnidad(int id) {
        return null;
    }

    @Override
    public CentroCosto creatOrUpdate(CentroCosto centroCosto) {
        return centroCostoJpaRepository.save(centroCosto);
    }

    @Override
    public CentroCosto findByAnioAndUnidad(AnioLaboral anio, UnidadOrganizacional unidad) {
        return centroCostoJpaRepository.findByAnioLaboralAndUnidadOrganizacional(anio,unidad);
    }

    @Override
    public CentroCosto getOneCentroCosto(int id) {
        return centroCostoJpaRepository.getOne(id);
    }

    @Override
    public void deleteCentroCosto(CentroCosto centroCosto) {
        centroCostoJpaRepository.delete(centroCosto);
    }

}
