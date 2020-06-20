package com.metabit.planilla.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="departamentos")
public class Departamento {

	@Id
	@GeneratedValue
	@Column(name="id_departamento")
	private int idDepartamento;
	
	@Column(name="departamento")
	private String departamento;

	@OneToMany(mappedBy="departamento",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
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
