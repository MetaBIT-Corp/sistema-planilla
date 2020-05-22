package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.UnidadOrganizacional;
import com.metabit.planilla.repository.UnidadOrganizacionalJpaRepository;
import com.metabit.planilla.service.UnidadOrganizacionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("unidadOrganiacionalServiceImpl")
public class UnidadOrganizacionalServiceImpl implements UnidadOrganizacionalService {

    @Autowired
    @Qualifier("unidadOrganizacionalJpaRepository")
    private UnidadOrganizacionalJpaRepository unidadOrganizacionalJpaRepository;

    @Override
    public List<UnidadOrganizacional> getAllUnidadesOrganizacionales() {
        return unidadOrganizacionalJpaRepository.findAll();
    }

    @Override
    public UnidadOrganizacional getOneUnidadOrganizacional(int id) {
        return unidadOrganizacionalJpaRepository.getOne(id);
    }

    @Override
    public List<UnidadOrganizacional> getAllHijas(int id) {
        return null;
    }

    @Override
    public UnidadOrganizacional addOrUpdateUnidadOrganizaional(UnidadOrganizacional unidadOrganizacional) {
        return unidadOrganizacionalJpaRepository.save(unidadOrganizacional);
    }

    @Override
    public void deleteUnidad(UnidadOrganizacional unidadOrganizacional) {
        unidadOrganizacionalJpaRepository.delete(unidadOrganizacional);
    }
}
