package com.metabit.planilla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "puestos")
public class Puesto {
	
	@Id
	@GeneratedValue
	@Column(name = "id_puesto", nullable = false)
	private int idPuesto;
	
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "puesto")
	private String puesto;
	
	@NotNull
	@Min(360)
	@Column(name = "salario_minimo")
	private double salarioMinimo;
	
	@NotNull
	@Min(360)
	@Column(name = "salario_maximo")
	private double salarioMaximo;
	
	@Size(min = 0, max = 250)
	@Column(name = "descripcion", nullable = true)
	private String descripcion;
	
	@Column(name = "usuario_requerido")
	private boolean usuarioRequerido;
	
	public Puesto() {
		super();
	}
	
	

	public Puesto(@NotNull String puesto, @NotNull double salarioMinimo, @NotNull double salarioMaximo,
			String descripcion, @NotNull boolean usuarioRequerido) {
		super();
		this.puesto = puesto;
		this.salarioMinimo = salarioMinimo;
		this.salarioMaximo = salarioMaximo;
		this.descripcion = descripcion;
		this.usuarioRequerido = usuarioRequerido;
	}



	public Puesto(int idPuesto, String puesto, double salarioMinimo, double salarioMaximo, String descripcion,
			boolean usuarioRequerido) {
		super();
		this.idPuesto = idPuesto;
		this.puesto = puesto;
		this.salarioMinimo = salarioMinimo;
		this.salarioMaximo = salarioMaximo;
		this.descripcion = descripcion;
		this.usuarioRequerido = usuarioRequerido;
	}
	
	
	public int getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public double getSalarioMinimo() {
		return salarioMinimo;
	}

	public void setSalarioMinimo(double salarioMinimo) {
		this.salarioMinimo = salarioMinimo;
	}

	public double getSalarioMaximo() {
		return salarioMaximo;
	}

	public void setSalarioMaximo(double salarioMaximo) {
		this.salarioMaximo = salarioMaximo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isUsuarioRequerido() {
		return usuarioRequerido;
	}

	public void setUsuarioRequerido(boolean usuarioRequerido) {
		this.usuarioRequerido = usuarioRequerido;
	}

	@Override
	public String toString() {
		return "Puesto [idPuesto=" + idPuesto + ", puesto=" + puesto + ", salarioMinimo=" + salarioMinimo
				+ ", salarioMaximo=" + salarioMaximo + ", descripcion=" + descripcion + ", usuarioRequerido="
				+ usuarioRequerido + "]";
	}

	
	

}