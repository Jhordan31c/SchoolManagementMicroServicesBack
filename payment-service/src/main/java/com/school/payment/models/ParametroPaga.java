package com.school.payment.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pago-parametros")
public class ParametroPaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double precioMatricula;
    private double precioPension;
    
    private int nivel;
    private int dia_vencimiento;
    private int mora;
    
    public ParametroPaga() {
    }
    public ParametroPaga(int id, double precioMatricula, double precioPension, int nivel, int dia_vencimiento, int mora) {
        this.id = id;
        this.precioMatricula = precioMatricula;
        this.precioPension = precioPension;
        this.nivel = nivel;
        this.dia_vencimiento = dia_vencimiento;
        this.mora = mora;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public double getPrecioMatricula() {
        return precioMatricula;
    }
    public void setPrecioMatricula(double precioMatricula) {
        this.precioMatricula = precioMatricula;
    }


    public double getPrecioPension() {
        return precioPension;
    }
    public void setPrecioPension(double precioPension) {
        this.precioPension = precioPension;
    }


    public int getNivel() {
        return nivel;
    }
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }


    public int getDia_vencimiento() {
        return dia_vencimiento;
    }


    public void setDia_vencimiento(int dia_vencimiento) {
        this.dia_vencimiento = dia_vencimiento;
    }


    public int getMora() {
        return mora;
    }


    public void setMora(int mora) {
        this.mora = mora;
    } 
    

}
