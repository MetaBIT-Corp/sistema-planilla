package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.RangoComision;

public interface RangoComisionService {

	public List<RangoComision> getAllRangoComision();
	public void deleteRangoComision(int idRangoComision);
}
