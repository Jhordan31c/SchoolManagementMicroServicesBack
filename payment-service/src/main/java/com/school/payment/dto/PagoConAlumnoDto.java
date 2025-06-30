package com.school.payment.dto;

import com.school.payment.models.Pago;
import java.util.Date;  // ✅ Si tu Pago usa Date

public class PagoConAlumnoDto {
    private Long id;
    private String nombre;
    private String detalle;
    private Double monto;
    private Date fechaVencimiento;  // ✅ Date si tu entidad Pago usa Date
    private Date fechaPago;         // ✅ Date si tu entidad Pago usa Date
    private Integer estado;
    private Long alumnoId;
    
    // Datos del alumno
    private String alumnoNombre;
    private String alumnoApellido;
    private String alumnoDni;
    
    // Constructor por defecto
    public PagoConAlumnoDto() {}
    
    // Constructor principal - VERIFICA QUE LOS MÉTODOS EXISTAN EN PAGO
    public PagoConAlumnoDto(Pago pago, AlumnoDto alumno) {
        this.id = pago.getId();
        this.nombre = pago.getNombre();
        this.detalle = pago.getDetalle();
        this.monto = pago.getMonto();
        
        // ✅ Verifica que estos métodos existan en tu entidad Pago:
        this.fechaVencimiento = pago.getFecha_vencimiento();  // ¿Existe este método?
        this.fechaPago = pago.getFecha_pago();                // ¿Existe este método?
        this.estado = pago.getEstado();
        
        // ✅ Verifica que este método exista:
        this.alumnoId = pago.getAlumnoId();  // ¿Existe este método?
        
        if (alumno != null) {
            this.alumnoNombre = alumno.getNombre();
            this.alumnoApellido = alumno.getApellido();
            this.alumnoDni = alumno.getDni();
        }
    }
    
    // Getters y Setters (todos los que necesites)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    
    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    
    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }
    
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
    
    public Long getAlumnoId() { return alumnoId; }
    public void setAlumnoId(Long alumnoId) { this.alumnoId = alumnoId; }
    
    public String getAlumnoNombre() { return alumnoNombre; }
    public void setAlumnoNombre(String alumnoNombre) { this.alumnoNombre = alumnoNombre; }
    
    public String getAlumnoApellido() { return alumnoApellido; }
    public void setAlumnoApellido(String alumnoApellido) { this.alumnoApellido = alumnoApellido; }
    
    public String getAlumnoDni() { return alumnoDni; }
    public void setAlumnoDni(String alumnoDni) { this.alumnoDni = alumnoDni; }
}