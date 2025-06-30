package com.school.academic.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "aulas")
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_grado")
    private Grado grado;

    private char sub_grado;

    private int estado;

    private Date fecha_registro;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente tutor;

    @ManyToMany(cascade = {CascadeType.PERSIST})                //GUARDAR en CASCADA
    @JoinTable(name = "aula_alumnos",                           //NOMBRE de TABLA INTERMEDIA
        joinColumns = @JoinColumn(name="id_aula"),              //FK PRINCIPAL, referencia a AULA
        inverseJoinColumns = @JoinColumn(name = "id_alumno"),   //FK INVERSA, referencia a los AULUMNOS
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_aula","id_alumno"}) //NO SE PERMITEN COMBINACIONES REPETIDAS
    )
    private Set<Alumno> alumnos;

    //APLICANDO BIDIRECCIONALIDAD A HORARIOS | HORARIO ES DUEÑA DE LA RELACION
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "aula")
    private Set<Horario> horarios;

    
    public Aula() {
        alumnos = new HashSet<>();
        horarios = new HashSet<>();
    }
    public Aula(Grado grado, char sub_grado, Docente tutor) {
        this();
        this.grado = grado;
        this.sub_grado = sub_grado;
        this.tutor = tutor;
    }


    public Aula(Long id, Grado grado, char sub_grado, int estado, Date fecha_registro, Docente tutor) {
        this();
        this.id = id;
        this.grado = grado;
        this.sub_grado = sub_grado;
        this.estado = estado;
        this.fecha_registro = fecha_registro;
        this.tutor = tutor;
    }
    

    public Aula(Long id, Grado grado, char sub_grado, int estado, Date fecha_registro, Docente tutor,
            Set<Horario> horarios) {
        this.id = id;
        this.grado = grado;
        this.sub_grado = sub_grado;
        this.estado = estado;
        this.fecha_registro = fecha_registro;
        this.tutor = tutor;
        this.horarios = horarios;
        this.alumnos = new HashSet<>();
    }
    
    public Aula(Long id) {
        this();
        this.id = id;
    }

    @Override
    public String toString() {
        return "Aula [id=" + id + ", grado=" + grado + ", sub_grado=" + sub_grado + ", estado=" + estado
                + ", fecha_registro=" + fecha_registro + ", tutor=" + tutor + "]";
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public Grado getGrado() {
        return grado;
    }
    public void setGrado(Grado grado) {
        this.grado = grado;
    }


    public char getSub_grado() {
        return sub_grado;
    }
    public void setSub_grado(char sub_grado) {
        this.sub_grado = sub_grado;
    }


    

    public Date getFecha_registro() {
        return fecha_registro;
    }
    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }


    public Docente getTutor() {
        return tutor;
    }
    public void setTutor(Docente tutor) {
        this.tutor = tutor;
    }


    public Set<Alumno> getAlumnos() {
        return alumnos;
    }
    public void setAlumnos(Set<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
    
    public void addAlumno(Alumno a){
        this.alumnos.add(a);
    }

    public void removeAlumno(Alumno a){
        this.alumnos.remove(a);
    }

    public Set<Horario> getHorarios() {
        return horarios;
    }
    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }
    
    public void addHorario(Horario h){
        this.horarios.add(h);
        h.setAula(this);
    }

    public void removeHorario(Horario h){
        h.setAula(null);
        this.horarios.remove(h);
    }
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
}
