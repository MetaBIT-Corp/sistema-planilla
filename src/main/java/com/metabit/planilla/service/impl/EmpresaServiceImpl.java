package com.metabit.planilla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Empresa;
import com.metabit.planilla.repository.EmpresaJpaRepository;
import com.metabit.planilla.service.EmpresaService;

@Service("empresaServiceImpl")
public class EmpresaServiceImpl implements EmpresaService{

	@Autowired
	@Qualifier("empresaJpaRepository")
	private EmpresaJpaRepository empresaJpaRepository;
	
	@Override
	public Empresa getEmpresa() {
		return empresaJpaRepository.getOne(1);
	}

	@Override
	public Empresa updateEmpresa(Empresa empresa) {
		return empresaJpaRepository.save(empresa);
	}

}
