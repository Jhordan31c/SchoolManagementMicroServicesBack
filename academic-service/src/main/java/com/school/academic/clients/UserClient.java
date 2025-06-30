package com.school.academic.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.school.academic.dto.UserDto;

@FeignClient(name = "auth-service", url = "${services.auth.url}")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
    
    // CREAR NUEVO USUARIO
    @PostMapping("/users")
    UserDto createUser(@RequestBody UserDto userRequest);
    
    // ACTUALIZAR USUARIO EXISTENTE
    @PutMapping("/users/{id}")
    UserDto updateUser(@PathVariable("id") Long id, @RequestBody UserDto userRequest);
    
    // ELIMINAR USUARIO
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Long id);
    
    // ACTUALIZAR ESTADO DEL USUARIO
    @PutMapping("/users/{id}/status")
    UserDto updateUserStatus(@PathVariable("id") Long id, @RequestParam("active") Boolean active);
    
    // BUSCAR USUARIO POR USERNAME
    @GetMapping("/users/username/{username}")
    UserDto getUserByUsername(@PathVariable("username") String username);
    
    // VERIFICAR SI EXISTE USERNAME
    @GetMapping("/users/exists/{username}")
    Boolean existsByUsername(@PathVariable("username") String username);
    
    // OBTENER USUARIOS POR ROL
    @GetMapping("/users/role/{roleId}")
    List<UserDto> getUsersByRole(@PathVariable("roleId") Long roleId);
    
    // HEALTH CHECK
    @GetMapping("/users/health")
    String healthCheck();

}
