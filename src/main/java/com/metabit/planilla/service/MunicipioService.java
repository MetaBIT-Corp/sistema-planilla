package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.Departamento;
import com.metabit.planilla.entity.Municipio;

public interface MunicipioService {

	public abstract List<Municipio> getAllMunicipios();
	public abstract Municipio getMunicipio(int idMunicipio);
	public abstract List<Municipio> getMunicipiosByDepartamento(Departamento departamento);

	public abstract Municipio storeMunicipio(Municipio municipio);
	public abstract Municipio updateMunicipio(Municipio municipio);
	public abstract void deleteMunicipio(int idMunicipio);
}
