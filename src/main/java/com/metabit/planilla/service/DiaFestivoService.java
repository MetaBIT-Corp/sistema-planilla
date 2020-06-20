package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.DiaFestivo;

public interface DiaFestivoService {

	public abstract List<DiaFestivo> getDiasFestivosDelPeriodoActivo();
	public abstract DiaFestivo getDiaFestivo(int idDiaFestivo);
}
