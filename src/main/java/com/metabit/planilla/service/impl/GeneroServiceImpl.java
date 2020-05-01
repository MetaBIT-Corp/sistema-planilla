package com.metabit.planilla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Genero;
import com.metabit.planilla.repository.GeneroRepository;
import com.metabit.planilla.service.GeneroService;

@Service("generoServiceImpl")
public class GeneroServiceImpl implements GeneroService{

	@Autowired
	@Qualifier("generoRepository")
	private GeneroRepository generoRepository;
	
	@Override
	public Genero addGenero(Genero genero) {
		return generoRepository.save(genero);
	}

}
