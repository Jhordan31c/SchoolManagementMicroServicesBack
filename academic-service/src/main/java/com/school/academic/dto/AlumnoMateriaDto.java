package com.school.academic.dto;

import java.util.List;

import com.school.academic.models.Bimestre;

public class AlumnoMateriaDto {
    private Long id;

    private String dni;
    private String alumno;

    private String nivel;
    private int grado;
    private char subGrado;
    
    private String materia;
    private List<Bimestre> bimestres;
    
    public AlumnoMateriaDto() {
    }
    

    public AlumnoMateriaDto(Long id, String dni, String alumno, String nivel, int grado, char subGrado, String materia,
            List<Bimestre> bimestres) {
        this.id = id;
        this.dni = dni;
        this.alumno = alumno;
        this.nivel = nivel;
        this.grado = grado;
        this.subGrado = subGrado;
        this.materia = materia;
        this.bimestres = bimestres;
    }


    /*
    public AlumnoMateriaDto(Long id, String alumno, String nivel, int grado, char subGrado, String materia,
            List<Bimestre> bimestres) {
        this.id = id;
        this.alumno = alumno;
        this.nivel = nivel;
        this.grado = grado;
        this.subGrado = subGrado;
        this.materia = materia;
        this.bimestres = bimestres;
    }
 */
    

    @Override
    public String toString() {
        return "AlumnoMateriaDto [id=" + id + ", alumno=" + alumno + ", nivel=" + nivel + ", grado=" + grado
                + ", subGrado=" + subGrado + ", materia=" + materia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
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

    public List<Bimestre> getBimestres() {
        return bimestres;
    }
    public void setBimestres(List<Bimestre> bimestres) {
        this.bimestres = bimestres;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    
}
