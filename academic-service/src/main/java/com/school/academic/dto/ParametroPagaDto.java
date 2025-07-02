package com.school.academic.dto;

public class ParametroPagaDto {
    private Long id;
    private int nivel;
    private Double precioMatricula;
    private Double precioPension;
    private int dia_vencimiento;
    private Double mora;

    
    public ParametroPagaDto() {}

    public ParametroPagaDto(Long id, int nivel, Double precioMatricula, Double precioPension, 
                           int dia_vencimiento, Double mora) {
        this.id = id;
        this.nivel = nivel;
        this.precioMatricula = precioMatricula;
        this.precioPension = precioPension;
        this.dia_vencimiento = dia_vencimiento;
        this.mora = mora;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public Double getPrecioMatricula() { return precioMatricula; }
    public void setPrecioMatricula(Double precioMatricula) { this.precioMatricula = precioMatricula; }

    public Double getPrecioPension() { return precioPension; }
    public void setPrecioPension(Double precioPension) { this.precioPension = precioPension; }

    public int getDia_vencimiento() { return dia_vencimiento; }
    public void setDia_vencimiento(int dia_vencimiento) { this.dia_vencimiento = dia_vencimiento; }

    public Double getMora() { return mora; }
    public void setMora(Double mora) { this.mora = mora; }
}