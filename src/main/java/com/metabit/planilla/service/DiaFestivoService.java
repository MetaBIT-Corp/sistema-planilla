package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.DiaFestivo;

public interface DiaFestivoService {
	public abstract List<DiaFestivo> getDiasFestivos();
	public abstract DiaFestivo getDiaFestivo(int id);
	public abstract DiaFestivo storeDiaFestivo(DiaFestivo diaFestivo);
	public abstract DiaFestivo updateDiaFestivo(DiaFestivo diaFestivo);
	public abstract void deleteDiaFestivo(int idDiaFestivo);
	public abstract List<DiaFestivo> getDiasFestivosDelPeriodoActivo();

}
