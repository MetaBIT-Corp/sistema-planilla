package com.metabit.planilla.entity;

import java.time.LocalDate;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "periodos")
public class Periodo {
	
	@Id
	@GeneratedValue
	@Column(name = "id_periodo", unique = true, nullable = false)
	private int idPeriodo;
	
	@Column(name = "fecha_inicio", nullable = false)
	@JsonFormat(pattern="dd/MM/yy")
	private LocalDate fechaInicio;
	
	@Column(name = "fecha_final", nullable = false)
	@JsonFormat(pattern="dd/MM/yy")
	private LocalDate fechaFinal;
	
	@Column(name = "activo", nullable = false)
	private boolean activo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_anio_laboral", nullable = false)
	private AnioLaboral anioLaboral;
	
	@OneToMany(mappedBy = "periodo", fetch = FetchType.EAGER)
	@JsonIgnore
    private List<Planilla> planillas =  new ArrayList<>();

	public Periodo() {
	}

	public Periodo(int idPeriodo, LocalDate fechaInicio, LocalDate fechaFinal, boolean activo, AnioLaboral anioLaboral,
			List<Planilla> planillas) {
		super();
		this.idPeriodo = idPeriodo;
		this.fechaInicio = fechaInicio;
		this.fechaFinal = fechaFinal;
		this.activo = activo;
		this.anioLaboral = anioLaboral;
		this.planillas = planillas;
	}

	public int getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(LocalDate fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public AnioLaboral getAnioLaboral() {
		return anioLaboral;
	}

	public void setAnioLaboral(AnioLaboral anioLaboral) {
		this.anioLaboral = anioLaboral;
	}

	public List<Planilla> getPlanillas() {
		return planillas;
	}

	public void setPlanillas(List<Planilla> planillas) {
		this.planillas = planillas;
	}
	
}
