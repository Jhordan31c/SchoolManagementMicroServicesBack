package com.school.academic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.academic.models.Area;
import com.school.academic.models.Materia;
import com.school.academic.services.MateriaService;

@RestController
@RequestMapping("/materias")
public class MateriasController {

    @Autowired
    MateriaService ms;


    @GetMapping
    public List<Materia> listaMaterias(){
        return ms.findAllMaterias();
    }

    @GetMapping("/areas")
    public List<Area> listaAreas(){
        return ms.findAllAreas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id)
    {   
        Optional<Materia> om = ms.findById(id);
        if(om.isPresent()){
            return ResponseEntity.ok().body(om.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }



    //Para que pertenesca a un area, debes poner el json del area en cuestion
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Materia m){
        return ResponseEntity.ok().body(ms.create(m));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Materia m)
    {
        Optional<Materia> om =ms.update(id, m);
        if(om.isPresent()){
            return ResponseEntity.ok().body(om.orElseThrow());
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id)
    {   
        Optional<Materia> om = ms.delete(id);
        if(om.isPresent()){
            return ResponseEntity.ok().body(om.orElseThrow());
        }
        return ResponseEntity.badRequest().build();
    }

}
