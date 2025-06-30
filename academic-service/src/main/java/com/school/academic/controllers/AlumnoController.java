package com.school.academic.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.academic.clients.UserClient;
import com.school.academic.dto.AlumnoDto;
import com.school.academic.dto.AlumnoMateriaDto2;
import com.school.academic.dto.AlumnoMateriaDto3;
import com.school.academic.dto.UserDto;
import com.school.academic.models.Alumno;
import com.school.academic.models.AlumnoMateria;
import com.school.academic.models.Aula;
import com.school.academic.models.Grado;
import com.school.academic.services.AlumnoService;
import com.school.academic.validations.Validacion;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService as;

    @Autowired
    private UserClient userClient;


    @Autowired
    private Validacion v;


    @GetMapping
    public ResponseEntity<List<AlumnoDto>> listaAlumnos() {
        List<Alumno> alumnos = as.findAll();
        List<AlumnoDto> alumnosDto = alumnos.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(alumnosDto);
    }

    @GetMapping("/disponible/{x}")
    public List<Alumno> getListaDisponible(@PathVariable(name = "x") int estado){
        return as.findAllDisponible(estado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDto> getAlumno(@PathVariable Long id) {
        Optional<Alumno> alumno = as.findById(id);
        return alumno.map(a -> ResponseEntity.ok(convertToDto(a)))
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{x}")
    public List<Alumno> getLista(@PathVariable int x){
        return as.findAll(x);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<AlumnoDto> buscarPerfilDeAlumnoPorIdUsuario(@PathVariable Long userId) {
        try {
            Optional<Alumno> alumnoOpt = as.findByUser(userId);
            if (alumnoOpt.isPresent()) {
                Alumno alumno = alumnoOpt.get();
                AlumnoDto dto = convertToDto(alumno);
                return ResponseEntity.ok(dto);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Error obteniendo alumno por userId " + userId + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

     private AlumnoDto convertToDto(Alumno alumno) {
        UserDto userDto = null;
        
        // OBTENER UserDto COMPLETO USANDO USERCLIENT
        if (alumno.getUserId() != null) {
            try {
                userDto = userClient.getUserById(alumno.getUserId());
            } catch (Exception e) {
                System.err.println("Error obteniendo UserDto para alumno " + alumno.getId() + ": " + e.getMessage());
                // Crear UserDto básico en caso de error
                userDto = new UserDto();
                userDto.setId(alumno.getUserId());
                userDto.setUsername("N/A");
                userDto.setActivo(true);
            }
        }
        
        // CREAR DTO CON UserDto COMPLETO
        return new AlumnoDto(
            alumno.getId(),
            alumno.getDni(),
            alumno.getNombre(),
            alumno.getApellido(),
            alumno.getGenero(),
            alumno.getFecha_registro(),
            alumno.getFecha_nacimiento(),
            alumno.getEstado(),
            userDto // UserDto completo, NO solo userId
        );
    }

     @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Alumno a, BindingResult br)
    {
        if(br.hasFieldErrors()){
            return v.informe(br);
        }
        Optional<Alumno> oa = as.create(a);
        if(oa.isPresent()){
            Alumno x = oa.orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(x);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody Alumno a, @PathVariable Long id)
    {
        Optional<Alumno> oa = as.update(a, id);
        if(oa.isPresent()){
            Alumno x = oa.orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(x);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } 

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id)
    {
        Optional<Alumno> oa = as.delete(id);
        if (oa.isPresent()) {
            Alumno x = oa.orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(x);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PutMapping("/{id}/{x}")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @PathVariable int x)
    {
        Optional<Alumno> oa = as.updateEstado(id, x);
        if (oa.isPresent()) {
            Alumno a = oa.orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(a);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("/aula/{id}")
    public ResponseEntity<?> buscarAula(@PathVariable Long id)
    {
        //Optional<Aula> oa = as.findWithHorariosAlumnos(id);
        Optional<Aula> oa = as.findAulaByAlumno(id);
        if(oa.isPresent()){
            return ResponseEntity.ok().body(oa.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    
    @GetMapping("/materias/{x1}/{x2}")
    public List<AlumnoMateriaDto2> getMaterias( @PathVariable(name = "x1")  Long idAlumno, 
                                                @PathVariable(name = "x2")  Long idGrado)
    {
        return as.findMaterias(idAlumno, idGrado);
    }

    
    @GetMapping("materias/{id}")
    public AlumnoMateriaDto3 buscarMateria(@PathVariable Long id)
    {
        return as.findMateria(id);
    }


    @GetMapping("/grados/{id}")
    public List<Grado> getGrados(@PathVariable Long id){
        return as.findGradosByAlumno(id);
    }


    @GetMapping("/materias-todo/{id}")
    public List<AlumnoMateria> findAllByAlumno(@PathVariable Long id) {
        return as.findAllByAlumno(id);
    }

}