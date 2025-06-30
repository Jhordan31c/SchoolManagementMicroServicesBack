package com.school.academic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.school.academic.models.AlumnoMateria;
import com.school.academic.models.Grado;

public interface AlumnoMateriaRepository extends CrudRepository<AlumnoMateria, Long> {

    
    @Query("SELECT am FROM AlumnoMateria am WHERE am.alumno.id=?1")
    List<AlumnoMateria> findAllByIdAlumno(Long id);

    @Query("SELECT am FROM AlumnoMateria am WHERE am.alumno.id=?1 AND am.aula.id=?2")
    List<AlumnoMateria> findByAlumnoAula(Long idAlumno, Long idAula);

    @Query("SELECT am FROM AlumnoMateria am WHERE am.alumno.id=?1 AND am.grado.id=?2")
    List<AlumnoMateria> findAllByIdAlumnoGrado(Long idAlumno, Long idGrado);

    @Query("SELECT am FROM AlumnoMateria am WHERE am.docente.id=?1 AND am.aula.id=?2 AND am.materia.id=?3")
    List<AlumnoMateria> findByDocenteAulaMateria(Long idDocente, Long idAula, Long idMateria);

    //@Query("SELECT DISTINCT(am.grado.id) FROM AlumnoMateria am WHERE am.alumno.id=?1")
    //List<Long> findAllGradosByAlumno(Long idAlumno);
    
    @Query("SELECT DISTINCT am.grado FROM AlumnoMateria am WHERE am.alumno.id=?1")
    List<Grado> findAllGradosByAlumno(Long idAlumno);


}
