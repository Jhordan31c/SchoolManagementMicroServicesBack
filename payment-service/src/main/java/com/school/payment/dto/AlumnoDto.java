package com.school.payment.dto;

public class AlumnoDto {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private Integer estado;
    
    public AlumnoDto() {}
    
    public AlumnoDto(Long id, String dni, String nombre, String apellido, String email, Integer estado) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.estado = estado;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
}