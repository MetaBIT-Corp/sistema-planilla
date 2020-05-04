package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.Genero;
import com.metabit.planilla.repository.EmpleadoJpaRepository;
import com.metabit.planilla.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("empleadoServiceImpl")
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    @Qualifier("empleadoJpaRepository")
    private EmpleadoJpaRepository empleadoJpaRepository;

    @Override
    public List<Empleado> getAllEmployees() {
        return empleadoJpaRepository.findAll();
    }

    @Override
    public Empleado addEmployee(Empleado e) {
        return empleadoJpaRepository.save(e);
    }

    @Override
    public Empleado updateEmployee(Empleado e) {
        return empleadoJpaRepository.save(e);
    }

    @Override
    public Empleado findEmployeeById(int id) {
        return empleadoJpaRepository.getOne(id);
    }

	@Override
	public List<Empleado> findByGenero(Genero genero) {
		return empleadoJpaRepository.findByGenero(genero);
	}
}
