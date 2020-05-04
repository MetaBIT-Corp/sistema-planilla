package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "profesiones")
public class Profesion {
	
	@Id
	@GeneratedValue
	@Column(name = "id_profesion", nullable = false)
	private int idProfesion;
	
	@NotNull
	@Column(name = "profesion")
	private String profesion;
	
	@NotNull
	@Column(name = "es_profesion")
	private boolean esProfesion;
	
	public Profesion() {
		super();
	}

	public Profesion(int idProfesion, String profesion, boolean esProfesion) {
		super();
		this.idProfesion = idProfesion;
		this.profesion = profesion;
		this.esProfesion = esProfesion;
	}
	
	
	public int getIdProfesion() {
		return idProfesion;
	}

	public void setIdProfesion(int idProfesion) {
		this.idProfesion = idProfesion;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public boolean getEsProfesion() {
		return esProfesion;
	}

	public void setEsProfesion(boolean esProfesion) {
		this.esProfesion = esProfesion;
	}

	@Override
	public String toString() {
		return "Profesion [idProfesion=" + idProfesion + ", profesion=" + profesion + ", esProfesion=" + esProfesion + "]";
	}	

}