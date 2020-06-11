package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.Privilegio;
import com.metabit.planilla.repository.PrivilegioJpaRepository;
import com.metabit.planilla.service.PrivilegioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("privilegioServiceImpl")
public class PrivilegioServiceImpl implements PrivilegioService {

    @Autowired
    @Qualifier("privilegioJpaRepository")
    private PrivilegioJpaRepository privilegioJpaRepository;

    @Override
    public List<Privilegio> getPrivilegios() {
        return privilegioJpaRepository.findAll();
    }

    @Override
    public Privilegio getPrivilegio(int idPrivilegio) {
        return privilegioJpaRepository.findById(idPrivilegio).get();
    }

    @Override
    public Privilegio storePrivilegio(Privilegio privilegio) {
        return privilegioJpaRepository.save(privilegio);
    }

    @Override
    public Privilegio updatePrivilegio(Privilegio privilegio) {
        return privilegioJpaRepository.save(privilegio);
    }

    @Override
    public void deletePrivilegio(int idPrivilegio) {
        privilegioJpaRepository.deleteById(idPrivilegio);
    }

    @Override
    public List<Privilegio> getRolRecursoPrivilegios(Integer idRol, Integer idRecurso) {
        return privilegioJpaRepository.getRolRecursoPrivilegios(idRol,idRecurso);
    }
}
