package com.school.academic.dto;

import java.util.List;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private boolean activo;
    private List<RolDto> roles;
    private boolean admin;


    public UserDto() {}
    
    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
        this.activo = true;
    }
    
    public UserDto(Long id, String username, Boolean activo, List<RolDto> roles) {
        this.id = id;
        this.username = username;
        this.activo = activo;
        this.roles = roles;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public List<RolDto> getRoles() { return roles; }
    public void setRoles(List<RolDto> roles) { this.roles = roles; }
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
}

