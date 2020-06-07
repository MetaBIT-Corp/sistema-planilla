package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.Privilegio;
import com.metabit.planilla.entity.Recurso;
import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.RolRecursoPrivilegio;
import com.metabit.planilla.repository.RolRecursoPrivilegioJpaRepository;
import com.metabit.planilla.service.RolRecursoPrivilegioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("rolRecursoPrivilegioServiceImpl")
public class RolRecursoPrivilegioServiceImpl implements RolRecursoPrivilegioService {

    @Autowired
    @Qualifier("rolRecursoPrivilegioJpaRepository")
    private RolRecursoPrivilegioJpaRepository rolRecursoPrivilegioJpaRepository;

    @Override
    public List<RolRecursoPrivilegio> getRolesRecursosPrivilegios() {
        return rolRecursoPrivilegioJpaRepository.findAll();
    }

    @Override
    public RolRecursoPrivilegio getRolRecursoPrivilegio(int idRolRecursoPrivilegio) {
        return rolRecursoPrivilegioJpaRepository.findById(idRolRecursoPrivilegio).get();
    }

    @Override
    public RolRecursoPrivilegio storeRolRecursoPrivilegio(RolRecursoPrivilegio rolRecursoPrivilegio) {
        return rolRecursoPrivilegioJpaRepository.save(rolRecursoPrivilegio);
    }

    @Override
    public RolRecursoPrivilegio updateRolRecursoPrivilegio(RolRecursoPrivilegio rolRecursoPrivilegio) {
        return rolRecursoPrivilegioJpaRepository.save(rolRecursoPrivilegio);
    }

    @Override
    public void deleteRolRecursoPrivilegio(int idRolRecursoPrivilegio) {
        rolRecursoPrivilegioJpaRepository.deleteById(idRolRecursoPrivilegio);
    }

    @Override
    public List<RolRecursoPrivilegio> findByRol(Rol rol) {
        return rolRecursoPrivilegioJpaRepository.findByRol(rol);
    }

    @Override
    public List<RolRecursoPrivilegio> findByRecurso(Recurso recurso) {
        return rolRecursoPrivilegioJpaRepository.findByRecurso(recurso);
    }

    @Override
    public List<RolRecursoPrivilegio> findByPrivilegio(Privilegio privilegio) {
        return rolRecursoPrivilegioJpaRepository.findByPrivilegio(privilegio);
    }

}
