package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "planilla_movimientos")
public class PlanillaMovimiento {
	
	@Id
	@GeneratedValue
	@Column(name = "id_planilla_movimiento")
	private int idPlaillaMovimiento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_planilla", nullable = false)
	private Planilla planilla;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimiento", nullable = false)
	private TipoMovimiento tipoMovimiento;

	public PlanillaMovimiento() {
	}
	
	public PlanillaMovimiento(int idPlaillaMovimiento, Planilla planilla, TipoMovimiento tipoMovimiento) {
		super();
		this.idPlaillaMovimiento = idPlaillaMovimiento;
		this.planilla = planilla;
		this.tipoMovimiento = tipoMovimiento;
	}

	public int getIdPlaillaMovimiento() {
		return idPlaillaMovimiento;
	}

	public void setIdPlaillaMovimiento(int idPlaillaMovimiento) {
		this.idPlaillaMovimiento = idPlaillaMovimiento;
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
