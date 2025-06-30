package com.school.academic.dto;

import java.time.LocalTime;

public class HorarioDocenteDto {
    private String nivel;
    private int grado;
    private char subGrado;
    private String tutor;
    private String materia;
    private int dia;
    private LocalTime h_inicio;
    private LocalTime h_fin;
    
 
    
    public HorarioDocenteDto() {
    }

    public HorarioDocenteDto(String tutor, String nivel, int grado, char subGrado, String materia,
            int dia, LocalTime h_inicio, LocalTime h_fin) {
        this.tutor = tutor;
        this.nivel = nivel;
        this.grado = grado;
        this.subGrado = subGrado;
        this.materia = materia;
        this.dia = dia;
        this.h_inicio = h_inicio;
        this.h_fin = h_fin;
    }


    @Override
    public String toString() {
        return "HorarioDocenteDto [nivel=" + nivel + ", grado=" + grado + ", subGrado=" + subGrado + ", tutor=" + tutor
                + ", materia=" + materia + ", dia=" + dia + ", h_inicio=" + h_inicio + ", h_fin=" + h_fin + "]";
    }
 

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public char getSubGrado() {
        return subGrado;
    }

    public void setSubGrado(char subGrado) {
        this.subGrado = subGrado;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public LocalTime getH_inicio() {
        return h_inicio;
    }

    public void setH_inicio(LocalTime h_inicio) {
        this.h_inicio = h_inicio;
    }

    public LocalTime getH_fin() {
        return h_fin;
    }

    public void setH_fin(LocalTime h_fin) {
        this.h_fin = h_fin;
    }  
    
}
