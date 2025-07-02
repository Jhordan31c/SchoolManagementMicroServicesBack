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

import com.school.academic.models.Evento;
import com.school.academic.services.EventosService;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    EventosService es;

    @GetMapping
    public List<Evento> lista(){
        return es.findAll();
    }

    @GetMapping("filtro/{año}")
    public List<Evento> lista(@PathVariable Integer año){
        return es.findAllByYear(año);
    }

    @GetMapping("filtro/{año}/{mes}")
    public List<Evento> lista(@PathVariable Integer año, @PathVariable Integer mes){
        return es.findAllByMonth(año, mes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id)
    {
        Optional<Evento> oe = es.findById(id);
        if (oe.isPresent()) {
            return ResponseEntity.ok().body(oe.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    
    //CRUD
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Evento e){   
        Evento x = es.create(e).orElseThrow();
        return ResponseEntity.ok().body(x);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Evento e)
    {
        Optional<Evento> oe = es.update(e, id);
        if(oe.isPresent()){
            return ResponseEntity.ok().body(oe.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id)
    {
        Optional<Evento> oe = es.delete(id);
        if(oe.isPresent()){
            return ResponseEntity.ok().body(oe.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
