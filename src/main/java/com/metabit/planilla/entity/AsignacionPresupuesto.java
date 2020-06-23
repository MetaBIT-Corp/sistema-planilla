package com.metabit.planilla.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asignaciones_presupuestos")
public class AsignacionPresupuesto {
    @Id
    @GeneratedValue
    @Column(name = "id_asignacion_presupuesto", nullable = false)
    private int id_asignacion;

    @Column(name = "monto_asignacion", nullable = false)
    private double montoAsignacion;

    @Column(name = "es_incremento", nullable = false)
    private Boolean esIncremento;

    @Column(name = "fecha_asignacion", nullable = true)
    private LocalDate fechaAsignacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_centro_costo")
    private CentroCosto centroCosto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    public AsignacionPresupuesto(){}
    public AsignacionPresupuesto(double montoAsignacion, Boolean esIncremento, LocalDate fechaAsignacion, CentroCosto centroCosto, Usuario usuario) {
        this.montoAsignacion = montoAsignacion;
        this.esIncremento = esIncremento;
        this.fechaAsignacion = fechaAsignacion;
        this.centroCosto = centroCosto;
        this.usuario = usuario;
    }

    public int getId_asignacion() {
        return id_asignacion;
    }

    public void setId_asignacion(int id_asignacion) {
        this.id_asignacion = id_asignacion;
    }

    public double getMontoAsignacion() {
        return montoAsignacion;
    }

    public void setMontoAsignacion(double montoAsignacion) {
        this.montoAsignacion = montoAsignacion;
    }

    public Boolean getEsIncremento() {
        return esIncremento;
    }

    public void setEsIncremento(Boolean esIncremento) {
        this.esIncremento = esIncremento;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public CentroCosto getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(CentroCosto centroCosto) {
        this.centroCosto = centroCosto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
