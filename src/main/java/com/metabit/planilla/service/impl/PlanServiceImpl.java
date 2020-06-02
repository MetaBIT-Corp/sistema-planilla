package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.Plan;
import com.metabit.planilla.repository.PlanJpaRepository;
import com.metabit.planilla.service.PlanService;

@Service("planServiceImpl")
public class PlanServiceImpl implements PlanService{

	@Autowired
	@Qualifier("planJpaRepository")
	private PlanJpaRepository planJpaRepository;
	
	@Override
	public Plan createPlan(Plan plan) {
		
		return planJpaRepository.save(plan);
	}

	@Override
	public Plan updatePlan(Plan plan) {
		
		return planJpaRepository.save(plan);
	}

	@Override
	public void deletePlan(int idPlan) {
		
		planJpaRepository.deleteById(idPlan);
	}

	@Override
	public List<Plan> getAllPlansByEmpleado(Empleado empleado) {
		
		return planJpaRepository.findByEmpleado(empleado);
	}

}
