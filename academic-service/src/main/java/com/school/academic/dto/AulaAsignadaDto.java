package com.school.academic.dto;

public class AulaAsignadaDto {
    private long id_aula;
    private String tutor;
    private String nivel;
    private int grado;
    private char subGrado;
    private int alumnos;
    private String materia;
    private Long id_materia;
    

    public AulaAsignadaDto() {
    }

    public AulaAsignadaDto(long id_aula, String tutor, String nivel, int grado, char subGrado, int alumnos,
            String materia, Long id_materia) {
        this.id_aula = id_aula;
        this.tutor = tutor;
        this.nivel = nivel;
        this.grado = grado;
        this.subGrado = subGrado;
        this.alumnos = alumnos;
        this.materia = materia;
        this.id_materia = id_materia;
    }


    @Override
    public String toString() {
        return "AulaAsignadaDto [id_aula=" + id_aula + ", tutor=" + tutor + ", nivel=" + nivel + ", grado=" + grado
                + ", subGrado=" + subGrado + ", alumnos=" + alumnos + ", materia=" + materia + ", id_materia="
                + id_materia + "]";
    }


    public long getId_aula() {
        return id_aula;
    }
    public void setId_aula(long id_aula) {
        this.id_aula = id_aula;
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
    public int getAlumnos() {
        return alumnos;
    }
    public void setAlumnos(int alumnos) {
        this.alumnos = alumnos;
    }
    public String getMateria() {
        return materia;
    }
    public void setMateria(String materia) {
        this.materia = materia;
    }

    public char getSubGrado() {
        return subGrado;
    }

    public void setSubGrado(char subGrado) {
        this.subGrado = subGrado;
    }

    public Long getId_materia() {
        return id_materia;
    }

    public void setId_materia(Long id_materia) {
        this.id_materia = id_materia;
    }

}
