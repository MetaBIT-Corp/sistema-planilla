package com.metabit.planilla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Direccion;
import com.metabit.planilla.repository.DireccionJpaRepository;
import com.metabit.planilla.service.DireccionService;

@Service("direccionServiceImpl")
public class DireccionServiceImpl implements DireccionService {

	@Autowired
	@Qualifier("direccionJpaRepository")
	private DireccionJpaRepository direccionJpaRepository;
	
	@Override
	public Direccion updateDireccion(Direccion direccion) {
		return direccionJpaRepository.saveAndFlush(direccion);
	}

}
