package com.school.academic.repositories;

import org.springframework.data.repository.CrudRepository;

import com.school.academic.models.Materia;

public interface MateriaRepository extends CrudRepository<Materia, Long>{
    
}
