package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Puesto;
import com.metabit.planilla.repository.PuestoJpaRepository;
import com.metabit.planilla.service.PuestoService;

@Service("puestoServiceImpl")
public class PuestoServiceImpl implements PuestoService{
	
	@Autowired
	@Qualifier("puestoJpaRepository")
	private PuestoJpaRepository puestoJpaRepository;

	@Override
	public List<Puesto> getPuestos() {
		// TODO Auto-generated method stub
		return puestoJpaRepository.findAll();
	}

	@Override
	public Puesto getPuesto(int id) {
		return puestoJpaRepository.findById(id).get();
	}

	@Override
	public Puesto storePuesto(Puesto puesto) {
		// TODO Auto-generated method stub
		return puestoJpaRepository.save(puesto);
	}

	@Override
	public Puesto updatePuesto(Puesto puesto) {
		// TODO Auto-generated method stub
		return puestoJpaRepository.save(puesto);
	}

	@Override
	public void deletePuesto(int idPuesto) {
		// TODO Auto-generated method stub
		puestoJpaRepository.deleteById(idPuesto);
	}

}
