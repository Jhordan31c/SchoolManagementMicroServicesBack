package com.school.academic.dto;

public class ContadorDto {
    private int orden;
    private long cantidad;

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    public ContadorDto(int orden, long cantidad) {
        this.orden = orden;
        this.cantidad = cantidad;
    }

}
