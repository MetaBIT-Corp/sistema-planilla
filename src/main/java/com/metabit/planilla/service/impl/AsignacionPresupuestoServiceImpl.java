package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.AsignacionPresupuesto;
import com.metabit.planilla.repository.AsignacionPrespuestoJpaRepository;
import com.metabit.planilla.service.AsignacionPresupuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("asignacionPresupuestoServiceImpl")
public class AsignacionPresupuestoServiceImpl implements AsignacionPresupuestoService {

    @Autowired
    @Qualifier("asignacionPresupuestoJpaRepository")
    private AsignacionPrespuestoJpaRepository asignacionPrespuestoJpaRepository;

    @Override
    public AsignacionPresupuesto addAsignacion(AsignacionPresupuesto asignacionPresupuesto) {
        return asignacionPrespuestoJpaRepository.save(asignacionPresupuesto);
    }
}
