package com.metabit.planilla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.metabit.planilla.entity.Profesion;
import com.metabit.planilla.repository.ProfesionJpaRepository;
import com.metabit.planilla.service.ProfesionService;

@Service("profesionServiceImpl")
public class ProfesionServiceImpl implements ProfesionService {

    @Autowired
    @Qualifier("profesionJpaRepository")
    private ProfesionJpaRepository profesionJpaRepository;

    @Override
    public List<Profesion> getProfesiones() {
        return profesionJpaRepository.findAll();
    }

    @Override
    public Profesion getProfesion(int id) {
        return profesionJpaRepository.findById(id).get();
    }

    @Override
    public Profesion storeProfesion(Profesion profesion) {
        return profesionJpaRepository.save(profesion);
    }

    @Override
    public Profesion updateProfesion(Profesion profesion) {
        return profesionJpaRepository.save(profesion);
    }

    @Override
    public void deleteProfesion(int idProfesion) {
        profesionJpaRepository.deleteById(idProfesion);
    }

}