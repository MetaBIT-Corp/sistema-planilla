package com.metabit.planilla.entity;

import javax.persistence.*;

@Entity
@Table(name = "centros_costos")
public class CentroCosto {

    @Id
    @GeneratedValue
    @Column(name = "id_centro_costo")
    private int id_centro_costo;

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

    public CentroCosto(){}
    public CentroCosto(int id_centro_costo, double presupuestoAsignado, double presupuestoDevengado, double presupuestoAnterior, UnidadOrganizacional unidadOrganizacional) {
        this.id_centro_costo = id_centro_costo;
        this.presupuestoAsignado = presupuestoAsignado;
        this.presupuestoDevengado = presupuestoDevengado;
        this.presupuestoAnterior = presupuestoAnterior;
        this.unidadOrganizacional = unidadOrganizacional;
    }

    public int getId_centro_costo() {
        return id_centro_costo;
    }

    public void setId_centro_costo(int id_centro_costo) {
        this.id_centro_costo = id_centro_costo;
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
    
    
}
