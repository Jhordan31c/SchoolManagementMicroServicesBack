package com.school.academic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.academic.models.Grado;
import com.school.academic.services.GradosService;

@RestController
@RequestMapping("/grados")
public class GradoController {
    
    @Autowired
    GradosService gs;

    @GetMapping
    public List<Grado> getLista(){
        return gs.findAll();
    }

    @GetMapping("/nivel/{x}")
    public List<Grado> getLista(@PathVariable(name = "x") String nivel){
        return gs.findAll(nivel);
    }

    @GetMapping("/niveles")
    public List<String> getNiveles(){
        return gs.findNiveles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id)
    {
        Optional<Grado> op = gs.findById(id);
        if(op.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(op.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
