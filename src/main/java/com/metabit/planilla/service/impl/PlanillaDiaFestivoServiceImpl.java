package com.metabit.planilla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.repository.PlanillaDiaFestivoJpaRepository;
import com.metabit.planilla.service.PlanillaDiaFestivoService;

@Service("planillaDiaFestivoServiceImpl")
public class PlanillaDiaFestivoServiceImpl implements PlanillaDiaFestivoService{

		@Autowired
		@Qualifier("planillaDiaFestivoJpaRepository")
		PlanillaDiaFestivoJpaRepository planillaDiaFestivoJpaRepository;
		
}
