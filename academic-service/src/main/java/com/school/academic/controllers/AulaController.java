package com.school.academic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.academic.dto.AulaDto;
import com.school.academic.models.Aula;
import com.school.academic.services.AulaService;

@RestController
@RequestMapping("/aulas")
public class AulaController {
    
    @Autowired
    AulaService as;
    
    @GetMapping
    public List<Aula> lista(){
        return as.findAll();
    }


    @GetMapping("/estado/{x}")
    public List<Aula> listaActivo(@PathVariable int x){
        return as.findAllEstado(x);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id)
    {
        //Optional<Aula> oa = as.findWithHorariosAlumnos(id);
        Optional<Aula> oa = as.find(id);
        if(oa.isPresent()){
            return ResponseEntity.ok().body(oa.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping()
    public ResponseEntity<?> registrar(@RequestBody AulaDto a){
        return ResponseEntity.ok().body(as.create(a));
    }


    @PutMapping("/{id}/{x}")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @PathVariable int x)
    {
        Optional<Aula> op = as.updateEstado(id, x);
        if (op.isPresent()) {
            return ResponseEntity.ok().body(op.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    
    
}
