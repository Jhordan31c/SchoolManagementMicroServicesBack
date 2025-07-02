package com.school.auth.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.auth.models.Rol;
import com.school.auth.clients.AcademicClient;
import com.school.auth.dto.AlumnoDto;
import com.school.auth.dto.DocenteDto;
import com.school.auth.models.User;
import com.school.auth.repositories.RolRepository;
import com.school.auth.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository ur;

    @Autowired
    private RolRepository rr;

    @Autowired
    private PasswordEncoder encriptador;

    @Autowired
    private AcademicClient academicClient;

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id){
        return ur.findById(id);
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return (List<User>) ur.findAll();
    }

    @Transactional
    public User create(User u, int tipo){
        List<Rol> roles = new ArrayList<>();
    
        Optional<Rol> or = rr.findByNombre("ROLE_USER");
        or.ifPresent(roles::add);

        if(u.isAdmin()){
            Optional<Rol> orAdmin = rr.findByNombre("ROLE_ADMIN");
            orAdmin.ifPresent(roles::add);
        }

        if (tipo==1) {
            Optional<Rol> orAdmin = rr.findByNombre("ROLE_ALUMNO");
            orAdmin.ifPresent(roles::add);
        }
        if (tipo==2) {
            Optional<Rol> orAdmin = rr.findByNombre("ROLE_DOCENTE");
            orAdmin.ifPresent(roles::add);
        }

        String password = encriptador.encode(u.getPassword());

        u.setActivo(true);
        u.setRoles(roles);
        u.setPassword(password);

        return ur.save(u);
    }

    @Transactional
    public User update(User oldUser, User newUser){
        String password = newUser.getPassword();
        String username = newUser.getUsername();

        if (!password.isBlank()) {
            password = encriptador.encode(newUser.getPassword());
            oldUser.setPassword(password);
        }
        if (!username.isBlank()) {
            oldUser.setUsername(username);
        }
        if (newUser.getRoles()!=null) {
            if (newUser.getRoles().isEmpty()) {
                oldUser.setRoles(newUser.getRoles());
            }
        }
        oldUser.setActivo(newUser.isActivo());
        
        return ur.save(oldUser);
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return ur.existsByUsername(username);
    }

    public Object buscarPerfilPorIdYRol(Long id, String rol) {
        
        if (rol.equalsIgnoreCase("ROLE_ADMIN")) {
            User user = findById(id).orElseThrow(() -> 
                new RuntimeException("Usuario administrador no encontrado"));
            return user;
            
        } else if (rol.equalsIgnoreCase("ROLE_ALUMNO")) {
            try {
                // ✅ SERVICE USA EL FEIGN CLIENT Y OBTIENE DTO CON UserDto COMPLETO
                AlumnoDto alumno = academicClient.buscarPerfilDeAlumnoPorIdUsuario(id);
                return alumno;
            } catch (Exception e) {
                System.err.println("Error obteniendo perfil de alumno: " + e.getMessage());
                throw new RuntimeException("No se pudo obtener el perfil del alumno");
            }
            
        } else if (rol.equalsIgnoreCase("ROLE_DOCENTE")) {
            try {
                // ✅ SERVICE USA EL FEIGN CLIENT Y OBTIENE DTO CON UserDto COMPLETO
                DocenteDto docente = academicClient.buscarPerfilDeDocentePorIdUsuario(id);
                return docente;
            } catch (Exception e) {
                System.err.println("Error obteniendo perfil de docente: " + e.getMessage());
                throw new RuntimeException("No se pudo obtener el perfil del docente");
            }
        }

        throw new IllegalArgumentException("Rol no válido o no soportado: " + rol);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return ur.findByUsername(username).orElse(null);
    }

    @Transactional
    public User updateStatus(Long id, boolean active) {
        Optional<User> userOpt = ur.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setActivo(active);
            return ur.save(user);
        }
        throw new RuntimeException("Usuario no encontrado");
    }

    @Transactional
    public User createUserForMicroservice(User u, String roleName) {
        List<Rol> roles = new ArrayList<>();
        
        // Rol base
        Optional<Rol> roleUser = rr.findByNombre("ROLE_USER");
        roleUser.ifPresent(roles::add);
        
        // Rol específico
        Optional<Rol> specificRole = rr.findByNombre(roleName);
        specificRole.ifPresent(roles::add);

        String password = encriptador.encode(u.getPassword());

        u.setActivo(true);
        u.setRoles(roles);
        u.setPassword(password);

        return ur.save(u);
    }

    
}
