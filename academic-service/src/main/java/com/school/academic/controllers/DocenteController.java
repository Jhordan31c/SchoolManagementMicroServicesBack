package com.school.academic.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.school.academic.dto.AlumnoMateriaDto;
import com.school.academic.dto.AulaAsignadaDto;
import com.school.academic.dto.HorarioDocenteDto;
import com.school.academic.models.Alumno;
import com.school.academic.models.Docente;
import com.school.academic.services.DocenteService;
import com.school.academic.validations.Validacion;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/docentes")
public class DocenteController {

    @Autowired
    DocenteService ds;


    @Autowired 
    private Validacion v;

    @GetMapping()
    public List<Docente> lista(){
        return ds.findAll();
    }

    @GetMapping("/estado/{x}")
    public List<Docente> lista2(@PathVariable int x){
        return ds.findAll(x);
    }
    
    @GetMapping("/basico")
    public List<Docente> lista3(){
        return ds.findAllBasico();
    }


    

    @GetMapping("/horario/{id}")
    public List<HorarioDocenteDto> listaHorario(@PathVariable Long id){   
        return ds.HorariosPorDocente(id);
    }

    @GetMapping("/aulas-asignadas/{id}")
    public List<AulaAsignadaDto> aulasAsigandas(@PathVariable Long id){
        return ds.getAulasAsignadas(id);
    }

    @GetMapping("/alumnos-materias/{x1}/{x2}/{x3}")
    public List<AlumnoMateriaDto> alumnosMaterias(  @PathVariable(name = "x1") Long idDocente   ,
                                                    @PathVariable(name = "x2") Long idAula      ,
                                                    @PathVariable(name = "x3") Long id_Materia  )
    {
        return ds.getAlumnosMateria(idDocente, idAula, id_Materia);
    }

    @PutMapping("/alumnos-materias")
    public void aupdateAlumnosMateria(@RequestBody List<AlumnoMateriaDto> lista){
        ds.updateAlumnosMateria(lista);
    }


    @GetMapping("/alumnos-correspondientes/{x}")
    public List<Alumno> alumnosCorrespondientes(@PathVariable(name = "x") Long id){
        return ds.getAlumnosCorrespondientes(id);
    }






    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id)
    {
        Optional<Docente> od = ds.findById(id);
        if (od.isPresent()) {
            return ResponseEntity.ok().body(od.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }



    
    //CRUD
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Docente d, BindingResult br){   
        if(br.hasFieldErrors()){
            return v.informe(br);
        }
        return ResponseEntity.ok().body(ds.create(d));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Docente d)
    {
        Optional<Docente> od = ds.update(id, d);
        if (od.isPresent()) {
            return ResponseEntity.ok().body(od.orElseThrow());
        }
        return ResponseEntity.badRequest().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id)
    {
        Optional<Docente> od = ds.delete(id);
        if (od.isPresent()) {
            return ResponseEntity.ok().body(od.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }





    @PutMapping("/{id}/{x}")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @PathVariable int x)
    {
        Optional<Docente> od = ds.updateEstado(id, x);
        if (od.isPresent()) {
            return ResponseEntity.ok().body(od.orElseThrow());
        }
        return ResponseEntity.badRequest().build();
    }

}
