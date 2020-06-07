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

	@Override
	public TipoUnidadOrganizacional store(TipoUnidadOrganizacional tuo) {
		// TODO Auto-generated method stub
		return tipoUnidadOrganizacionalJpaRepository.save(tuo);
	}

	@Override
	public TipoUnidadOrganizacional update(TipoUnidadOrganizacional tuo) {
		// TODO Auto-generated method stub
		return tipoUnidadOrganizacionalJpaRepository.save(tuo);
	}

	@Override
	public void destroy(int id_tuo) {
		// TODO Auto-generated method stub
		tipoUnidadOrganizacionalJpaRepository.deleteById(id_tuo);
	}

	@Override
	public List<TipoUnidadOrganizacional> getByTipoUnidadOrganizacionalHabilitado(boolean estado) {
		// TODO Auto-generated method stub
		return tipoUnidadOrganizacionalJpaRepository.findByTipoUnidadOrganizacionalHabilitado(estado);
	}
    
    
}
