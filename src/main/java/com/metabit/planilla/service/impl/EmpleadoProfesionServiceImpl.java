package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.EmpleadoProfesion;
import com.metabit.planilla.repository.EmpleadoProfesionJpaRepository;
import com.metabit.planilla.service.EmpleadoProfesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service("empleadoProfesionServiceImpl")
public class EmpleadoProfesionServiceImpl implements EmpleadoProfesionService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    @Qualifier("empleadoProfesionJpaRepository")
    private EmpleadoProfesionJpaRepository empleadoProfesionJpaRepository;

    @Override
    public List<EmpleadoProfesion> getAllProfessionsEmployee(Empleado e) {
        return empleadoProfesionJpaRepository.findByEmpleado(e);
    }

    @Override
    public EmpleadoProfesion createOrUpdateProfessionsEmployee(EmpleadoProfesion ep) {
        return empleadoProfesionJpaRepository.save(ep);
    }

    @Override
    @Transactional
    public void deleteProfesionEmpleado(int id) {
        Query query = em.createNativeQuery("DELETE EMPLEADOS_PROFESIONES WHERE ID_EMPLEADO_PROFESION= ?");
        query.setParameter(1,id);
        em.joinTransaction();
        query.executeUpdate();
        em.close();
    }

}
