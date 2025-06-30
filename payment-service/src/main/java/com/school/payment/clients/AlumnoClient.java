package com.school.payment.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import com.school.payment.dto.AlumnoDto;

@FeignClient(name = "academic-service", url = "${services.academic.url}")
public interface AlumnoClient {

    @GetMapping("/alumnos/{id}")
    AlumnoDto findById(@PathVariable("id") Long id);

    @GetMapping("/alumnos/buscar/{id}")
    AlumnoDto getAlumnoById(@PathVariable("id") Long id);

    @GetMapping("/alumnos/user/{userId}")  
    AlumnoDto getAlumnoByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/alumnos")
    List<AlumnoDto> findAllByEstado(@RequestParam("estado") Integer estado);
}