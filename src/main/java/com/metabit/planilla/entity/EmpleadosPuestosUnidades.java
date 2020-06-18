package com.metabit.planilla.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "empleados_puestos_unidades")
public class EmpleadosPuestosUnidades {
	
	@Id
	@GeneratedValue
	@Column(name = "id_empleado_puesto_unidad", nullable = false)
	private int idEmpleadoPuestoUnidad;

	@ManyToOne(fetch=FetchType.LAZY,optional = false)
	@JoinColumn(name = "id_empleado", nullable = false)
	private Empleado empleado;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_puesto", nullable = false)
	private Puesto puesto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidad_organizacional", nullable = false)
	private UnidadOrganizacional unidadOrganizacional;

	@Column(name = "fecha_inicio", nullable = true)
	private LocalDate fechaInicio;

	@Column(name = "fecha_fin", nullable = true)
	private LocalDate fechaFin;

	public EmpleadosPuestosUnidades() {
		super();
	}

	public EmpleadosPuestosUnidades(@NotNull Empleado empleado, @NotNull Puesto puesto, @NotNull UnidadOrganizacional unidadOrganizacional) {
		super();
		this.empleado = empleado;
		this.puesto = puesto;
		this.unidadOrganizacional = unidadOrganizacional;
	}

	public EmpleadosPuestosUnidades(int idEmpleadoPuestoUnidad, @NotNull Empleado empleado, @NotNull Puesto puesto) {
		super();
		this.idEmpleadoPuestoUnidad = idEmpleadoPuestoUnidad;
		this.empleado = empleado;
		this.puesto = puesto;
	}

	public UnidadOrganizacional getUnidadOrganizacional() {
		return unidadOrganizacional;
	}

	public void setUnidadOrganizacional(UnidadOrganizacional unidadOrganizacional) {
		this.unidadOrganizacional = unidadOrganizacional;
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

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public String toString() {
		return "EmpleadosPuestosUnidades [idEmpleadoPuestoUnidad=" + idEmpleadoPuestoUnidad + ", empleado=" + empleado
				+ ", puesto=" + puesto + "]";
	}
}
