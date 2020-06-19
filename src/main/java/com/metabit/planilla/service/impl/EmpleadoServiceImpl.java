package com.metabit.planilla.service.impl;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.Genero;
import com.metabit.planilla.entity.Usuario;
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

    @Override
    public Boolean existEmployeeCode(String codigo, int idEmp) {
        if(idEmp==0){
            return (empleadoJpaRepository.findByCodigo(codigo)!=null)?true:false;
        }else{
            if(idEmp==empleadoJpaRepository.findByCodigo(codigo).getIdEmpleado()){
                return false;
            }else{
                return true;
            }
        }

    }

    @Override
    public Boolean existInstitucionalEmail(String correo, int idEmp) {
        if(idEmp==0){
            return (empleadoJpaRepository.findByCorreoInstitucional(correo)!=null)?true:false;
        }else{
            if(empleadoJpaRepository.findByCorreoInstitucional(correo)!=null){
                if(idEmp==empleadoJpaRepository.findByCorreoInstitucional(correo).getIdEmpleado()){
                    return false;
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }
    }

    @Override
    public Boolean existPersonalEmail(String correo, int idEmp) {
        if (idEmp==0){
            return (empleadoJpaRepository.findByCorreoPersonal(correo)!=null)?true:false;
        }else{
            if(empleadoJpaRepository.findByCorreoPersonal(correo)!=null){
                if(idEmp==empleadoJpaRepository.findByCorreoPersonal(correo).getIdEmpleado()){
                    return false;
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }
    }

    @Override
    public Empleado findByUsuario(Usuario usuario) {
        return empleadoJpaRepository.findByUsuario(usuario);
    }

    @Override
    public List<Empleado> getAllHabilitados() {
        return empleadoJpaRepository.findAllByEmpleadoHabilitadoIsTrue();
    }

}
