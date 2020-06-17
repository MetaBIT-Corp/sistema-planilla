package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.RangoRenta;

public interface RangoRentaService {

	public abstract List<RangoRenta> getAllRangosRenta();
	public abstract RangoRenta getOne(int id);
	public abstract RangoRenta update(RangoRenta rg);
}
