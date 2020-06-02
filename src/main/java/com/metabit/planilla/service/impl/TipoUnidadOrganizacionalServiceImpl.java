package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.TipoUnidadOrganizacional;
import com.metabit.planilla.repository.TipoUnidadOrganizacionalJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.service.TipoUnidadOrganizacionalService;

import java.util.List;

@Service("tipoUnidadOrganizacionalServiceImpl")
public class TipoUnidadOrganizacionalServiceImpl implements TipoUnidadOrganizacionalService {

    @Autowired
    @Qualifier("tipoUnidadOrganizacionalJpaRepository")
    private TipoUnidadOrganizacionalJpaRepository tipoUnidadOrganizacionalJpaRepository;

    @Override
    public List<TipoUnidadOrganizacional> getAll() {
        return tipoUnidadOrganizacionalJpaRepository.findAll();
    }

    @Override
    public TipoUnidadOrganizacional getOne(int id) {
        return tipoUnidadOrganizacionalJpaRepository.getOne(id);
    }
}
