package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.Genero;

public interface GeneroService {

	public abstract Genero addGenero(Genero genero);
	public abstract List<Genero> getAllGeneros();
	public abstract Genero getGenero(int idGenero);
	public abstract Genero updateGenero(Genero genero);
	public abstract void deleteGenero(int idGenero);
}
