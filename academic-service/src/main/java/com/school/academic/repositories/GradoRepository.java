package com.school.academic.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.school.academic.models.Grado;
import java.util.List;


public interface GradoRepository extends CrudRepository<Grado, Long>{
    
    List<Grado> findByNivel(String nivel);

    @Query("SELECT DISTINCT g.nivel FROM Grado g ")
    List<String> findNiveles();
}
