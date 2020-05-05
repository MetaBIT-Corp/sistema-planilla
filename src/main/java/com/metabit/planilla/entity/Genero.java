package com.metabit.planilla.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "generos")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Genero {

	@Id
	@GeneratedValue
	@Column(name = "id_genero", nullable = false)
	private int idGenero;

	@Column(name = "genero", nullable = false)
	private String genero;

	public int getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(int idGenero) {
		this.idGenero = idGenero;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Genero(int idGenero, String genero) {
		super();
		this.idGenero = idGenero;
		this.genero = genero;
	}

	public Genero() {
	}

}
