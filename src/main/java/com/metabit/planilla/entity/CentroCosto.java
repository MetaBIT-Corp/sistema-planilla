package com.metabit.planilla.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "centros_costos")
public class CentroCosto {

    @Id
    @GeneratedValue
    @Column(name = "id_centro_costo")
    private int idCentroCosto;

    @Column(name = "presupuesto_asignado")
    private double presupuestoAsignado;

    @Column(name = "presupuesto_devengado")
    private double presupuestoDevengado;

    @Column(name = "presupuesto_anterior")
    private double presupuestoAnterior;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_unidad_organizacional")
    private UnidadOrganizacional unidadOrganizacional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_anio")
    private AnioLaboral anioLaboral;

    //Asignaciones de presupuesto
    @JsonIgnore
    @OneToMany(mappedBy="centroCosto",cascade=CascadeType.ALL)
    private List<AsignacionPresupuesto> asignacionesPresupuesto=new ArrayList<>();

    public CentroCosto(){}
    public CentroCosto(int id_centro_costo, double presupuestoAsignado, double presupuestoDevengado, double presupuestoAnterior, UnidadOrganizacional unidadOrganizacional) {
        this.idCentroCosto = idCentroCosto;
        this.presupuestoAsignado = presupuestoAsignado;
        this.presupuestoDevengado = presupuestoDevengado;
        this.presupuestoAnterior = presupuestoAnterior;
        this.unidadOrganizacional = unidadOrganizacional;
    }

    public int getIdCentroCosto() {
        return idCentroCosto;
    }

    public void setIdCentroCosto(int idCentroCosto) {
        this.idCentroCosto = idCentroCosto;
    }

    public double getPresupuestoAsignado() {
        return presupuestoAsignado;
    }

    public void setPresupuestoAsignado(double presupuestoAsignado) {
        this.presupuestoAsignado = presupuestoAsignado;
    }

    public double getPresupuestoDevengado() {
        return presupuestoDevengado;
    }

    public void setPresupuestoDevengado(double presupuestoDevengado) {
        this.presupuestoDevengado = presupuestoDevengado;
    }

    public double getPresupuestoAnterior() {
        return presupuestoAnterior;
    }

    public void setPresupuestoAnterior(double presupuestoAnterior) {
        this.presupuestoAnterior = presupuestoAnterior;
    }

    public UnidadOrganizacional getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    public void setUnidadOrganizacional(UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }
	public AnioLaboral getAnioLaboral() {
		return anioLaboral;
	}
	public void setAnioLaboral(AnioLaboral anioLaboral) {
		this.anioLaboral = anioLaboral;
	}

    public List<AsignacionPresupuesto> getAsignacionesPresupuesto() {
        return asignacionesPresupuesto;
    }

    public void setAsignacionesPresupuesto(List<AsignacionPresupuesto> asignacionesPresupuesto) {
        this.asignacionesPresupuesto = asignacionesPresupuesto;
    }
}
