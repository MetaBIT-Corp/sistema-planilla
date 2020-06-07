package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rangos_renta")
public class RangoRenta {
	
	@Id
	@GeneratedValue
	@Column(name = "id_rango_renta")
	private int idRangoRenta;
	
	@Column(name = "salario_min", nullable = false)
	private float salarioMin;
	
	@Column(name = "salario_max", nullable = false)
	private float salarioMax;
	
	@Column(name = "porcentaje_renta", nullable = false)
	private float porcentajeRenta;
	
	@Column(name = "exceso", nullable = false)
	private float exceso;
	
	@Column(name = "cuota_fija", nullable = false)
	private float cuotaFija;
	
	@Column(name = "periodicidad_renta", nullable = false)
	private int periodicidadRenta;
	
	@Column(name = "rango_renta_habilitado", nullable = false)
	private boolean rangoRentaHabilitado;

	public RangoRenta() {
	}

	public RangoRenta(int idRangoRenta, float salarioMin, float salarioMax, float porcentajeRenta, float exceso,
			float cuotaFija, int periodicidadRenta, boolean rangoRentaHabilitado) {
		super();
		this.idRangoRenta = idRangoRenta;
		this.salarioMin = salarioMin;
		this.salarioMax = salarioMax;
		this.porcentajeRenta = porcentajeRenta;
		this.exceso = exceso;
		this.cuotaFija = cuotaFija;
		this.periodicidadRenta = periodicidadRenta;
		this.rangoRentaHabilitado = rangoRentaHabilitado;
	}

	public int getIdRangoRenta() {
		return idRangoRenta;
	}

	public void setIdRangoRenta(int idRangoRenta) {
		this.idRangoRenta = idRangoRenta;
	}

	public float getSalarioMin() {
		return salarioMin;
	}

	public void setSalarioMin(float salarioMin) {
		this.salarioMin = salarioMin;
	}

	public float getSalarioMax() {
		return salarioMax;
	}

	public void setSalarioMax(float salarioMax) {
		this.salarioMax = salarioMax;
	}

	public float getPorcentajeRenta() {
		return porcentajeRenta;
	}

	public void setPorcentajeRenta(float porcentajeRenta) {
		this.porcentajeRenta = porcentajeRenta;
	}

	public float getExceso() {
		return exceso;
	}

	public void setExceso(float exceso) {
		this.exceso = exceso;
	}

	public float getCuotaFija() {
		return cuotaFija;
	}

	public void setCuotaFija(float cuotaFija) {
		this.cuotaFija = cuotaFija;
	}

	public int getPeriodicidadRenta() {
		return periodicidadRenta;
	}

	public void setPeriodicidadRenta(int periodicidadRenta) {
		this.periodicidadRenta = periodicidadRenta;
	}

	public boolean isRangoRentaHabilitado() {
		return rangoRentaHabilitado;
	}

	public void setRangoRentaHabilitado(boolean rangoRentaHabilitado) {
		this.rangoRentaHabilitado = rangoRentaHabilitado;
	}
	
}
