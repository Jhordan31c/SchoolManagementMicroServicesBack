package com.school.auth.dto;

import java.util.Date;

public class AlumnoDto {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private char genero;
    private Date fecha_registro;
    private Date fecha_nacimiento;
    private int estado;
    private UserDto user;

    public AlumnoDto(Long id, String dni, String nombre, String apellido, char genero, Date fecha_registro,
            Date fecha_nacimiento, int estado, UserDto user) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.fecha_registro = fecha_registro;
        this.fecha_nacimiento = fecha_nacimiento;
        this.estado = estado;
        this.user = user;
    }

    public AlumnoDto() {
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

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
    
}
