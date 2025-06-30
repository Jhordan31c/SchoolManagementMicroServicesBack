package com.school.academic.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.school.academic.models.Area;

public interface AreaRepository extends CrudRepository<Area,Long>{
    
    @Query("SELECT a FROM Area a LEFT JOIN FETCH a.materias WHERE a.id=?1")
    Optional<Area> findWithAreas(Long id);
    
}
