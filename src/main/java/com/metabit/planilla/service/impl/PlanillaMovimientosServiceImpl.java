package com.metabit.planilla.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Planilla;
import com.metabit.planilla.entity.PlanillaMovimiento;
import com.metabit.planilla.repository.PlanillaMovimientosJpaRepository;
import com.metabit.planilla.service.PlanillaMovimientosService;

@Service("planillaMovimientosServiceImpl")
public class PlanillaMovimientosServiceImpl implements PlanillaMovimientosService{
	
	@Autowired
	@Qualifier("planillaMovimientosJpaRepository")
	private PlanillaMovimientosJpaRepository planillaMovimientosJpaRepository;

	@Override
	public PlanillaMovimiento storePlanillaMovimiento(PlanillaMovimiento planillaMovimiento) {
		// TODO Auto-generated method stub
		return planillaMovimientosJpaRepository.save(planillaMovimiento);
	}

	@Override
	public List<PlanillaMovimiento> getPlanillaMovimientosByPlanilla(Planilla planilla) {
		return planillaMovimientosJpaRepository.findByPlanilla(planilla);
	}

	@Override
	public Optional<PlanillaMovimiento> getPlanillaMovimientosById(int id_planilla_movimiento) {
		return planillaMovimientosJpaRepository.findById(id_planilla_movimiento);
	}

	@Override
	public void deletePlanillaMovimientosById(int id_planilla_movimiento) {
		planillaMovimientosJpaRepository.deleteById(id_planilla_movimiento);
		
	}
	
	
}
