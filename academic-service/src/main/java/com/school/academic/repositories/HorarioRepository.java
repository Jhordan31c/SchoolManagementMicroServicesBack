package com.school.academic.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.school.academic.models.Horario;

public interface HorarioRepository extends CrudRepository<Horario, Long>{

    @Query("SELECT new Horario(h.id, h.docente, h.materia, h.dia, h.inicio, h.fin) FROM Horario h WHERE h.aula.id = ?1")    
    Set<Horario> findAllByAula(Long aulaId);

    @Query("SELECT h FROM Horario h WHERE h.aula.id = ?1 AND h.dia = ?2")    
    List<Horario> findAllByAulaAndDia(Long aulaId, Integer dia);
    // @Query("SELECT a FROM Alumno a WHERE a.estado =?1")
    // List<Alumno> findAllByEstado(Integer estado);

    @Query("SELECT DISTINCT new Horario(h.materia, h.docente, h.aula) FROM Horario h WHERE h.aula.id = ?1")
    Set<Horario> findDistinctMateriasByAula(Long aulaId);

    @Query("SELECT DISTINCT new Horario(h.materia, h.docente, h.aula) FROM Horario h WHERE h.docente.id = ?1 AND YEAR(h.aula.fecha_registro) = YEAR(CURRENT_DATE)")
    Set<Horario> findDistinctMateriasByDocente(Long docenteId);


    @Query("SELECT new Horario("
        +"new Aula(a.id, a.grado, a.sub_grado, a.estado, a.fecha_registro, a.tutor)," 
        +"h.materia, h.dia, h.inicio, h.fin) FROM Horario h LEFT JOIN h.aula a WHERE h.docente.id = ?1")
    List<Horario> findAllByDocente2(Long docenteId);

    //@Query("SELECT new Horario(h.aula, h.materia, h.dia, h.inicio, h.fin) FROM Horario h WHERE h.docente.id = ?1 AND EXTRACT(YEAR FROM h.aula.fecha_registro) = ?2")
    
    @Query("SELECT new Horario("
        +"new Aula(a.id, a.grado, a.sub_grado, a.estado, a.fecha_registro, a.tutor)," 
        +"h.materia, h.dia, h.inicio, h.fin) FROM Horario h LEFT JOIN h.aula a WHERE h.docente.id = ?1 AND YEAR(a.fecha_registro) = YEAR(CURRENT_DATE)")
    List<Horario> findAllByDocente(Long docenteId);

}