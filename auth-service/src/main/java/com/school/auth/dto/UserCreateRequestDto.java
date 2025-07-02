package com.school.auth.dto;

public class UserCreateRequestDto {
    private String username;
    private String password;
    private String email;
    private int roleId;


    public UserCreateRequestDto() {}

    public UserCreateRequestDto(String username, String password, String email, int roleId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
    }

    // Getters y setters (iguales que academic-service)
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }
}