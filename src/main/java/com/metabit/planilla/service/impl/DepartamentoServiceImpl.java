package com.metabit.planilla.service.impl;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Departamento;
import com.metabit.planilla.repository.DepartamentoJpaRepository;
import com.metabit.planilla.service.DepartamentoService;

@Service("departamentoServiceImpl")
public class DepartamentoServiceImpl implements DepartamentoService {

	@Autowired
	@Qualifier("departamentoJpaRepository")
	private DepartamentoJpaRepository departamentoJpaRepository;
	@Override
	public List<Departamento> getAllDepartamentos() {
		return departamentoJpaRepository.findAll();
	}
	@Override
	public Departamento getDepartamento(int idDepartamento) {
		return departamentoJpaRepository.findByIdDepartamento(idDepartamento);
	}

	@Override
	public Departamento createOrUpdateDepartamento(Departamento departamento) {
		return departamentoJpaRepository.save(departamento);
	}

	@Override
	public void deleteDepartamento(Departamento departamento) {
		departamentoJpaRepository.delete(departamento);
	}


}
