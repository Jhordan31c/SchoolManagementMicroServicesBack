package com.school.academic.dto;

public class ContadorDto2 {
    private int orden;
    private double cantidad;

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public ContadorDto2(int orden, double cantidad) {
        this.orden = orden;
        this.cantidad = cantidad;
    }

}
