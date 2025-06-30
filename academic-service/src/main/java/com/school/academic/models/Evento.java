package com.school.academic.models;

import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String nombre;

    String descripcion;

    private Date fecha;

    private int estado;

    @Column(name = "h_inicio")
    private LocalTime inicio;

    @Column(name = "h_fin")
    private LocalTime fin;
    

    public Evento() {
    }
    
    public Evento(String nombre, String descripcion, Date fecha, LocalTime inicio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.inicio = inicio;
    }


    @Override
    public String toString() {
        return "Evento [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fecha=" + fecha
                + ", estado=" + estado + ", inicio=" + inicio + "]";
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public LocalTime getInicio() {
        return inicio;
    }


    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

    
    
}
