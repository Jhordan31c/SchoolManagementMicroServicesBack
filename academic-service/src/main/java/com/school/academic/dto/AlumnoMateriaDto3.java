package com.school.academic.dto;

import java.util.List;

import com.school.academic.models.Bimestre;

public class AlumnoMateriaDto3 {
    private Long id;
    private String docente;
    private String materia;
    private List<Bimestre> bimestres;


    public AlumnoMateriaDto3(Long id, String docente, String materia, List<Bimestre> bimestres) {
        this.id = id;
        this.docente = docente;
        this.materia = materia;
        this.bimestres = bimestres;
    }

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDocente() {
        return docente;
    }
    public void setDocente(String docente) {
        this.docente = docente;
    }
    public String getMateria() {
        return materia;
    }
    public void setMateria(String materia) {
        this.materia = materia;
    }
    public List<Bimestre> getBimestres() {
        return bimestres;
    }
    public void setBimestres(List<Bimestre> bimestres) {
        this.bimestres = bimestres;
    }

    
}
