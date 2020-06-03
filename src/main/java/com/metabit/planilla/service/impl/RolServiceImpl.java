package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.repository.RolJpaRepository;
import com.metabit.planilla.service.RolService;

@Service("rolServiceImpl")
public class RolServiceImpl implements RolService{
	
	@Autowired
	@Qualifier("rolJpaRepository")
	private RolJpaRepository rolJpaRepository;

	@Override
	public List<Rol> getAllRoles() {
		// TODO Auto-generated method stub
		return rolJpaRepository.findAll();
	}

	@Override
	public Rol getByIdRol(Integer idRol) {
		// TODO Auto-generated method stub
		return rolJpaRepository.findByIdRol(idRol);
	}

	@Override
	public List<Rol> getAvailableRoles(int id_usuario) {
		// TODO Auto-generated method stub
		return rolJpaRepository.findAvailableRoles(id_usuario);
	}

	@Override
	public List<Rol> getUserRoles(int id_usuario) {
		// TODO Auto-generated method stub
		return rolJpaRepository.findUserRoles(id_usuario);
	}

	@Override
	public Rol storeRol(Rol rol) {
		return rolJpaRepository.save(rol);
	}

	@Override
	public Rol updateRol(Rol rol) {
		return rolJpaRepository.save(rol);
	}

	@Override
	public void deleteRol(int idRol) {
		rolJpaRepository.deleteById(idRol);
	}

}
