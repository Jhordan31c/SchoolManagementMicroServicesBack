package com.school.academic.dto;

import java.util.Date;

public class DocenteDto {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String correo;
    private Date fecha_registro;
    private int estado;
    private UserDto user; // ✅ UserDto completo, NO solo userId
    
    // Constructores
    public DocenteDto() {}
    
    public DocenteDto(Long id, String dni, String nombre, String apellido, String correo, 
                     Date fecha_registro, int estado, UserDto user) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fecha_registro = fecha_registro;
        this.estado = estado;
        this.user = user;
    }

    // Getters y Setters
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
    
    public String getCorreo() { 
        return correo; 
    }
    
    public void setCorreo(String correo) { 
        this.correo = correo; 
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
    
    public UserDto getUser() { 
        return user; 
    }
    
    public void setUser(UserDto user) { 
        this.user = user; 
    }
}
