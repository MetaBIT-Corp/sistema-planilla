package com.metabit.planilla.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "privilegios")
public class Privilegio {
	
	@Id
	@GeneratedValue
	@Column(name = "id_privilegio", unique = true, nullable = false)
	private Integer idPrivilegio;
	
	@Column(name = "privilegio")
	private String privilegio;
	
	@OneToMany(mappedBy = "privilegio", fetch = FetchType.LAZY)
	@JsonIgnore
    private List<RolesRecursosPrivilegios> rolesRecursosPrivilegios =  new ArrayList<>();
	
	public Privilegio() {}

	public Privilegio(Integer idPrivilegio, String privilegio,
			List<RolesRecursosPrivilegios> rolesRecursosPrivilegios) {
		super();
		this.idPrivilegio = idPrivilegio;
		this.privilegio = privilegio;
		this.rolesRecursosPrivilegios = rolesRecursosPrivilegios;
	}

	public Integer getIdPrivilegio() {
		return idPrivilegio;
	}

	public void setIdPrivilegio(Integer idPrivilegio) {
		this.idPrivilegio = idPrivilegio;
	}

	public String getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(String privilegio) {
		this.privilegio = privilegio;
	}

	public List<RolesRecursosPrivilegios> getRolesRecursosPrivilegios() {
		return rolesRecursosPrivilegios;
	}

	public void setRolesRecursosPrivilegios(List<RolesRecursosPrivilegios> rolesRecursosPrivilegios) {
		this.rolesRecursosPrivilegios = rolesRecursosPrivilegios;
	}

}
