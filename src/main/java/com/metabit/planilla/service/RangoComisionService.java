package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.RangoComision;

public interface RangoComisionService {

	public List<RangoComision> getAllRangoComision();
	public void deleteRangoComision(int idRangoComision);
	public RangoComision getOneRango(int id);
	public RangoComision createOrUpdate(RangoComision rangoComision);
}
