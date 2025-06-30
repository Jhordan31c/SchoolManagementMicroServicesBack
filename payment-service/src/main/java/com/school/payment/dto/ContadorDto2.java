package com.school.payment.dto;

public class ContadorDto2 {
    private Integer periodo; // Mes, año, o alumnoId dependiendo del contexto
    private Double total;    // Suma de montos o cantidad
    
    // Constructor para queries con SUM
    public ContadorDto2(Integer periodo, Double total) {
        this.periodo = periodo;
        this.total = total;
    }
    
    // Constructor para queries con COUNT
    public ContadorDto2(Integer periodo, Long count) {
        this.periodo = periodo;
        this.total = count != null ? count.doubleValue() : 0.0;
    }
    
    // Constructor por defecto
    public ContadorDto2() {}

    // Getters y Setters
    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ContadorDto2 [periodo=" + periodo + ", total=" + total + "]";
    }
}