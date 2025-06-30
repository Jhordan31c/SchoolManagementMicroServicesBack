package com.school.academic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.academic.models.Grado;
import com.school.academic.repositories.GradoRepository;

@Service
public class GradosService {
    
    @Autowired
    GradoRepository gr;

    @Transactional(readOnly = true)
    public List<Grado> findAll(){
        return (List<Grado>) gr.findAll();
    }

    @Transactional(readOnly = true)
    public List<Grado> findAll(String nivel){
        return gr.findByNivel(nivel);
    }


    @Transactional(readOnly = true)
    public List<String> findNiveles(){
        return gr.findNiveles();
    }   

    @Transactional(readOnly = true)
    public Optional<Grado> findById(Long id){
        return gr.findById(id);
    }
    

}
