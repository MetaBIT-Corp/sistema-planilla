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
		Empresa emp_obj = empresaJpaRepository.getOne(1);
		emp_obj.setEmpresa(empresa.getEmpresa());
		emp_obj.setTelefono(empresa.getTelefono());
		emp_obj.setPage(empresa.getPage());
		emp_obj.setCorreoEmpresa(empresa.getCorreoEmpresa());
		emp_obj.setNicEmpresa(empresa.getNicEmpresa());
		emp_obj.setNitEmpresa(empresa.getNitEmpresa());
		emp_obj.setDireccion(empresa.getDireccion());
		emp_obj.setPaginaEmpresa(empresa.getPaginaEmpresa());
		return empresaJpaRepository.save(emp_obj);
	}

}
