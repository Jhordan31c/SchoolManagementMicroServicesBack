package com.school.auth.dto;

import java.util.List;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private boolean activo;
    private List<RolDto> roles;
    private boolean admin;

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

