package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "empleados_profesiones")
public class EmpleadoProfesion {
	
	@Id
	@GeneratedValue
	@Column(name = "id_empleado_profesion", nullable = false)
	private int idProfesion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_empleado")
	Empleado empleado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_profesion")
	Profesion profesion;

}
