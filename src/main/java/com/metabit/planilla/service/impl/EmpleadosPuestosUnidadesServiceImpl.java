package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadosPuestosUnidades;
import com.metabit.planilla.repository.EmpleadosPuestosUnidadesJpaRepository;
import com.metabit.planilla.service.EmpleadosPuestosUnidadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("empleadosPuestosUnidadesServiceImpl")
public class EmpleadosPuestosUnidadesServiceImpl implements EmpleadosPuestosUnidadesService {

    @Autowired
    @Qualifier("empleadosPuestosUnidadesJpaRepository")
    private EmpleadosPuestosUnidadesJpaRepository empleadosPuestosUnidadesJpaRepository;

    @Override
    public EmpleadosPuestosUnidades getByEmployee(Empleado empleado) {
        return empleadosPuestosUnidadesJpaRepository.findByEmpleado(empleado);
    }

    @Override
    public EmpleadosPuestosUnidades createOrUpdate(EmpleadosPuestosUnidades empleadosPuestosUnidades) {
        return empleadosPuestosUnidadesJpaRepository.save(empleadosPuestosUnidades);
    }
}
