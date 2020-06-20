package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.EstadoCivil;
import com.metabit.planilla.repository.EstadoCivilJpaRepository;
import com.metabit.planilla.service.EstadoCivilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("estadoCivilServiceImpl")
public class EstadoCivilServiceImpl implements EstadoCivilService {
    @Autowired
    @Qualifier("estadoCivilJpaRespository")
    private EstadoCivilJpaRepository estadoCivilJpaRepository;

    @Override
    public List<EstadoCivil> getAllCivilStates() {
        return estadoCivilJpaRepository.findAll();
    }

    @Override
    public EstadoCivil getCivilState(int id) {
        return estadoCivilJpaRepository.getOne(id);
    }

	@Override
	public EstadoCivil addCivilState(EstadoCivil estadoCivil) {
		return estadoCivilJpaRepository.save(estadoCivil);
	}

	@Override
	public EstadoCivil updateCivilState(EstadoCivil estadoCivil) {
		return estadoCivilJpaRepository.save(estadoCivil);
	}

	@Override
	public void deleteEstadoCivil(int idEstadoCivil) {
		estadoCivilJpaRepository.deleteById(idEstadoCivil);
	}
}
