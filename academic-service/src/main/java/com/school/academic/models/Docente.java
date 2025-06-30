package com.school.academic.models;

import java.util.Date;

import com.school.academic.dto.UserDto;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;

@Entity
@Table(name = "docentes")
public class Docente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String dni;

    private String nombre;

    private String apellido;

    private String correo;

    private Date fecha_registro;

    private int estado;

    @Transient
    private UserDto user;


    public Docente() {
    }

    public Docente(String dni, String nombre, String apellido, String correo) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }

    
    public Docente(Long id, Long userId, String dni, String nombre, String apellido, String correo) {
        this.id = id;
        this.userId = userId;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fecha_registro = new Date();
        this.estado = 1; 
    }

    public Docente(Long id, Long userId, String dni, String nombre, String apellido, String correo, Date fecha_registro,
            int estado) {
        this.id = id;
        this.userId = userId;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fecha_registro = fecha_registro;
        this.estado = estado;
    }

    @PrePersist
    public void prePersist() {
        if (fecha_registro == null) {
            fecha_registro = new Date();
        }
        if (estado == 0) {
            estado = 1;
        }
    }


    @Override
    public String toString() {
        return "Docente [id=" + id + ", userId=" + userId + ", dni=" + dni + ", nombre=" + nombre + 
               ", apellido=" + apellido + ", correo=" + correo + ", fecha_registro=" + fecha_registro + 
               ", estado=" + estado + "]";
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
}
