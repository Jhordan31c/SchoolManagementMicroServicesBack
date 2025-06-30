package com.school.academic.dto;

import java.time.LocalTime;

public class HorarioDto {
    private int dia;
    private Long id_docente;
    private Long id_materia;
    private LocalTime h_inicio;
    private LocalTime h_fin; 


    public HorarioDto() {
    }

    
    public HorarioDto(int dia, Long id_docente, Long id_materia, LocalTime h_inicio, LocalTime h_fin) {
        this.dia = dia;
        this.id_docente = id_docente;
        this.id_materia = id_materia;
        this.h_inicio = h_inicio;
        this.h_fin = h_fin;
    }


    @Override
    public String toString() {
        return "HorarioDto [dia=" + dia + ", id_docente=" + id_docente + ", id_materia=" + id_materia + ", h_inicio="
                + h_inicio + ", h_fin=" + h_fin + "]";
    }


    public int getDia() {
        return dia;
    }


    public void setDia(int dia) {
        this.dia = dia;
    }

    public Long getId_docente() {
        return id_docente;
    }

    public void setId_docente(Long id_docente) {
        this.id_docente = id_docente;
    }

    public Long getId_materia() {
        return id_materia;
    }

    public void setId_materia(Long id_materia) {
        this.id_materia = id_materia;
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
