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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tipos_unidad_organizacional")
public class TipoUnidadOrganizacional {

	@Id
	@GeneratedValue
	@Column(name = "id_tipo_unidad_organizacional", unique=true, nullable=false)
	private Integer idTipoUnidadOrganizacional;
	
	@Column(name = "tipo_unidad_organizacional", unique = true, nullable = false)
	private String tipoUnidadOrganizacional;
	
	@Column(name = "tipo_unidad_organizacional_habilidato", nullable = false)
	private Boolean tipoUnidadOrganizacionalHabilitado;
	
	@Column(name = "nivel_jerarquico", nullable = false)
	private Integer nivelJerarquico;
	
	@OneToMany(mappedBy = "tipoUnidadOrganizacional", fetch = FetchType.EAGER)
	@JsonIgnore
    private List<UnidadOrganizacional> unidades_organizacional =  new ArrayList<>();

	public TipoUnidadOrganizacional() {
	}

	public TipoUnidadOrganizacional(Integer idTipoUnidadOrganizacional, String tipoUnidadOrganizacional,
			Boolean tipoUnidadOrganizacionalHabilitado, Integer nivelJerarquico,
			List<UnidadOrganizacional> unidades_organizacional) {
		super();
		this.idTipoUnidadOrganizacional = idTipoUnidadOrganizacional;
		this.tipoUnidadOrganizacional = tipoUnidadOrganizacional;
		this.tipoUnidadOrganizacionalHabilitado = tipoUnidadOrganizacionalHabilitado;
		this.nivelJerarquico = nivelJerarquico;
		this.unidades_organizacional = unidades_organizacional;
	}

	public Integer getIdTipoUnidadOrganizacional() {
		return idTipoUnidadOrganizacional;
	}

	public void setIdTipoUnidadOrganizacional(Integer idTipoUnidadOrganizacional) {
		this.idTipoUnidadOrganizacional = idTipoUnidadOrganizacional;
	}

	public String getTipoUnidadOrganizacional() {
		return tipoUnidadOrganizacional;
	}

	public void setTipoUnidadOrganizacional(String tipoUnidadOrganizacional) {
		this.tipoUnidadOrganizacional = tipoUnidadOrganizacional;
	}

	public Boolean getTipoUnidadOrganizacionalHabilitado() {
		return tipoUnidadOrganizacionalHabilitado;
	}

	public void setTipoUnidadOrganizacionalHabilitado(Boolean tipoUnidadOrganizacionalHabilitado) {
		this.tipoUnidadOrganizacionalHabilitado = tipoUnidadOrganizacionalHabilitado;
	}

	public Integer getNivelJerarquico() {
		return nivelJerarquico;
	}

	public void setNivelJerarquico(Integer nivelJerarquico) {
		this.nivelJerarquico = nivelJerarquico;
	}

	public List<UnidadOrganizacional> getUnidades_organizacional() {
		return unidades_organizacional;
	}

	public void setUnidades_organizacional(List<UnidadOrganizacional> unidades_organizacional) {
		this.unidades_organizacional = unidades_organizacional;
	}
	
	
}
