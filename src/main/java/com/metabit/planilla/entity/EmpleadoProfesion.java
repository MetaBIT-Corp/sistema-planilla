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
	private int idProfesionEmpleado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empleado")
	private Empleado empleado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profesion")
	private Profesion profesion;

	public EmpleadoProfesion(){};

	public EmpleadoProfesion(Empleado empleado, Profesion profesion) {
		this.empleado = empleado;
		this.profesion = profesion;
	}

	public int getIdProfesionEmpleado() {
		return idProfesionEmpleado;
	}

	public void setIdProfesionEmpleado(int idProfesionEmpleado) {
		this.idProfesionEmpleado = idProfesionEmpleado;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Profesion getProfesion() {
		return profesion;
	}

	public void setProfesion(Profesion profesion) {
		this.profesion = profesion;
	}
}
