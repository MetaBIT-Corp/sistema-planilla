package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="empleados_documentos")
public class EmpleadoDocumento {

	@Id
	@GeneratedValue
	@Column(name = "id_empleado_documento", nullable = false)
	private int idEmpleadoDocumento;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_tipo_documento")
	private TipoDocumento tipoDocumento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_empleado")
	Empleado empleado;

	
	@NotNull
	@Column(name="codigo_documento", nullable = false)
	private String codigoDocumento;
	
	
	public EmpleadoDocumento() {}

	public EmpleadoDocumento(Empleado empleado,TipoDocumento tipoDocumento, String codigoDocumento) {
		super();
		this.tipoDocumento=tipoDocumento;
		this.empleado=empleado;
		this.codigoDocumento = codigoDocumento;
	}


	public int getIdEmpleadoDocumento() {
		return idEmpleadoDocumento;
	}

	public void setIdEmpleadoDocumento(int idEmpleadoDocumento) {
		this.idEmpleadoDocumento = idEmpleadoDocumento;
	}
	
	public String getCodigoDocumento() {
		return codigoDocumento;
	}

	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
}
