package com.metabit.planilla.service.impl;


import com.metabit.planilla.entity.Direccion;
import com.metabit.planilla.repository.DireccionJpaRepository;
import com.metabit.planilla.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("direccionServiceImpl")
public class DireccionServiceImpl implements DireccionService {

    @Autowired
    @Qualifier("direccionJpaRepository")
    private DireccionJpaRepository direccionJpaRepository;

    @Override
    public Direccion addDirection(Direccion d) {
        return direccionJpaRepository.save(d);
    }

    @Override
    public Direccion updateDirection(Direccion d) {
        return direccionJpaRepository.save(d);
    }

    @Override
    public Direccion getDirection(int id) {
        return direccionJpaRepository.getOne(id);
    }

}
