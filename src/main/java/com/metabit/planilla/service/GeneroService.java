package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.Genero;

public interface GeneroService {

	public abstract Genero addGenero(Genero genero);
	public abstract List<Genero> getAllGeneros();
}
