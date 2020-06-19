package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "planillas_dias_festivos")
public class PlanillaDiaFestivo {
	@Id
	@GeneratedValue
	@Column(name = "id_planilla_dia_festivo")
	private int idPlanillaDiaFestivo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_planilla", nullable = false)
	private Planilla planilla;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_dia_festivo", nullable = false)
	private DiaFestivo diaFestivo;

	/**
	 * @param idPlanillaDiaFestivo
	 * @param planilla
	 * @param diaFestivo
	 */
	public PlanillaDiaFestivo(int idPlanillaDiaFestivo, Planilla planilla, DiaFestivo diaFestivo) {
		super();
		this.idPlanillaDiaFestivo = idPlanillaDiaFestivo;
		this.planilla = planilla;
		this.diaFestivo = diaFestivo;
	}

	public int getIdPlanillaDiaFestivo() {
		return idPlanillaDiaFestivo;
	}

	public void setIdPlanillaDiaFestivo(int idPlanillaDiaFestivo) {
		this.idPlanillaDiaFestivo = idPlanillaDiaFestivo;
	}

	public Planilla getPlanilla() {
		return planilla;
	}

	public void setPlanilla(Planilla planilla) {
		this.planilla = planilla;
	}

	public DiaFestivo getDiaFestivo() {
		return diaFestivo;
	}

	public void setDiaFestivo(DiaFestivo diaFestivo) {
		this.diaFestivo = diaFestivo;
	}
}
