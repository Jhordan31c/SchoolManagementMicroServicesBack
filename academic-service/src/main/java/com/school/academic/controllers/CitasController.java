package com.school.academic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.academic.models.Cita;
import com.school.academic.services.CitaService;

@RestController
@RequestMapping("/citas")
public class CitasController {
    
    @Autowired
    CitaService cs;

    @GetMapping
    public List<Cita> lista(){
        return cs.findAll();
    }
    
    @GetMapping("/estado/{x}")
    public List<Cita> listaPorEstado(@PathVariable int x){
        return cs.findAllEstado(x);
    }


    @GetMapping("/estado/{x}/alumno/{id}")
    public List<Cita> listaPorEstadoAlumno  (@PathVariable int x, @PathVariable Long id){
        return cs.findAllEstadoAlumno(x, id);
    }

    @GetMapping("/estado/{x}/docente/{id}")
    public List<Cita> listaPorEstadoDocente (@PathVariable int x, @PathVariable Long id){
        return cs.findAllEstadoDocente(x, id);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?>  buscar(@PathVariable Long id)
    {
        Optional<Cita> oc = cs.findById(id);
        if(oc.isPresent()){
            Cita x = oc.orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(x);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //CRUD
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Cita c){
        Cita x = cs.create(c).orElseThrow();
        return ResponseEntity.ok().body(x);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cita c)
    {
        Optional<Cita> oc = cs.update(c, id);
        if (oc.isPresent()) {
            return ResponseEntity.ok().body(oc.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id)
    {
        Optional<Cita> oc = cs.delete(id);
        if (oc.isPresent()) {
            return ResponseEntity.ok().body(oc.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @PutMapping("/{id}/{x}")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @PathVariable int x)
    {
        Optional<Cita> oc = cs.updateEstado(id, x);
        if (oc.isPresent()) {
            return ResponseEntity.ok().body(oc.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
