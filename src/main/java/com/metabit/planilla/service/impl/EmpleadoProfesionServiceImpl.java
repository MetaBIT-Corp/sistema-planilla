package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadoProfesion;
import com.metabit.planilla.repository.EmpleadoProfesionJpaRepository;
import com.metabit.planilla.service.EmpleadoProfesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("empleadoProfesionServiceImpl")
public class EmpleadoProfesionServiceImpl implements EmpleadoProfesionService {

    @Autowired
    @Qualifier("empleadoProfesionJpaRepository")
    private EmpleadoProfesionJpaRepository empleadoProfesionJpaRepository;
    @Override
    public List<EmpleadoProfesion> getAllProfessionsEmployee(Empleado e) {
        return empleadoProfesionJpaRepository.findByEmpleado(e);
    }

    @Override
    public Boolean createOrUpdateProfessionsEmployee(List<EmpleadoProfesion> ep) {
        Iterable<EmpleadoProfesion> i = ep;
        return !empleadoProfesionJpaRepository.saveAll(i).isEmpty();
    }
}
