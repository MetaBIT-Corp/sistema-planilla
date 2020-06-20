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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="dias_festivos")
public class DiaFestivo {

	@Id
	@GeneratedValue
	@Column(name = "id_dia_festivo")
	private int idDiaFestivo;
	
	@NotNull
	@Size(min= 1, max = 250, message="La descripción del día festivo es obligatoria (máximo de 250 caracteres)")
	@Column(name = "dia_descripcion")
	private String diaDescripcion;
	
	@NotNull
	@Min(1)
	@Max(31)
	@Column(name = "dia")
	private int dia;
	
	@NotNull
	@Min(1)
	@Max(12)
	@Column(name = "mes")
	private int mes;
	
	@OneToMany(mappedBy = "diaFestivo", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnore
    private List<PlanillaDiaFestivo> planillaDiaFestivo =  new ArrayList<>();
    
	public DiaFestivo() {
		super();
	}

	/**
	 * @param idDiaFestivo
	 * @param diaDescripcion
	 * @param dia
	 * @param mes
	 * @param planillaDiaFestivo
	 */
	public DiaFestivo(int idDiaFestivo,
			@NotNull @Size(min = 1, max = 250, message = "La descripción del día festivo es obligatoria (máximo de 250 caracteres)") String diaDescripcion,
			@NotNull @Min(1) @Max(31) int dia, @NotNull @Min(1) @Max(12) int mes) {
		super();
		this.idDiaFestivo = idDiaFestivo;
		this.diaDescripcion = diaDescripcion;
		this.dia = dia;
		this.mes = mes;
	}
	

	public int getIdDiaFestivo() {
		return idDiaFestivo;
	}

	public void setIdDiaFestivo(int idDiaFestivo) {
		this.idDiaFestivo = idDiaFestivo;
	}

	public String getDiaDescripcion() {
		return diaDescripcion;
	}

	public void setDiaDescripcion(String diaDescripcion) {
		this.diaDescripcion = diaDescripcion;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}
	
	public List<PlanillaDiaFestivo> getPlanillaDiaFestivo() {
		return planillaDiaFestivo;
	}

	public void setPlanillaDiaFestivo(List<PlanillaDiaFestivo> planillaDiaFestivo) {
		this.planillaDiaFestivo = planillaDiaFestivo;
	}
	
	@Override
	public String toString() {
		return "DiaFestivo [idDiaFestivo=" + idDiaFestivo + ", diaDescripcion=" + diaDescripcion + ", dia=" + dia
				+ ", mes=" + mes +"]";
	}
}
