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
	
	
	
}
