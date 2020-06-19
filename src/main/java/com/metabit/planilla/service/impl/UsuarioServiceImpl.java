package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Usuario;
import com.metabit.planilla.repository.UserJpaRepository;
import com.metabit.planilla.service.UsuarioService;

@Service("usuarioServiceImpl")
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;
	
	@Override
	public List<Usuario> getAdminUsers() {
		// TODO Auto-generated method stub
		return userJpaRepository.findAdminUsers();
	}
	
	
}
