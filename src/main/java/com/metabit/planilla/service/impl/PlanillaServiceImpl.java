package com.metabit.planilla.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Periodo;
import com.metabit.planilla.entity.Planilla;
import com.metabit.planilla.repository.PlanillaJpaRepository;
import com.metabit.planilla.service.PlanillaService;

@Service("planillaServiceImpl")
public class PlanillaServiceImpl implements PlanillaService {

	@Autowired
	@Qualifier("planillaJpaRepository")
	private PlanillaJpaRepository planillaJpaRepository;
	
	@Override
	public Planilla storePlanilla(Planilla planilla) {
		// TODO Auto-generated method stub
		return planillaJpaRepository.save(planilla);
	}

	@Override
	public Planilla updatePlanilla(Planilla planilla) {
		// TODO Auto-generated method stub
		return planillaJpaRepository.save(planilla);
	}

	@Override
	public void updatePlanillaMovimientos(int id_planilla) {
		// TODO Auto-generated method stub
		planillaJpaRepository.planillaUpdateMovimientos(id_planilla);
	}

	@Override
	public List<Planilla> getPlanillasByPeriodo(Periodo periodo) {
		return planillaJpaRepository.findByPeriodo(periodo);
	}

	@Override
	public Optional<Planilla> getPlanillaById(int id_planilla) {
		return planillaJpaRepository.findById(id_planilla);
	}

	@Override
	public String showMessage(String p_message) {
		return planillaJpaRepository.showMessage(p_message);
	}
	
	
	
}
