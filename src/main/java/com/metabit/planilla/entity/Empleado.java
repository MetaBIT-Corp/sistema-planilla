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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
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