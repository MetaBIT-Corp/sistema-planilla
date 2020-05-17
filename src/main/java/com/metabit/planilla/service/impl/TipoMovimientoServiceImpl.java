package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.TipoMovimiento;
import com.metabit.planilla.repository.TipoMovimientoJpaRepository;
import com.metabit.planilla.service.TipoMovimientoService;

@Service("tipoMovimientoServiceImpl")
public class TipoMovimientoServiceImpl implements TipoMovimientoService {

	@Autowired
	@Qualifier("tipoMovimientoJpaRepository")
	private TipoMovimientoJpaRepository tipoMovimientoJpaRepository;
	
	@Override
	public List<TipoMovimiento> getTiposMovimiento() {
		// TODO Auto-generated method stub
		return tipoMovimientoJpaRepository.findAll();
	}

	@Override
	public TipoMovimiento getTipoMovimiento(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoMovimiento storeTipoMovimiento(TipoMovimiento tipoMovimiento) {
		// TODO Auto-generated method stub
		return null;
	}

}
