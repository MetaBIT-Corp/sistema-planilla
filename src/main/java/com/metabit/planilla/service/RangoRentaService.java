package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.RangoRenta;

public interface RangoRentaService {

	public abstract List<RangoRenta> getAllRangosRenta();
	public abstract List<RangoRenta> getByPeriodicidad(int periodicidad);
	public abstract List<RangoRenta> getAllHabilitados(Boolean estado);
	public abstract RangoRenta getOne(int id);
	public abstract RangoRenta update(RangoRenta rr);
	public abstract RangoRenta store(RangoRenta rr);
}
