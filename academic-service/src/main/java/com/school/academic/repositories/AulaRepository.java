package com.school.academic.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.school.academic.dto.ContadorDto;
import com.school.academic.models.Aula;

public interface AulaRepository extends CrudRepository<Aula, Long>{
   
    
    @Query("SELECT new Aula(a.id, a.grado, a.sub_grado, a.estado, a.fecha_registro, a.tutor) FROM Aula a")
    List<Aula> findAllBasico();

    @Query("SELECT new Aula(a.id, a.grado, a.sub_grado, a.estado, a.fecha_registro, a.tutor) FROM Aula a WHERE a.estado = ?1")
    List<Aula> findAllBasicoByEstado(int estado);

    @Query("SELECT new Aula(a.id, a.grado, a.sub_grado, a.estado, a.fecha_registro, a.tutor) FROM Aula a WHERE a.id=?1")
    Optional<Aula> findBase(Long id);


    @Query("SELECT new Aula(a.id, a.grado, a.sub_grado, a.estado, a.fecha_registro, a.tutor) FROM Aula a WHERE a.id=?1")
    Optional<Aula> findBaseByAlumno(Long id);


    //Extras
    @Query("SELECT a FROM Aula a LEFT JOIN FETCH a.alumnos WHERE a.id=?1")
    Optional<Aula> findWithAlumnos(Long id);

    @Query("SELECT a FROM Aula a LEFT JOIN FETCH a.horarios WHERE a.id=?1")
    Optional<Aula> findWithHorarios(Long id);

    @Query("SELECT a FROM Aula a LEFT JOIN FETCH a.alumnos LEFT JOIN FETCH a.horarios WHERE a.id=?1")
    Optional<Aula> findWithAlumnosHorarios(Long id);


    @Query("SELECT new Aula(a.id, a.grado, a.sub_grado, a.estado, a.fecha_registro, a.tutor) FROM Aula a JOIN a.alumnos al "
            +"WHERE YEAR(a.fecha_registro) = YEAR(CURRENT_DATE) "
            +"AND al.id = ?1")
    Optional<Aula> findByAlumno(Long id);

    long countByEstado(int estado);

    @Query("SELECT NEW com.school.academic.dto.ContadorDto(a.estado, COUNT(a)) AS cantidad FROM Aula a "
                        + "GROUP BY a.estado")
    List<ContadorDto> contarAulasEstado();



}
