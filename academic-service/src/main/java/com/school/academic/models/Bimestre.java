package com.school.academic.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bimestres")
public class Bimestre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TINYINT")
    private int orden;
    
    @Column(columnDefinition = "TINYINT")
    private int nota1;

    @Column(columnDefinition = "TINYINT")
    private int nota2;

    @Column(columnDefinition = "TINYINT")
    private int nota3;

    @Column(columnDefinition = "TINYINT")
    private int nota4;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)      //el campo esta oculto
    @ManyToOne
    @JoinColumn(name = "id_am")
    private AlumnoMateria alumnoMateria;

    
    public Bimestre() {
    } 
    
    public Bimestre(int orden, int nota1, int nota2, int nota3, int nota4) {
        this.orden = orden;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
    }


    @Override
    public String toString() {
        return "Bimestre [id=" + id + ", nota1=" + nota1 + ", nota2=" + nota2 + ", nota3=" + nota3 + ", nota4=" + nota4
                + "]";
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public int getNota1() {
        return nota1;
    }
    public void setNota1(int nota1) {
        this.nota1 = nota1;
    }
    public int getNota2() {
        return nota2;
    }
    public void setNota2(int nota2) {
        this.nota2 = nota2;
    }
    public int getNota3() {
        return nota3;
    }
    public void setNota3(int nota3) {
        this.nota3 = nota3;
    }
    public int getNota4() {
        return nota4;
    }
    public void setNota4(int nota4) {
        this.nota4 = nota4;
    }
    public AlumnoMateria getAlumnoMateria() {
        return alumnoMateria;
    }
    public void setAlumnoMateria(AlumnoMateria alumnoMateria) {
        this.alumnoMateria = alumnoMateria;
    }
    public int getOrden() {
        return orden;
    }
    public void setOrden(int orden) {
        this.orden = orden;
    }

}
