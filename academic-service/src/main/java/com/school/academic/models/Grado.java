package com.school.academic.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "grados")
public class Grado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nivel;

    @Column(name = "sub_nivel")
    private int subNivel;

    public Grado() {
    }

    public Grado(String nivel, int subNivel) {
        this.nivel = nivel;
        this.subNivel = subNivel;
    }

    @Override
    public String toString() {
        return "Grado [id=" + id + ", nivel=" + nivel + ", subNivel=" + subNivel + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getSubNivel() {
        return subNivel;
    }

    public void setSubNivel(int subNivel) {
        this.subNivel = subNivel;
    }
    
}
