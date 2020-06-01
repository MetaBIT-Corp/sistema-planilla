package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rangos_comision")
public class RangoComision {

	@Id
	@GeneratedValue
	@Column(name = "id_rango_comision", nullable = false)
	private int id;

	@Column(name = "venta_min", nullable = false)
	private float ventaMin;

	@Column(name = "venta_max", nullable = false)
	private float ventaMax;

	@Column(name = "tasa_comision", nullable = false)
	private float tasaComision;

	@Column(name = "rango_comision_habilitado", nullable = false)
	private float habilitado;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getVentaMin() {
		return ventaMin;
	}

	public void setVentaMin(float ventaMin) {
		this.ventaMin = ventaMin;
	}

	public float getVentaMax() {
		return ventaMax;
	}

	public void setVentaMax(float ventaMax) {
		this.ventaMax = ventaMax;
	}

	public float getTasaComision() {
		return tasaComision;
	}

	public void setTasaComision(float tasaComision) {
		this.tasaComision = tasaComision;
	}

	public float getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(float habilitado) {
		this.habilitado = habilitado;
	}

	public RangoComision(int id, float ventaMin, float ventaMax, float tasaComision, float habilitado) {
		super();
		this.id = id;
		this.ventaMin = ventaMin;
		this.ventaMax = ventaMax;
		this.tasaComision = tasaComision;
		this.habilitado = habilitado;
	}

	public RangoComision() {

	}

}
