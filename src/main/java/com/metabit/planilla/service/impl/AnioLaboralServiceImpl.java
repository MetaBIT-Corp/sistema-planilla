package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.AnioLaboral;
import com.metabit.planilla.repository.AnioLaboralJpaRepository;
import com.metabit.planilla.service.AnioLaboralService;

@Service("anioLaboralServiceImpl")
public class AnioLaboralServiceImpl implements AnioLaboralService {

	@Autowired
	@Qualifier("anioLaboralJpaRepository")
	private AnioLaboralJpaRepository anioLaboralJpaRepository;
	
	@Override
	public List<AnioLaboral> getAllAniosLaborales() {
		// TODO Auto-generated method stub
		return anioLaboralJpaRepository.findAll();
	}

	@Override
	public AnioLaboral getAnioLaboral(int anio) {
		// TODO Auto-generated method stub
		return anioLaboralJpaRepository.findByAnioLaboral(anio);
	}

	@Override
	public AnioLaboral addAnioLaboral(AnioLaboral anioLaboral) {
		// TODO Auto-generated method stub
		return anioLaboralJpaRepository.save(anioLaboral);
	}
	
}
