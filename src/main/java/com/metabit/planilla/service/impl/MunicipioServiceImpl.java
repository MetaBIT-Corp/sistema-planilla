package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Departamento;
import com.metabit.planilla.entity.Municipio;
import com.metabit.planilla.repository.MunicipioJpaRepository;
import com.metabit.planilla.service.MunicipioService;

@Service("municipioServiceImpl")
public class MunicipioServiceImpl implements MunicipioService{

	@Autowired
	@Qualifier("municipioJpaRepository")
	private MunicipioJpaRepository municipioJpaRepository;
	
	@Override
	public List<Municipio> getAllMunicipios() {
		return municipioJpaRepository.findAll();
	}

	@Override
	public Municipio getMunicipio(int idMunicipio) {
		return municipioJpaRepository.getOne(idMunicipio);
	}

	@Override
	public List<Municipio> getMunicipiosByDepartamento(Departamento departamento) {
		return municipioJpaRepository.findByDepartamento(departamento);
	}

	@Override
	public Municipio storeMunicipio(Municipio municipio) {
		return municipioJpaRepository.save(municipio);
	}

	@Override
	public Municipio updateMunicipio(Municipio municipio) {
		return municipioJpaRepository.save(municipio);
	}

	@Override
	public void deleteMunicipio(int idMunicipio) {
		municipioJpaRepository.deleteById(idMunicipio);
	}

}
