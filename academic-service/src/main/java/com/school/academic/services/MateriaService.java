package com.school.academic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.academic.models.Area;
import com.school.academic.models.Materia;
import com.school.academic.repositories.AreaRepository;
import com.school.academic.repositories.MateriaRepository;

@Service
public class MateriaService {

    @Autowired
	private MateriaRepository mr;

	@Autowired
	private AreaRepository ar;

    
    @Transactional(readOnly = true)
    public List<Materia> findAllMaterias(){
        return (List<Materia>) mr.findAll();
    }

    @Transactional(readOnly = true)
    public List<Area> findAllAreas(){
        return (List<Area>) ar.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Materia> findById(Long id){
        return mr.findById(id);
    }


    @Transactional
    public Materia create(Materia m){
        m.setId(null);
        return mr.save(m);
    }

    @Transactional
    public Optional<Materia> update(Long id, Materia m){
        Optional<Materia> om = mr.findById(id);
        if(om.isPresent()){
            Materia x = om.orElseThrow();

            if (m.getNombre()!=null) {
                x.setNombre(m.getNombre());
            }
            if (m.getArea()!=null) {
                x.setArea(m.getArea());
            }
            
            Materia x2 = mr.save(x);
            return Optional.of(x2);
        }
        return om;
    }

    @Transactional
    public Optional<Materia> delete(Long id){
        Optional<Materia> om = mr.findById(id);
        om.ifPresent(x->mr.delete(x));
        return om;
    }

}
