package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.Puesto;

public interface PuestoService {
	
	public abstract List<Puesto> getPuestos();
	public abstract Puesto getPuesto(int id);
	public abstract Puesto storePuesto(Puesto puesto);
	public abstract Puesto updatePuesto(Puesto puesto);
	public abstract void deletePuesto(int idPuesto);
	public abstract void destroyPuesto(int idPuesto);

}
