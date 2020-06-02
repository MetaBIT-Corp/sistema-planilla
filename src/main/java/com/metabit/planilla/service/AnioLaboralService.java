package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.AnioLaboral;

public interface AnioLaboralService {
	public abstract List<AnioLaboral> getAllAniosLaborales();
	public abstract AnioLaboral getAnioLaboral(int anio);
}
