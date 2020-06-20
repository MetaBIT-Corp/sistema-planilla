package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.DiaFestivo;
import com.metabit.planilla.repository.DiaFestivoJpaRepository;
import com.metabit.planilla.service.DiaFestivoService;

@Service("diaFestivoServiceImpl")
public class DiaFestivoServiceImpl implements DiaFestivoService{
	
	@Autowired
	@Qualifier("diaFestivoJpaRepository")
	private DiaFestivoJpaRepository diaFestivoJpaRepository;

	@Override
	public List<DiaFestivo> getDiasFestivos() {
		return diaFestivoJpaRepository.findAll();
	}

	@Override
	public DiaFestivo storeDiaFestivo(DiaFestivo diaFestivo) {
		return diaFestivoJpaRepository.save(diaFestivo);
	}

	@Override
	public DiaFestivo updateDiaFestivo(DiaFestivo diaFestivo) {
		return diaFestivoJpaRepository.save(diaFestivo);
	}

	@Override
	public void deleteDiaFestivo(int idDiaFestivo) {
		diaFestivoJpaRepository.deleteById(idDiaFestivo);
	}

	@Override
	public DiaFestivo getDiaFestivo(int id) {
		return diaFestivoJpaRepository.findById(id).get();
	}

	
	
}
