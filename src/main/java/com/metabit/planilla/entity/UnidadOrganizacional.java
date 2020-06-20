package com.metabit.planilla.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unidades_organizacionales")
public class UnidadOrganizacional {

    @Id
    @GeneratedValue
    @Column(name = "id_unidad_organizacional")
    private int idUnidadOrganizacional;

    @Column(name = "unidad_organizacional",nullable = false)
    private String unidadOrganizacional;

    //Sub Unidades
    @JsonIgnore
    @OneToMany(mappedBy="unidadPadre",cascade=CascadeType.ALL)
    private List<UnidadOrganizacional> subunidades=new ArrayList<>();


    @OneToOne(fetch=FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "id_empleado_jefe")
    private Empleado empleadoJefe;

    //Centros de costos
    @JsonIgnore
    @OneToMany(mappedBy="unidadOrganizacional",cascade=CascadeType.ALL)
    private List<CentroCosto> centroCostos=new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name="id_unidad_organizacional_padre")
    private UnidadOrganizacional unidadPadre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_tipo_unidad_organizacional")
    private TipoUnidadOrganizacional tipoUnidadOrganizacional;

    //Empleado puestos unidades
    @JsonIgnore
    @OneToMany(mappedBy="unidadOrganizacional",cascade=CascadeType.ALL)
    private List<EmpleadosPuestosUnidades> empleadosPuestosUnidades=new ArrayList<>();

    public UnidadOrganizacional(){}
    public UnidadOrganizacional(int idUnidadOrganizacional, String unidadOrganizacional, List<UnidadOrganizacional> subunidades, List<CentroCosto> centroCostos, UnidadOrganizacional unidadPadre) {
        this.idUnidadOrganizacional = idUnidadOrganizacional;
        this.unidadOrganizacional = unidadOrganizacional;
        this.subunidades = subunidades;
        this.centroCostos = centroCostos;
        this.unidadPadre = unidadPadre;
    }

    public List<EmpleadosPuestosUnidades> getEmpleadosPuestosUnidades() {
        return empleadosPuestosUnidades;
    }

    public void setEmpleadosPuestosUnidades(List<EmpleadosPuestosUnidades> empleadosPuestosUnidades) {
        this.empleadosPuestosUnidades = empleadosPuestosUnidades;
    }

    public TipoUnidadOrganizacional getTipoUnidadOrganizacional() {
        return tipoUnidadOrganizacional;
    }

    public void setTipoUnidadOrganizacional(TipoUnidadOrganizacional tipoUnidadOrganizacional) {
        this.tipoUnidadOrganizacional = tipoUnidadOrganizacional;
    }

    public int getIdUnidadOrganizacional() {
        return idUnidadOrganizacional;
    }

    public void setIdUnidadOrganizacional(int idUnidadOrganizacional) {
        this.idUnidadOrganizacional = idUnidadOrganizacional;
    }

    public String getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    public void setUnidadOrganizacional(String unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    public List<UnidadOrganizacional> getSubunidades() {
        return subunidades;
    }

    public void setSubunidades(List<UnidadOrganizacional> subunidades) {
        this.subunidades = subunidades;
    }

    public List<CentroCosto> getCentroCostos() {
        return centroCostos;
    }

    public void setCentroCostos(List<CentroCosto> centroCostos) {
        this.centroCostos = centroCostos;
    }

    public UnidadOrganizacional getUnidadPadre() {
        return unidadPadre;
    }

    public void setUnidadPadre(UnidadOrganizacional unidadPadre) {
        this.unidadPadre = unidadPadre;
    }

    public Empleado getEmpleadoJefe() {
        return empleadoJefe;
    }

    public void setEmpleadoJefe(Empleado empleadoJefe) {
        this.empleadoJefe = empleadoJefe;
    }
}
