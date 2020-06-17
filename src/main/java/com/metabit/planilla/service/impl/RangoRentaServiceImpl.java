package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.RangoRenta;
import com.metabit.planilla.repository.RangoRentaJpaRepository;
import com.metabit.planilla.service.RangoRentaService;

@Service("rangoRentaServiceImpl")
public class RangoRentaServiceImpl implements RangoRentaService {

	@Autowired
	@Qualifier("rangoRentaJpaRepository")
	private RangoRentaJpaRepository rangoRentaJpaRepository;

	@Override
	public List<RangoRenta> getAllRangosRenta() {
		// TODO Auto-generated method stub
		return rangoRentaJpaRepository.findAll();
	}

	@Override
	public RangoRenta getOne(int id) {
		// TODO Auto-generated method stub
		return rangoRentaJpaRepository.getOne(id);
	}

	@Override
	public RangoRenta update(RangoRenta rg) {
		// TODO Auto-generated method stub
		return rangoRentaJpaRepository.save(rg);
	}
	
}
