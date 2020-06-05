package com.metabit.planilla.service;

import com.metabit.planilla.entity.Periodo;

public interface PeriodoService {
	
	public abstract Periodo getByIdPeriodo(int id_periodo);
	public abstract Periodo storePeriodo(Periodo periodo);
}
