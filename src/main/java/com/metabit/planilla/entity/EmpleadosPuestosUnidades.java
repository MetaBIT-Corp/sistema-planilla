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
@Table(name = "empleados_puestos_unidades")
public class EmpleadosPuestosUnidades {
	
	@Id
	@GeneratedValue
	@Column(name = "id_empleado_puesto_unidad", nullable = false)
	private int idEmpleadoPuestoUnidad;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_empleado", nullable = false)
	private Empleado empleado;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_puesto", nullable = false)
	private Puesto puesto;

	public EmpleadosPuestosUnidades() {
		super();
	}

	public EmpleadosPuestosUnidades(@NotNull Empleado empleado, @NotNull Puesto puesto) {
		super();
		this.empleado = empleado;
		this.puesto = puesto;
	}

	public EmpleadosPuestosUnidades(int idEmpleadoPuestoUnidad, @NotNull Empleado empleado, @NotNull Puesto puesto) {
		super();
		this.idEmpleadoPuestoUnidad = idEmpleadoPuestoUnidad;
		this.empleado = empleado;
		this.puesto = puesto;
	}

	public int getIdEmpleadoPuestoUnidad() {
		return idEmpleadoPuestoUnidad;
	}

	public void setIdEmpleadoPuestoUnidad(int idEmpleadoPuestoUnidad) {
		this.idEmpleadoPuestoUnidad = idEmpleadoPuestoUnidad;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	@Override
	public String toString() {
		return "EmpleadosPuestosUnidades [idEmpleadoPuestoUnidad=" + idEmpleadoPuestoUnidad + ", empleado=" + empleado
				+ ", puesto=" + puesto + "]";
	}
}
