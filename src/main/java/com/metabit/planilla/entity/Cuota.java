package com.metabit.planilla.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cuotas")
public class Cuota {

	@Id
	@GeneratedValue
	@Column(name = "id_cuota", nullable = false)
	private int idCuota;
	
	@Column(name = "numero_cuota", nullable = false)
	private int numeroCuota;
	
	@Column(name = "fecha_prevista_pago", nullable = false)
	private Date fechaPrevistaPago;
	
	@Column(name = "fecha_real_pago", nullable = false)
	private Date fechaRealPago;
	
	@Column(name = "monto_cancelado", nullable = false)
	private float montoCancelado;
	
	@ManyToOne
	@JoinColumn(name = "id_plan", nullable = false)
	private Plan plan;

	public int getIdCuota() {
		return idCuota;
	}

	public void setIdCuota(int idCuota) {
		this.idCuota = idCuota;
	}

	public int getNumeroCuota() {
		return numeroCuota;
	}

	public void setNumeroCuota(int numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public Date getFechaPrevistaPago() {
		return fechaPrevistaPago;
	}

	public void setFechaPrevistaPago(Date fechaPrevistaPago) {
		this.fechaPrevistaPago = fechaPrevistaPago;
	}

	public Date getFechaRealPago() {
		return fechaRealPago;
	}

	public void setFechaRealPago(Date fechaRealPago) {
		this.fechaRealPago = fechaRealPago;
	}
	
	public float getMontoCancelado() {
		return montoCancelado;
	}

	public void setMontoCancelado(float montoCancelado) {
		this.montoCancelado = montoCancelado;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Cuota(int idCuota, int numeroCuota, Date fechaPrevistaPago, Date fechaRealPago, float montoCancelado, Plan plan) {
		super();
		this.idCuota = idCuota;
		this.numeroCuota = numeroCuota;
		this.fechaPrevistaPago = fechaPrevistaPago;
		this.fechaRealPago = fechaRealPago;
		this.montoCancelado = montoCancelado;
		this.plan = plan;
	}
	
	public Cuota() {}
}
