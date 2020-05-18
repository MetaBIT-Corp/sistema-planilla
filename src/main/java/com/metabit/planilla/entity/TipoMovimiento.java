package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tipos_movimiento")
public class TipoMovimiento {
	
	@Id
	@GeneratedValue
	@Column(name = "id_movimiento")
	private int idMovimiento;
	
	@NotNull
	@Size(min= 1, max = 250, message="El título de movimiento es obligatorio (máximo de 250 caracteres)")
	@Column(name = "movimiento", nullable = false)
	private String movimiento;
	
	@Column(name = "porcentaje_movimiento", columnDefinition="Decimal(5,2) default '0.00'")
	private double porcentajeMovimiento;
	
	@Column(name = "monto_base", columnDefinition="Decimal(10,2) default '0.00'")
	private double montoBase;
	
	@Column(name = "tipo_movimiento_habilitado", nullable = false)
	private boolean tipoMovimientoHabilitado;
	
	@Column(name = "es_descuento", nullable = false)
	private boolean esDescuento;
	
	@Column(name = "es_fijo", nullable = false)
	private boolean esFijo;

	public TipoMovimiento() {
		super();
	}

	public TipoMovimiento(
			@NotNull @Size(min = 1, max = 250, message = "El título de movimiento es obligatorio (máximo de 250 caracteres)") String movimiento,
			double porcentajeMovimiento, double montoBase, boolean tipoMovimientoHabilitado, boolean esDescuento,
			boolean esFijo) {
		super();
		this.movimiento = movimiento;
		this.porcentajeMovimiento = porcentajeMovimiento;
		this.montoBase = montoBase;
		this.tipoMovimientoHabilitado = tipoMovimientoHabilitado;
		this.esDescuento = esDescuento;
		this.esFijo = esFijo;
	}

	public TipoMovimiento(int idMovimiento,
			@NotNull @Size(min = 1, max = 250, message = "El título de movimiento es obligatorio (máximo de 250 caracteres)") String movimiento,
			double porcentajeMovimiento, double montoBase, boolean tipoMovimientoHabilitado, boolean esDescuento,
			boolean esFijo) {
		super();
		this.idMovimiento = idMovimiento;
		this.movimiento = movimiento;
		this.porcentajeMovimiento = porcentajeMovimiento;
		this.montoBase = montoBase;
		this.tipoMovimientoHabilitado = tipoMovimientoHabilitado;
		this.esDescuento = esDescuento;
		this.esFijo = esFijo;
	}

	public int getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(int idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public String getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}

	public double getPorcentajeMovimiento() {
		return porcentajeMovimiento;
	}

	public void setPorcentajeMovimiento(double porcentajeMovimiento) {
		this.porcentajeMovimiento = porcentajeMovimiento;
	}

	public double getMontoBase() {
		return montoBase;
	}

	public void setMontoBase(double montoBase) {
		this.montoBase = montoBase;
	}

	public boolean isTipoMovimientoHabilitado() {
		return tipoMovimientoHabilitado;
	}

	public void setTipoMovimientoHabilitado(boolean tipoMovimientoHabilitado) {
		this.tipoMovimientoHabilitado = tipoMovimientoHabilitado;
	}

	public boolean isEsDescuento() {
		return esDescuento;
	}

	public void setEsDescuento(boolean esDescuento) {
		this.esDescuento = esDescuento;
	}

	public boolean isEsFijo() {
		return esFijo;
	}

	public void setEsFijo(boolean esFijo) {
		this.esFijo = esFijo;
	}

	@Override
	public String toString() {
		return "TipoMovimiento [idMovimiento=" + idMovimiento + ", movimiento=" + movimiento + ", porcentajeMovimiento="
				+ porcentajeMovimiento + ", montoBase=" + montoBase + ", tipoMovimientoHabilitado="
				+ tipoMovimientoHabilitado + ", esDescuento=" + esDescuento + ", esFijo=" + esFijo + "]";
	}
	
}
