package com.school.academic.models;

import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;

    private Date fecha;

    @Column(name = "h_inicio")
    private LocalTime inicio;

    private String titulo;

    private String detalle;

    private int estado;

    private String mensaje;

    
    public Cita() {
    }

    public Cita(Docente docente, Alumno alumno, Date fecha, LocalTime inicio, String titulo, String detalle) {
        this.docente = docente;
        this.alumno = alumno;
        this.fecha = fecha;
        this.inicio = inicio;
        this.titulo = titulo;
        this.detalle = detalle;
    }

    
    //Docente docente, Alumno alumno, 
    

    public Cita(Long id, Docente docente, Alumno alumno, Date fecha, LocalTime inicio, String titulo, String detalle,
            int estado, String mensaje) {
        this.id = id;
        this.docente = docente;
        this.alumno = alumno;
        this.fecha = fecha;
        this.inicio = inicio;
        this.titulo = titulo;
        this.detalle = detalle;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Cita [id=" + id + ", docente=" + docente + ", alumno=" + alumno + ", fecha=" + fecha + ", inicio="
                + inicio + ", titulo=" + titulo + ", detalle=" + detalle + ", estado=" + estado + "]";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
