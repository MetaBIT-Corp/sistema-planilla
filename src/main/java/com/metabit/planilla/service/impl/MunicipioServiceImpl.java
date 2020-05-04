package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Municipio;
import com.metabit.planilla.repository.MunicipioJpaRepository;
import com.metabit.planilla.service.MunicipioService;

@Service("municipioServiceImpl")
public class MunicipioServiceImpl implements MunicipioService{

	@Autowired
	@Qualifier("municipioJpaRepository")
	private MunicipioJpaRepository municipioJpaRepository;
	
	@Override
	public List<Municipio> getAllMunicipios() {
		return municipioJpaRepository.findAll();
	}

}
