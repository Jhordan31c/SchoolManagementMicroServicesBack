package com.school.payment.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.school.payment.dto.AlumnoDto;

@Entity
@Table(name = "alumno_pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CAMBIAR ESTO:
    // @ManyToOne
    // @JoinColumn(name = "id_alumno")
    // private Alumno alumno;

    // POR ESTO:
    @Column(name = "id_alumno", nullable = false)
    private Long alumnoId; // ✅ Solo referencia por ID

    @Column(name = "aula_id")
    private Long aulaId;

    private String nombre;
    private String detalle;
    private Double monto;
    private Date fecha_pago;
    private Date fecha_vencimiento;
    private int estado;

    // DATOS EXTERNOS (NO SE PERSISTEN)
    @Transient
    private AlumnoDto alumno; // ✅ Se puede llenar con AlumnoClient si necesitas

    // Constructores
    public Pago() {
    }

    public Pago(String nombre, Double monto, Date fecha_vencimiento, int estado) {
        this.nombre = nombre;
        this.monto = monto;
        this.fecha_vencimiento = fecha_vencimiento;
        this.estado = estado;
    }

    public Pago(String nombre, Double monto, Date fecha_vencimiento, int estado, Long alumnoId) {
        this.nombre = nombre;
        this.monto = monto;
        this.fecha_vencimiento = fecha_vencimiento;
        this.estado = estado;
        this.alumnoId = alumnoId; // ✅ Usar alumnoId
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ✅ CAMBIAR getAlumno/setAlumno POR getAlumnoId/setAlumnoId
    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Long getAulaId() { return aulaId; }
    public void setAulaId(Long aulaId) { this.aulaId = aulaId; }

    // ✅ MANTENER PARA COMPATIBILIDAD CON DTO
    public AlumnoDto getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoDto alumno) {
        this.alumno = alumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pago [id=" + id + ", alumnoId=" + alumnoId + ", nombre=" + nombre +
                ", monto=" + monto + ", fecha_vencimiento=" + fecha_vencimiento + "]";
    }
}