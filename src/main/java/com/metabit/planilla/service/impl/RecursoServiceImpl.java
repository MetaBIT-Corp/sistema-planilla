package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.Recurso;
import com.metabit.planilla.repository.RecursoJpaRepository;
import com.metabit.planilla.service.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("recursoServiceImpl")
public class RecursoServiceImpl implements RecursoService {

    @Autowired
    @Qualifier("recursoJpaRepository")
    private RecursoJpaRepository recursoJpaRepository;

    @Override
    public List<Recurso> getRecursos() {
        return recursoJpaRepository.findAll();
    }

    @Override
    public Recurso getRecurso(int idRecurso) {
        return recursoJpaRepository.findById(idRecurso).get();
    }

    @Override
    public Recurso storeRecurso(Recurso recurso) {
        return recursoJpaRepository.save(recurso);
    }

    @Override
    public Recurso updateRecurso(Recurso recurso) {
        return recursoJpaRepository.save(recurso);
    }

    @Override
    public void deleteRecurso(int idRecurso) {
        recursoJpaRepository.deleteById(idRecurso);
    }
}
