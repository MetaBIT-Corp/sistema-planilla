package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.DiaFestivo;
import com.metabit.planilla.entity.Planilla;
import com.metabit.planilla.entity.PlanillaDiaFestivo;
import com.metabit.planilla.repository.PlanillaDiaFestivoJpaRepository;
import com.metabit.planilla.service.PlanillaDiaFestivoService;

@Service("planillaDiaFestivoServiceImpl")
public class PlanillaDiaFestivoServiceImpl implements PlanillaDiaFestivoService{

		@Autowired
		@Qualifier("planillaDiaFestivoJpaRepository")
		PlanillaDiaFestivoJpaRepository planillaDiaFestivoJpaRepository;


		@Override
		public void deleteAllPlanillaDiasFestivos(List<PlanillaDiaFestivo> planillaDiasFestivos) {
			planillaDiaFestivoJpaRepository.deleteAll(planillaDiasFestivos);
		}

		@Override
		public List<PlanillaDiaFestivo> addAllPlanillaDiasFestivos(List<PlanillaDiaFestivo> planillaDiasFestivos) {
			return planillaDiaFestivoJpaRepository.saveAll(planillaDiasFestivos);
		}

		@Override
		public List<PlanillaDiaFestivo> findByPlanilla(Planilla planilla) {
			return planillaDiaFestivoJpaRepository.findByPlanilla(planilla);
		}
		
}
