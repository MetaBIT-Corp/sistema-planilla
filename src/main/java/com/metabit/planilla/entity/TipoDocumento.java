package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tipos_documento")
public class TipoDocumento {
	
	@Id
	@GeneratedValue
	@Column(name = "id_tipo_documento", nullable = false)
	private int idTipoDocumento;
	
	@NotNull
	@Column(name = "tipo_documento")
	private String tipoDocumento;
	
	@NotNull
	@Column(name = "formato")
	private String formato;
	
	@NotNull
	@Column(name = "tipo_documento_habilitado")
	private boolean habilitado;
	
	public TipoDocumento() {
		super();
	}

	public TipoDocumento(int idTipoDocumento, String tipoDocumento, String formato, boolean habilitado) {
		super();
		this.idTipoDocumento = idTipoDocumento;
		this.tipoDocumento = tipoDocumento;
		this.formato = formato;
		this.habilitado = habilitado;
	}
	
	
	public int getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(int idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	@Override
	public String toString() {
		return "Tipo Documento [ Tipo Documento=" + tipoDocumento + ", Formato=" + formato + "]";
	}	

}