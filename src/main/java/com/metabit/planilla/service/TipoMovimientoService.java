package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.TipoMovimiento;

public interface TipoMovimientoService {
	public abstract List<TipoMovimiento> getTiposMovimiento();
	public abstract TipoMovimiento getTipoMovimiento(int id);
	public abstract TipoMovimiento storeTipoMovimiento(TipoMovimiento tipoMovimiento);
}
