package com.metabit.planilla.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="departamentos")
public class Departamento {

	@Id
	@GeneratedValue
	@Column(name="id_departamento")
	private int idDepartamento;
	
	@Column(name="departamento")
	private String departamento;
	
	@OneToMany(mappedBy="departamento",cascade=CascadeType.ALL)
	private List<Municipio> municipios=new ArrayList<>();
	

	public int getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(int idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public List<Municipio> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}
}
