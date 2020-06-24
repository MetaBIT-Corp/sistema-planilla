package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="direcciones")
public class Direccion {
	
	@Id
	@GeneratedValue
	@Column(name="id_direccion")
	private int idDireccion;
	
	@NotNull(message = "Debe especificar urbanizacion")
	@Column(name="urbanizacion")
	private String urbanizacion;
	
	@Column(name="calle")
	private String calle;
	
	@Column(name="numero_casa")
	private String numeroCasa;
	
	@Column(name="complemento")
	private String complemento;
	
	@ManyToOne
	@JoinColumn(name="id_municipio")
	private Municipio municipio;
	
	public Direccion() {}

	public Direccion(String urbanizacion, String calle, String numeroCasa, String complemento,
			Municipio municipio) {
		super();
		this.urbanizacion = urbanizacion;
		this.calle = calle;
		this.numeroCasa = numeroCasa;
		this.complemento = complemento;
		this.municipio = municipio;
	}

	public int getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(int idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getUrbanizacion() {
		return urbanizacion;
	}

	public void setUrbanizacion(String urbanizacion) {
		this.urbanizacion = urbanizacion;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumeroCasa() {
		return numeroCasa;
	}

	public void setNumeroCasa(String numeroCasa) {
		this.numeroCasa = numeroCasa;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	@Override
	public String toString() {
		return urbanizacion+", "+calle+", "+numeroCasa+". Complemento: "+complemento+".";
	}
}
