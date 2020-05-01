package com.metabit.planilla.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "empleados")
public class Empleado {
	
	@Id
	@GeneratedValue
	@Column(name = "id_empleado", nullable = false)
	private int idEmpleado;
	
	@NotNull
	@Column(name = "codigo", nullable = false)
	private String codigo;
	
	@NotNull
	@Column(name = "nombre_primero", nullable = false)
	private String nombrePrimero;
	
	@NotNull
	@Column(name = "nombre_segundo")
	private String nombreSegundo;
	
	
	@Column(name = "apellido_paterno")
	private String apellidoPaterno;
	
	@NotNull
	@Column(name = "apellido_materno", nullable = false)
	private String apellidoMaterno;
	
	
	@Column(name = "apellido_casada")
	private String apellidoCasada;
	
	@NotNull
	@Column(name = "fecha_nacimiento", nullable = false)
	private Date fechaNacimiento;
	
	@NotNull
	@Column(name = "correo_personal", nullable = false)
	private String correoPersonal;
	
	@NotNull
	@Column(name = "salario_base_mensual", nullable = false)
	private double salarioBaseMensual;
	
	@NotNull
	@Column(name = "horas_trabajo", nullable = false)
	private int horasTrabajo;
	
	@NotNull
	@Column(name = "es_administrativo", nullable = false)
	private Boolean esAdministrativo;
	
	@NotNull
	@Column(name = "empleado_habilitado", nullable = false)
	private String empleadoHabilitado;
	
	//Personals Documents
	@OneToMany(mappedBy="empleado",cascade=CascadeType.ALL)
	private List<EmpleadoDocumento> documentosEmpleado=new ArrayList<>();
	
	//User
	@OneToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
		
	//Gender
	/*@ManyToOne
	@JoinColumn(name="id_genero")
	private Genero genero;*/
	
	//Civil State
	@ManyToOne
	@JoinColumn(name="id_estado_civil")
	private EstadoCivil estadoCivil;
	
	//Direction
	@OneToOne
	@JoinColumn(name="id_direccion")
	private Direccion direccion;
	
	//Professions
	@OneToMany(mappedBy="empleado",cascade=CascadeType.ALL)
	private List<EmpleadoProfesion> profesionesEmpleado=new ArrayList<>();
	
	
	
}