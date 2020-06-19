package com.metabit.planilla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.repository.DiaFestivoJpaRepository;
import com.metabit.planilla.service.DiaFestivoService;

@Service("diaFestivoServiceImpl")
public class DiaFestivoServiceImpl implements DiaFestivoService{
	
	@Autowired
	@Qualifier("diaFestivoJpaRepository")
	private DiaFestivoJpaRepository diaFestivoJpaRepository;
	
}
