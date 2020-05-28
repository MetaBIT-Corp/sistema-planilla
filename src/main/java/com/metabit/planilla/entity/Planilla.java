package com.metabit.planilla.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "planillas")
public class Planilla {
	
	@Id
	@GeneratedValue
	@Column(name = "id_planilla", unique = true, nullable = false)
	private int idPlanilla;
	
	@Column(name = "fecha_emision", nullable = false)
	@JsonFormat(pattern="dd/MM/yy")
	private Date fechaEmision;
	
	@Column(name = "monto_ventas")
	private float montoVentas;
	
	@Column(name = "monto_comision")
	private float montoComision;
	
	@Column(name = "total_descuentos")
	private float totalDescuentos;
	
	@Column(name = "renta")
	private float renta;
	
	@Column(name = "salario_neto")
	private float salarioNeto;
	
	@Column(name = "horas_extra_diurnas")
	private int horasExtraDiurnas;
	
	@Column(name = "horas_extra_nocturnas")
	private int horasExtraNocturnas;
	
	@Column(name = "monto_horas_extra")
	private float montoHorasExtra;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_empleado", nullable = false)
	private Empleado empleado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_periodo", nullable = false)
	private Periodo periodo;
	
	@OneToMany(mappedBy = "planilla", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
    private List<PlanillaMovimiento> planillaMovimientos =  new ArrayList<>();

	public Planilla() {
	}

	public Planilla(int idPlanilla, Date fechaEmision, float montoVentas, float montoComision, float totalDescuentos,
			float renta, float salarioNeto, int horasExtraDiurnas, int horasExtraNocturnas, float montoHorasExtra,
			Empleado empleado, Periodo periodo, List<PlanillaMovimiento> planillaMovimientos) {
		super();
		this.idPlanilla = idPlanilla;
		this.fechaEmision = fechaEmision;
		this.montoVentas = montoVentas;
		this.montoComision = montoComision;
		this.totalDescuentos = totalDescuentos;
		this.renta = renta;
		this.salarioNeto = salarioNeto;
		this.horasExtraDiurnas = horasExtraDiurnas;
		this.horasExtraNocturnas = horasExtraNocturnas;
		this.montoHorasExtra = montoHorasExtra;
		this.empleado = empleado;
		this.periodo = periodo;
		this.planillaMovimientos = planillaMovimientos;
	}

	public int getIdPlanilla() {
		return idPlanilla;
	}

	public void setIdPlanilla(int idPlanilla) {
		this.idPlanilla = idPlanilla;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public float getMontoVentas() {
		return montoVentas;
	}

	public void setMontoVentas(float montoVentas) {
		this.montoVentas = montoVentas;
	}

	public float getMontoComision() {
		return montoComision;
	}

	public void setMontoComision(float montoComision) {
		this.montoComision = montoComision;
	}

	public float getTotalDescuentos() {
		return totalDescuentos;
	}

	public void setTotalDescuentos(float totalDescuentos) {
		this.totalDescuentos = totalDescuentos;
	}

	public float getRenta() {
		return renta;
	}

	public void setRenta(float renta) {
		this.renta = renta;
	}

	public float getSalarioNeto() {
		return salarioNeto;
	}

	public void setSalarioNeto(float salarioNeto) {
		this.salarioNeto = salarioNeto;
	}

	public int getHorasExtraDiurnas() {
		return horasExtraDiurnas;
	}

	public void setHorasExtraDiurnas(int horasExtraDiurnas) {
		this.horasExtraDiurnas = horasExtraDiurnas;
	}

	public int getHorasExtraNocturnas() {
		return horasExtraNocturnas;
	}

	public void setHorasExtraNocturnas(int horasExtraNocturnas) {
		this.horasExtraNocturnas = horasExtraNocturnas;
	}

	public float getMontoHorasExtra() {
		return montoHorasExtra;
	}

	public void setMontoHorasExtra(float montoHorasExtra) {
		this.montoHorasExtra = montoHorasExtra;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public List<PlanillaMovimiento> getPlanillaMovimientos() {
		return planillaMovimientos;
	}

	public void setPlanillaMovimientos(List<PlanillaMovimiento> planillaMovimientos) {
		this.planillaMovimientos = planillaMovimientos;
	}
	
}
