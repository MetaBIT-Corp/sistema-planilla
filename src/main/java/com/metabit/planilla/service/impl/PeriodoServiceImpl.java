package com.metabit.planilla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Periodo;
import com.metabit.planilla.repository.PeriodoJpaRepository;
import com.metabit.planilla.service.PeriodoService;

@Service("periodoServiceImpl")
public class PeriodoServiceImpl implements PeriodoService{

	@Autowired
	@Qualifier("periodoJpaRepository")
	private PeriodoJpaRepository periodoJpaRepository;

	@Override
	public Periodo getByIdPerido(int id_periodo) {
		// TODO Auto-generated method stub
		return periodoJpaRepository.findByIdPeriodo(id_periodo);
	}
	
	

}
