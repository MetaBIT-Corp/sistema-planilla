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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="roles")
public class Rol {
	
	@Id
	@GeneratedValue
	@Column(name = "id_rol", unique = true, nullable = false)
	private Integer idRol;
	
	@Column(name = "authority", nullable = false)
	private String authority;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Usuario> usuarios = new HashSet<Usuario>();
	
	@OneToMany(mappedBy = "rol", fetch = FetchType.EAGER)
	@JsonIgnore
    private List<RolRecursoPrivilegio> rolesRecursosPrivilegios =  new ArrayList<>();
	
	public Rol() {}

	public Rol(Integer idRol, String authority) {
		this.idRol = idRol;
		this.authority = authority;
	}

	public Rol(Integer idRol, String authority, Set<Usuario> usuarios, List<RolRecursoPrivilegio> rolesRecursosPrivilegios) {
		super();
		this.idRol = idRol;
		this.authority = authority;
		this.usuarios = usuarios;
		this.rolesRecursosPrivilegios = rolesRecursosPrivilegios;
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<RolRecursoPrivilegio> getRolesRecursosPrivilegios() {
		return rolesRecursosPrivilegios;
	}

	public void setRolesRecursosPrivilegios(List<RolRecursoPrivilegio> rolesRecursosPrivilegios) {
		this.rolesRecursosPrivilegios = rolesRecursosPrivilegios;
	}
	
}
