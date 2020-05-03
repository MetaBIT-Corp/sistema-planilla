package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="empresas")
public class Empresa {

	@Id
	@GeneratedValue
	@Column(name="id_empresa", nullable = false)
	private int idEmpresa;
	
	@Column(name="empresa", nullable = false)
	private String empresa;
	
	@Column(name="nit_empresa", nullable = false)
	private String nitEmpresa;
	
	@Column(name="nic_empresa", nullable = false)
	private String nicEmpresa;
	
	@Column(name="telefono", nullable = false)
	private String telefono;
	
	@Column(name="pagina_empresa", nullable = false)
	private String paginaEmpresa;
	
	@Column(name="correo_empresa", nullable = false)
	private String correoEmpresa;
	
	@Column(name="page", nullable = false)
	private String page;
	
	@OneToOne
	@JoinColumn(name="id_direccion")
	private Direccion direccion;

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getNitEmpresa() {
		return nitEmpresa;
	}

	public void setNitEmpresa(String nitEmpresa) {
		this.nitEmpresa = nitEmpresa;
	}

	public String getNicEmpresa() {
		return nicEmpresa;
	}

	public void setNicEmpresa(String nicEmpresa) {
		this.nicEmpresa = nicEmpresa;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPaginaEmpresa() {
		return paginaEmpresa;
	}

	public void setPaginaEmpresa(String paginaEmpresa) {
		this.paginaEmpresa = paginaEmpresa;
	}

	public String getCorreoEmpresa() {
		return correoEmpresa;
	}

	public void setCorreoEmpresa(String correoEmpresa) {
		this.correoEmpresa = correoEmpresa;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Empresa(int idEmpresa, String empresa, String nitEmpresa, String nicEmpresa, String telefono,
			String paginaEmpresa, String correoEmpresa, String page, Direccion direccion) {
		super();
		this.idEmpresa = idEmpresa;
		this.empresa = empresa;
		this.nitEmpresa = nitEmpresa;
		this.nicEmpresa = nicEmpresa;
		this.telefono = telefono;
		this.paginaEmpresa = paginaEmpresa;
		this.correoEmpresa = correoEmpresa;
		this.page = page;
		this.direccion = direccion;
	}

	public Empresa() {
	}

}
