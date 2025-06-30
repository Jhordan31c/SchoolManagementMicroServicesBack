package com.school.academic.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "alumno_materia")
public class AlumnoMateria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "id_grado")
    private Grado grado;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "id_aula")
    private Aula aula;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "alumnoMateria")
    private List<Bimestre> bimestres;

    /* 
    @ElementCollection
    @CollectionTable(name = "notas", joinColumns = @JoinColumn(name = "id_am"))
    @MapKeyColumn(name = "index")
    @Column(name = "nota")
    private List<Integer> notas;
    */

    public AlumnoMateria() {
        bimestres = new ArrayList<>();
    }

    public AlumnoMateria(Alumno alumno, Grado grado, Materia materia) {
        this();
        this.alumno = alumno;
        this.grado = grado;
        this.materia = materia;
    }

    

    @Override
    public String toString() {
        return "AlumnoMateria [id=" + id + ", alumno=" + alumno + ", grado=" + grado + ", materia=" + materia + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public void addBimestre(Bimestre b){
        this.bimestres.add(b);
        b.setAlumnoMateria(this);
    }

    public void removeBimestre(Bimestre b){
        b.setAlumnoMateria(null);
        this.bimestres.remove(b);
    }

    public List<Bimestre> getBimestres() {
        return bimestres;
    }

    public void setBimestres(List<Bimestre> bimestres) {
        this.bimestres = bimestres;
    }
    
}
