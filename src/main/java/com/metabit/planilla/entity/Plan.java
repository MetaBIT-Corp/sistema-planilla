package com.metabit.planilla.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "planes")
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_plan", nullable = false, unique = true)
	private int idPlan;
	
	@Column(name = "plan", nullable = false)
	private String plan;
	
	@Column(name = "monto_plan", nullable = false)
	private float montoPlan;
	
	@Column(name = "tasa_interes", nullable = false)
	private float tasaInteres;
	
	@Column(name = "periodicidad_plan", nullable = false)
	private int periodicidadPlan;
	
	@Column(name = "plazo", nullable = false)
	private int plazo;
	
	@Column(name = "monto_cuota", nullable = false)
	private float montoCuota;
	
	@Column(name = "es_activo", nullable = false)
	private boolean esActivo;
	
	@Column(name = "es_egreso", nullable = false)
	private boolean esEgreso;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_empleado", nullable = false)
	private Empleado empleado;
	
	public int getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(int idPlan) {
		this.idPlan = idPlan;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public float getMontoPlan() {
		return montoPlan;
	}

	public void setMontoPlan(float montoPlan) {
		this.montoPlan = montoPlan;
	}

	public float getTasaInteres() {
		return tasaInteres;
	}

	public void setTasaInteres(float tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	public int getPeriodicidadPlan() {
		return periodicidadPlan;
	}

	public void setPeriodicidadPlan(int periodicidadPlan) {
		this.periodicidadPlan = periodicidadPlan;
	}

	public int getPlazo() {
		return plazo;
	}

	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}

	public float getMontoCuota() {
		return montoCuota;
	}

	public void setMontoCuota(float montoCuota) {
		this.montoCuota = montoCuota;
	}

	public boolean isEsActivo() {
		return esActivo;
	}

	public void setEsActivo(boolean esActivo) {
		this.esActivo = esActivo;
	}

	public boolean isEsEgreso() {
		return esEgreso;
	}

	public void setEsEgreso(boolean esEgreso) {
		this.esEgreso = esEgreso;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Plan(int idPlan, String plan, float montoPlan, float tasaInteres, int periodicidadPlan, int plazo,
			float montoCuota, boolean esActivo, boolean esEgreso, Empleado empleado) {
		super();
		this.idPlan = idPlan;
		this.plan = plan;
		this.montoPlan = montoPlan;
		this.tasaInteres = tasaInteres;
		this.periodicidadPlan = periodicidadPlan;
		this.plazo = plazo;
		this.montoCuota = montoCuota;
		this.esActivo = esActivo;
		this.esEgreso = esEgreso;
		this.empleado = empleado;
	}
	
	public Plan() {}

	@Override
	public String toString() {
		return "Plan [idPlan=" + idPlan + ", plan=" + plan + ", montoPlan=" + montoPlan + ", tasaInteres=" + tasaInteres
				+ ", periodicidadPlan=" + periodicidadPlan + ", plazo=" + plazo + ", montoCuota=" + montoCuota
				+ ", esActivo=" + esActivo + ", esEgreso=" + esEgreso + ", empleado=" + empleado + "]";
	}
	
	
}
