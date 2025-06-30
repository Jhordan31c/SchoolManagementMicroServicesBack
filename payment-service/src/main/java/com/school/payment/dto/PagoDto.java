package com.school.payment.dto;

import java.util.Date;

public class PagoDto {
    private Long id;
    private Long alumnoId; // Solo el id del alumno para evitar ciclos
    private String nombre;
    private String detalle;
    private Double monto;
    private Date fecha_pago;
    private Date fecha_vencimiento;
    private int estado;

    public PagoDto() {
    }
    public PagoDto(Long id, Long alumnoId, String nombre, String detalle, Double monto, Date fecha_pago, Date fecha_vencimiento, int estado) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.nombre = nombre;
        this.detalle = detalle;
        this.monto = monto;
        this.fecha_pago = fecha_pago;
        this.fecha_vencimiento = fecha_vencimiento;
        this.estado = estado;
    }

    public PagoDto(Long id, String nombre, String detalle, Double monto, Date fecha_pago, Date fecha_vencimiento, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.detalle = detalle;
        this.monto = monto;
        this.fecha_pago = fecha_pago;
        this.fecha_vencimiento = fecha_vencimiento;
        this.estado = estado;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAlumnoId() { return alumnoId; }
    public void setAlumnoId(Long alumnoId) { this.alumnoId = alumnoId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public Date getFecha_pago() { return fecha_pago; }
    public void setFecha_pago(Date fecha_pago) { this.fecha_pago = fecha_pago; }
    public Date getFecha_vencimiento() { return fecha_vencimiento; }
    public void setFecha_vencimiento(Date fecha_vencimiento) { this.fecha_vencimiento = fecha_vencimiento; }
    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
}
