package com.school.academic.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "apoderados")
public class Apoderado {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dni;

    private String nombre;

    private String apellido;

    private String parentesco;

    private String telefono;

    private String correo;

    private String direccion;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)      //el campo esta oculto
    @OneToOne
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;

    public Apoderado() {
    }

    public Apoderado(String dni, String nombre, String apellido, String parentesco, String telefono, String correo,
            String direccion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.parentesco = parentesco;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    

    @Override
    public String toString() {
        return "Apoderado [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido
                + ", parentesco=" + parentesco + ", telefono=" + telefono + ", correo=" + correo + ", direccion="
                + direccion + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }


    



}
