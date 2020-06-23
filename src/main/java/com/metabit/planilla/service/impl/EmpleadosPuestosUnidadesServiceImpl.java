package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadosPuestosUnidades;
import com.metabit.planilla.entity.UnidadOrganizacional;
import com.metabit.planilla.repository.EmpleadosPuestosUnidadesJpaRepository;
import com.metabit.planilla.service.EmpleadosPuestosUnidadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("empleadosPuestosUnidadesServiceImpl")
public class EmpleadosPuestosUnidadesServiceImpl implements EmpleadosPuestosUnidadesService {

    @Autowired
    @Qualifier("empleadosPuestosUnidadesJpaRepository")
    private EmpleadosPuestosUnidadesJpaRepository empleadosPuestosUnidadesJpaRepository;

    @Override
    public List<EmpleadosPuestosUnidades> getByEmployee(Empleado empleado) {
        return empleadosPuestosUnidadesJpaRepository.findByEmpleado(empleado);
    }

    @Override
    public EmpleadosPuestosUnidades createOrUpdate(EmpleadosPuestosUnidades empleadosPuestosUnidades) {
        return empleadosPuestosUnidadesJpaRepository.save(empleadosPuestosUnidades);
    }

    @Override
    public EmpleadosPuestosUnidades getByEmpleadoAndFechaFinIsNull(Empleado empleado) {
        return empleadosPuestosUnidadesJpaRepository.findByEmpleadoAndFechaFinIsNull(empleado);
    }

    @Override
    public List<EmpleadosPuestosUnidades> getAllByUnidadAndPuestoVigente(UnidadOrganizacional unidad) {
        return empleadosPuestosUnidadesJpaRepository.findAllByUnidadOrganizacionalAndFechaFinIsNull(unidad);
    }

}
