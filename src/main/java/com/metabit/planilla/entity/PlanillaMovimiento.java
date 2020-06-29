package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "planilla_movimientos")
public class PlanillaMovimiento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pm_generator")
    @SequenceGenerator(name = "pm_generator", sequenceName = "planilla_movimientos_seq",  allocationSize = 1)
	@Column(name = "id_planilla_movimiento")
	private int idPlaillaMovimiento;
	
	@Column(name = "monto_movimiento", nullable = false)
	private float montoMovimiento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_planilla", nullable = false)
	private Planilla planilla;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimiento", nullable = false)
	private TipoMovimiento tipoMovimiento;

	public PlanillaMovimiento() {
	}

	public PlanillaMovimiento(int idPlaillaMovimiento, float montoMovimiento, Planilla planilla,
			TipoMovimiento tipoMovimiento) {
		super();
		this.idPlaillaMovimiento = idPlaillaMovimiento;
		this.montoMovimiento = montoMovimiento;
		this.planilla = planilla;
		this.tipoMovimiento = tipoMovimiento;
	}

	public int getIdPlaillaMovimiento() {
		return idPlaillaMovimiento;
	}

	public void setIdPlaillaMovimiento(int idPlaillaMovimiento) {
		this.idPlaillaMovimiento = idPlaillaMovimiento;
	}

	public float getMontoMovimiento() {
		return montoMovimiento;
	}

	public void setMontoMovimiento(float montoMovimiento) {
		this.montoMovimiento = montoMovimiento;
	}

	public Planilla getPlanilla() {
		return planilla;
	}

	public void setPlanilla(Planilla planilla) {
		this.planilla = planilla;
	}

	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
}
