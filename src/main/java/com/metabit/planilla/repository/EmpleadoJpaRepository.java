package com.metabit.planilla.repository;

import java.io.Serializable;
import java.util.List;

import com.metabit.planilla.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metabit.planilla.entity.Empleado;
import com.metabit.planilla.entity.Genero;

@Repository("empleadoJpaRepository")
public interface EmpleadoJpaRepository extends JpaRepository<Empleado,Serializable>{

	public abstract List<Empleado> findByGenero(Genero genero);
	public abstract Empleado findByCodigo(String codigo);
	public abstract Empleado findByCorreoInstitucional(String correo);
	public abstract Empleado findByCorreoPersonal(String correo);
	public abstract Empleado findByUsuario(Usuario usuario);
}
