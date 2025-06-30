package com.school.academic.models;

import java.time.LocalTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "horario")
public class Horario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)      //el campo esta oculto
    @ManyToOne
    @JoinColumn(name = "id_aula")
    private Aula aula;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)      //el campo esta oculto
    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente docente;
    
    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;
    
    private Integer dia; 

    @Column(name = "h_inicio")
    private LocalTime inicio;

    @Column(name = "h_fin")
    private LocalTime fin;

    
    public Horario(Docente docente, Materia materia, Integer dia, LocalTime inicio, LocalTime fin) {
        this.docente = docente;
        this.materia = materia;
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
    }

    
    public Horario(Long id, Docente docente, Materia materia, Integer dia, LocalTime inicio, LocalTime fin) {
        this.id = id;
        this.docente = docente;
        this.materia = materia;
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
    }


    public Horario(Aula aula, Materia materia, Integer dia, LocalTime inicio, LocalTime fin) {
        this.aula = aula;
        this.materia = materia;
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
    }


    public Horario() {
    }
    public Horario(Materia materia, Docente docente,Aula aula) {
        this.aula = aula;
        this.docente = docente;
        this.materia = materia;
    }
    public Horario(Aula aula, Docente docente, Materia materia, Integer dia, LocalTime inicio, LocalTime fin) {
        this.aula = aula;
        this.docente = docente;
        this.materia = materia;
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
    }


    @Override
    public String toString() {
        return "Horario [id=" + id + ", aula=" + aula + ", docente=" + docente + ", materia=" + materia + ", dia=" + dia
                + ", inicio=" + inicio + ", fin=" + fin + "]";
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

}
