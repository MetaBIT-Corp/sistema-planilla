package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Genero;
import com.metabit.planilla.repository.GeneroJpaRepository;
import com.metabit.planilla.service.GeneroService;

@Service("generoServiceImpl")
public class GeneroServiceImpl implements GeneroService{

	@Autowired
	@Qualifier("generoJpaRepository")
	private GeneroJpaRepository generoJpaRepository;
	
	@Override
	public Genero addGenero(Genero genero) {
		return generoJpaRepository.save(genero);
	}

	@Override
	public List<Genero> getAllGeneros() {
		return generoJpaRepository.findAll();
	}

	@Override
	public Genero getGenero(int idGenero) {
		return generoJpaRepository.getOne(idGenero);
	}

	@Override
	public Genero updateGenero(Genero genero) {
		return generoJpaRepository.save(genero);
	}

	@Override
	public void deleteGenero(int idGenero) {
		generoJpaRepository.deleteById(idGenero);
	}

}
