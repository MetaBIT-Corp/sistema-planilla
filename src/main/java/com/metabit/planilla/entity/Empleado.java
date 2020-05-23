package com.metabit.planilla.entity;

import java.time.LocalDate;
import java.util.ArrayList;
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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "empleados")
public class Empleado {
	
	@Id
	@GeneratedValue
	@Column(name = "id_empleado", nullable = false)
	private int idEmpleado;
	
	@NotNull(message = "Debe especificar codigo de empleado.")
	@Column(name = "codigo", nullable = false, unique = true)
	private String codigo;
	
	@NotNull(message = "Debe especificar nombre.")
	@Column(name = "nombre_primero", nullable = false)
	private String nombrePrimero;

	@Column(name = "nombre_segundo")
	private String nombreSegundo;
	
	
	@Column(name = "apellido_paterno")
	private String apellidoPaterno;
	
	@NotNull(message = "Debe especificar el apellido materno.")
	@Column(name = "apellido_materno", nullable = false )
	private String apellidoMaterno;
	
	
	@Column(name = "apellido_casada")
	private String apellidoCasada;
	
	@NotNull(message = "Debe especificar fecha de nacimiento.")
	@Column(name = "fecha_nacimiento", nullable = false, columnDefinition="DATE")
	private LocalDate fechaNacimiento;

	@Column(name = "correo_personal", unique = true)
	private String correoPersonal;

	@Column(name = "correo_institucional", nullable = false, unique = true)
	private String correoInstitucional;
	
	@NotNull(message = "Debe especificar el salario base.")
	@Column(name = "salario_base_mensual", nullable = false)
	private double salarioBaseMensual;
	
	@NotNull(message = "Debe especificar las horas de trabajo.")
	@Column(name = "horas_trabajo", nullable = false)
	private int horasTrabajo;

	@Column(name = "es_administrativo")
	private Boolean esAdministrativo;

	@Column(name = "empleado_habilitado")
	private Boolean empleadoHabilitado;
	
	//Personals Documents
	@OneToMany(mappedBy="empleado",cascade=CascadeType.ALL)
	private List<EmpleadoDocumento> documentosEmpleado=new ArrayList<>();
	
	//User
	@OneToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
		
	//Gender
	@ManyToOne
	@JoinColumn(name="id_genero")
	private Genero genero;
	
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

	public Empleado() {
		super();
	}

	public Empleado(String codigo, String nombrePrimero, String nombreSegundo, String apellidoPaterno, String apellidoMaterno, String apellidoCasada, LocalDate fechaNacimiento, String correoPersonal, String correoInstitucional, double salarioBaseMensual, int horasTrabajo, Boolean esAdministrativo, Boolean empleadoHabilitado, List<EmpleadoDocumento> documentosEmpleado, Usuario usuario, EstadoCivil estadoCivil, Direccion direccion, List<EmpleadoProfesion> profesionesEmpleado,Genero genero) {
		this.codigo = codigo;
		this.nombrePrimero = nombrePrimero;
		this.nombreSegundo = nombreSegundo;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.apellidoCasada = apellidoCasada;
		this.fechaNacimiento = fechaNacimiento;
		this.correoPersonal = correoPersonal;
		this.correoInstitucional = correoInstitucional;
		this.salarioBaseMensual = salarioBaseMensual;
		this.horasTrabajo = horasTrabajo;
		this.esAdministrativo = esAdministrativo;
		this.empleadoHabilitado = empleadoHabilitado;
		this.documentosEmpleado = documentosEmpleado;
		this.usuario = usuario;
		this.estadoCivil = estadoCivil;
		this.direccion = direccion;
		this.profesionesEmpleado = profesionesEmpleado;
		this.genero=genero;
	}

	public String getCorreoInstitucional() {
		return correoInstitucional;
	}

	public void setCorreoInstitucional(String correoInstitucional) {
		this.correoInstitucional = correoInstitucional;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombrePrimero() {
		return nombrePrimero;
	}

	public void setNombrePrimero(String nombrePrimero) {
		this.nombrePrimero = nombrePrimero;
	}

	public String getNombreSegundo() {
		return nombreSegundo;
	}

	public void setNombreSegundo(String nombreSegundo) {
		this.nombreSegundo = nombreSegundo;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getApellidoCasada() {
		return apellidoCasada;
	}

	public void setApellidoCasada(String apellidoCasada) {
		this.apellidoCasada = apellidoCasada;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCorreoPersonal() {
		return correoPersonal;
	}

	public void setCorreoPersonal(String correoPersonal) {
		this.correoPersonal = correoPersonal;
	}

	public double getSalarioBaseMensual() {
		return salarioBaseMensual;
	}

	public void setSalarioBaseMensual(double salarioBaseMensual) {
		this.salarioBaseMensual = salarioBaseMensual;
	}

	public int getHorasTrabajo() {
		return horasTrabajo;
	}

	public void setHorasTrabajo(int horasTrabajo) {
		this.horasTrabajo = horasTrabajo;
	}

	public Boolean getEsAdministrativo() {
		return esAdministrativo;
	}

	public void setEsAdministrativo(Boolean esAdministrativo) {
		this.esAdministrativo = esAdministrativo;
	}

	public Boolean getEmpleadoHabilitado() {
		return empleadoHabilitado;
	}

	public void setEmpleadoHabilitado(Boolean empleadoHabilitado) {
		this.empleadoHabilitado = empleadoHabilitado;
	}

	public List<EmpleadoDocumento> getDocumentosEmpleado() {
		return documentosEmpleado;
	}

	public void setDocumentosEmpleado(List<EmpleadoDocumento> documentosEmpleado) {
		this.documentosEmpleado = documentosEmpleado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public List<EmpleadoProfesion> getProfesionesEmpleado() {
		return profesionesEmpleado;
	}

	public void setProfesionesEmpleado(List<EmpleadoProfesion> profesionesEmpleado) {
		this.profesionesEmpleado = profesionesEmpleado;
	}
}