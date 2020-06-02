package com.metabit.planilla.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "anios_laborales")
public class AnioLaboral {
	
	@Id
	@GeneratedValue
	@Column(name = "id_anio_laboral", unique = true, nullable = false)
	private int idAnioLaboral;
	
	@Column(name = "anio_laboral", unique = true, nullable = false)
	private int anioLaboral;
	
	@Column(name = "periodicidad", nullable = false)
	private int periodicidad;
	
	@OneToMany(mappedBy = "anioLaboral", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
    private List<Periodo> periodos =  new ArrayList<Periodo>();
	
	@OneToMany(mappedBy = "anioLaboral", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
    private List<CentroCosto> centrosCostos =  new ArrayList<>();

	public AnioLaboral() {
	}

	public AnioLaboral(int idAnioLaboral, int anioLaboral, int periodicidad, List<Periodo> periodos,
			List<CentroCosto> centrosCostos) {
		super();
		this.idAnioLaboral = idAnioLaboral;
		this.anioLaboral = anioLaboral;
		this.periodicidad = periodicidad;
		this.periodos = periodos;
		this.centrosCostos = centrosCostos;
	}

	public int getIdAnioLaboral() {
		return idAnioLaboral;
	}

	public void setIdAnioLaboral(int idAnioLaboral) {
		this.idAnioLaboral = idAnioLaboral;
	}

	public int getAnioLaboral() {
		return anioLaboral;
	}

	public void setAnioLaboral(int anioLaboral) {
		this.anioLaboral = anioLaboral;
	}

	public int getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(int periodicidad) {
		this.periodicidad = periodicidad;
	}

	public List<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<Periodo> periodos) {
		this.periodos = periodos;
	}

	public List<CentroCosto> getCentrosCostos() {
		return centrosCostos;
	}

	public void setCentrosCostos(List<CentroCosto> centrosCostos) {
		this.centrosCostos = centrosCostos;
	}
	
}
