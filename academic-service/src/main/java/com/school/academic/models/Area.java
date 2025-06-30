package com.school.academic.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "areas")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    //APLICANDO BIDIRECCIONALIDAD A MATERIAS | MATERIA ES DUEÑA DE LA RELACION
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "area")
    private Set<Materia> materias;

    public Area() {
        materias = new HashSet<>();
    }

    public Area(String nombre) {
        this();
        this.nombre = nombre;
    }


    
    
    @Override
    public String toString() {
        return "Area [id=" + id + ", nombre=" + nombre + ", materias=" + materias + "]";
    }


    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(Set<Materia> materias) {
        this.materias = materias;
    }

}
