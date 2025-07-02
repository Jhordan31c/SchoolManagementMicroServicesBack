package com.school.payment.dto;

import java.util.Date;

public class PagoRequestDto {
    private String nombre;
    private Double monto;
    private Date fechaVencimiento;
    private int estado;
    private Long alumnoId;
    private Long aulaId; // Para poder eliminar pagos por aula

    // Constructores
    public PagoRequestDto() {}

    public PagoRequestDto(String nombre, Double monto, Date fechaVencimiento, 
                         int estado, Long alumnoId, Long aulaId) {
        this.nombre = nombre;
        this.monto = monto;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.alumnoId = alumnoId;
        this.aulaId = aulaId;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    public Long getAlumnoId() { return alumnoId; }
    public void setAlumnoId(Long alumnoId) { this.alumnoId = alumnoId; }

    public Long getAulaId() { return aulaId; }
    public void setAulaId(Long aulaId) { this.aulaId = aulaId; }
}
