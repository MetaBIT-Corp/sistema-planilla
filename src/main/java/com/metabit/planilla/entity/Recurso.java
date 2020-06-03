package com.metabit.planilla.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "recursos")
public class Recurso {
	
	@Id
	@GeneratedValue
	@Column(name = "id_recurso", unique = true, nullable = false)
	private Integer idRecurso;
	
	@Column(name = "recurso")
	private String recurso;
	
	@OneToMany(mappedBy = "recurso", fetch = FetchType.LAZY)
	@JsonIgnore
    private List<RolRecursoPrivilegio> rolesRecursosPrivilegios =  new ArrayList<>();
	
	public Recurso() {}

	public Recurso(Integer idRecurso, String recurso) {
		this.idRecurso = idRecurso;
		this.recurso = recurso;
	}

	public Recurso(Integer idRecurso, String recurso, List<RolRecursoPrivilegio> rolesRecursosPrivilegios) {
		super();
		this.idRecurso = idRecurso;
		this.recurso = recurso;
		this.rolesRecursosPrivilegios = rolesRecursosPrivilegios;
	}

	public Integer getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public List<RolRecursoPrivilegio> getRolesRecursosPrivilegios() {
		return rolesRecursosPrivilegios;
	}

	public void setRolesRecursosPrivilegios(List<RolRecursoPrivilegio> rolesRecursosPrivilegios) {
		this.rolesRecursosPrivilegios = rolesRecursosPrivilegios;
	}
}
