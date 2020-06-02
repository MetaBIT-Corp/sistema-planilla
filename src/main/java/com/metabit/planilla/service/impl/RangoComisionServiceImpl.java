package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.RangoComision;
import com.metabit.planilla.repository.RangoComisionJpaRepository;
import com.metabit.planilla.service.RangoComisionService;

@Service("rangoComisionServiceImpl")
public class RangoComisionServiceImpl implements RangoComisionService{

	@Autowired
	@Qualifier("rangoComisionJpaRepository")
	private RangoComisionJpaRepository rangoComisionJpaRepository;
	
	@Override
	public List<RangoComision> getAllRangoComision() {
		
		return rangoComisionJpaRepository.findAll();
	}

	@Override
	public void deleteRangoComision(int idRangoComision) {
		
		rangoComisionJpaRepository.deleteById(idRangoComision);
	}

}
