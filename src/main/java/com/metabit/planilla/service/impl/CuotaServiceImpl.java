package com.metabit.planilla.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Cuota;
import com.metabit.planilla.entity.Plan;
import com.metabit.planilla.repository.CuotaJpaRepository;
import com.metabit.planilla.service.CuotaService;

@Service("cuotaServiceImpl")
public class CuotaServiceImpl implements CuotaService {

	@Autowired
	@Qualifier("cuotaJpaRepository")
	private CuotaJpaRepository cuotaJpaRepository;
	
	@Override
	public Cuota createCuota(Cuota cuota) {
		
		return cuotaJpaRepository.save(cuota);
	}

	@Override
	public Cuota updateCuota(Cuota cuota) {
		
		return cuotaJpaRepository.save(cuota);
	}

	@Override
	public void deleteCuota(int idCuota) {
		
		cuotaJpaRepository.deleteById(idCuota);
	}

	@Override
	public List<Cuota> getAllCuotasByPlan(Plan plan) {
		
		return cuotaJpaRepository.findByPlan(plan);
	}

	@Override
	public List<Cuota> getCuotasPlanesIngresoActivosByEmpleado(int idEmpleado, LocalDate inicioPeriodo, LocalDate finPeriodo) {
		return cuotaJpaRepository.getCuotasPlanesIngresoActivosByEmpleado(idEmpleado, inicioPeriodo, finPeriodo);
	}

	@Override
	public List<Cuota> getCuotasPlanesEgresoActivosByEmpleado(int idEmpleado, LocalDate inicioPeriodo, LocalDate finPeriodo) {
		return cuotaJpaRepository.getCuotasPlanesEgresoActivosByEmpleado(idEmpleado,inicioPeriodo, finPeriodo);
	}

}
