package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "rangos_renta")
public class RangoRenta {
	
	@Id
	@GeneratedValue
	@Column(name = "id_rango_renta")
	private int idRangoRenta;
	
	@Min(value = 0, message = "El salario mínimo no puede ser negativo")
	@Column(name = "salario_min", nullable = false)
	private float salarioMin;
	
	@Min(value = 0, message = "El salario máximo no puede ser negativo")
	@Column(name = "salario_max", nullable = true)
	private float salarioMax;
	
	@Min(value = 0, message = "El porcentaje de renta no puede ser negativo")
	@Max(value = 100, message = "El porcentaje de renta no puede ser mayor a 100")
	@Column(name = "porcentaje_renta", nullable = false)
	private float porcentajeRenta;
	
	@Min(value = 0, message = "El exceso de renta no puede ser negativo")
	@Column(name = "exceso", nullable = false)
	private float exceso;
	
	@Min(value = 0, message = "La cuota fija no puede ser negativa")
	@Column(name = "cuota_fija", nullable = false)
	private float cuotaFija;
	
	//@Pattern(regexp = "15|30", message = "La peridicidad solo puede ser mensual o quincenal")
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
