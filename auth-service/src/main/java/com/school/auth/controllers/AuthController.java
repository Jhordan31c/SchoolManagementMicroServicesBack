package com.school.auth.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.auth.dto.RolDto;
import com.school.auth.dto.UserDto;
import com.school.auth.models.Rol;
import com.school.auth.models.User;
import com.school.auth.services.UserService;
import com.school.auth.validations.Validacion;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    private UserService us;

    @Autowired
    private Validacion v;


    @GetMapping
    public List<User> listAll() {
        return us.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@Valid @RequestBody User u, BindingResult br) {
        if (br.hasFieldErrors()) {
            return v.informe(br);
        }
        User x = us.create(u, 0);
        return ResponseEntity.status(HttpStatus.CREATED).body(x);
    }

     @PostMapping("/registro")
    public ResponseEntity<?> registerUsuario(@Valid @RequestBody User u, BindingResult br){
        u.setAdmin(false);
        if(br.hasFieldErrors()){
            return v.informe(br);
        }
        User x = us.create(u,0);
        return ResponseEntity.status(HttpStatus.CREATED).body(x);
    }

    @GetMapping("/{id}/{rol}")
    public ResponseEntity<?> buscarUsuario(@PathVariable(name = "id") Long id, @PathVariable(name = "rol") String rol) {
        
        try {
            // ✅ SERVICE MANEJA TODA LA LÓGICA
            Object result = us.buscarPerfilPorIdYRol(id, rol);
            return ResponseEntity.ok().body(result);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Rol no válido o no soportado: " + e.getMessage());
            
        } catch (Exception e) {
            System.err.println("Error buscando perfil de usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try {
            User user = us.findById(id).orElse(null);
            if (user != null) {
                // CONVERTIR User ENTITY A UserDto
                UserDto userDto = convertUserToDto(user);
                return ResponseEntity.ok(userDto);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Error obteniendo usuario por ID: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        try {
            User user = us.findByUsername(username);
            if (user != null) {
                // CONVERTIR User ENTITY A UserDto
                UserDto userDto = convertUserToDto(user);
                return ResponseEntity.ok(userDto);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Error obteniendo usuario por username: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/exists/{username}")
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        try {
            boolean exists = us.existsByUsername(username);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            System.err.println("Error verificando existencia de username: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private UserDto convertUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setActivo(user.isActivo());
        
        // ✅ CONVERTIR ROLES A LIST<STRING>
        List<RolDto> roles = new ArrayList<>();
        if (user.getRoles() != null) {
            for (Rol rol : user.getRoles()) {
                RolDto rolDto = new RolDto();
                rolDto.setNombre(rol.getNombre());
                roles.add(rolDto);
            }
        }
        userDto.setRoles(roles);
        
        return userDto;
    }

}