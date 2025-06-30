package com.school.auth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.school.auth.dto.AlumnoDto;
import com.school.auth.dto.DocenteDto;

@FeignClient(name = "academic-service", url = "${services.academic.url}")
public interface AcademicClient {

    @GetMapping("/alumnos/user/{id}")
    AlumnoDto buscarPerfilDeAlumnoPorIdUsuario(@PathVariable("id") Long userId);

    @GetMapping("/docentes/user/{userId}")
    DocenteDto buscarPerfilDeDocentePorIdUsuario(@PathVariable("userId") Long userId);

    @GetMapping("/alumnos")
    java.util.List<AlumnoDto> obtenerTodosLosAlumnos();

    @GetMapping("/docentes")
    java.util.List<DocenteDto> obtenerTodosLosDocentes();
}