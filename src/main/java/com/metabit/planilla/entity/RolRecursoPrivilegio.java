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
@Table(name = "roles_recursos_privilegios")
public class RolRecursoPrivilegio {
	
	@Id
	@GeneratedValue
	@Column(name = "id_rol_usuario_privilegio", unique = true, nullable = false)
	private Integer idRolRecursoPrivilegio;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_privilegio", nullable = false)
	private Privilegio privilegio;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_recurso", nullable = false)
	private Recurso recurso;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_rol", nullable = false)
	private Rol rol;
	
	public RolRecursoPrivilegio() {}

	public RolRecursoPrivilegio(Integer idRolRecursoPrivilegio, Privilegio privilegio, Recurso recurso, Rol rol) {
		super();
		this.idRolRecursoPrivilegio = idRolRecursoPrivilegio;
		this.privilegio = privilegio;
		this.recurso = recurso;
		this.rol = rol;
	}

	public Integer getIdRolRecursoPrivilegio() {
		return idRolRecursoPrivilegio;
	}

	public void setIdRolRecursoPrivilegio(Integer idRolRecursoPrivilegio) {
		this.idRolRecursoPrivilegio = idRolRecursoPrivilegio;
	}

	public Privilegio getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(Privilegio privilegio) {
		this.privilegio = privilegio;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}
