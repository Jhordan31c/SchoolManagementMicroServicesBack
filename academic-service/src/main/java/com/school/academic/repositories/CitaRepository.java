package com.school.academic.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.school.academic.dto.ContadorDto;
import com.school.academic.models.Cita;

public interface CitaRepository extends CrudRepository<Cita, Long> {

   
    @Query("SELECT new Cita(c.id,"
        +"new Docente(d.id, d.dni, d.nombre, d.apellido, d.correo), "
        +"new Alumno(a.id, a.dni, a.nombre, a.apellido, a.genero, a.fecha_nacimiento, a.estado), "
        +"c.fecha, c.inicio, c.titulo, c.detalle, c.estado, c.mensaje) "
        +"FROM Cita c LEFT JOIN c.alumno a LEFT JOIN c.docente d")
    List<Cita> findAllBasico();
    
    @Query("SELECT new Cita(c.id,"
        +"new Docente(d.id, d.dni, d.nombre, d.apellido, d.correo), "
        +"new Alumno(a.id, a.dni, a.nombre, a.apellido, a.genero, a.fecha_nacimiento, a.estado), "
        +"c.fecha, c.inicio, c.titulo, c.detalle, c.estado, c.mensaje) "
        +"FROM Cita c LEFT JOIN c.alumno a LEFT JOIN c.docente d "
        +"WHERE c.estado=?1")
    List<Cita> findAllBasicoByEstado(int estado);


    @Query("SELECT new Cita(c.id,"
        +"new Docente(d.id, d.dni, d.nombre, d.apellido, d.correo), "
        +"new Alumno(a.id, a.dni, a.nombre, a.apellido, a.genero, a.fecha_nacimiento, a.estado), "
        +"c.fecha, c.inicio, c.titulo, c.detalle, c.estado, c.mensaje) "
        +"FROM Cita c LEFT JOIN c.alumno a LEFT JOIN c.docente d "
        +"WHERE c.estado=?1 "
        +"AND c.alumno.id = ?2")
    List<Cita> findAllBasicoByEstadoByAlumno(int estado, Long idAlumno);

    @Query("SELECT new Cita(c.id,"
        +"new Docente(d.id, d.dni, d.nombre, d.apellido, d.correo), "
        +"new Alumno(a.id, a.dni, a.nombre, a.apellido, a.genero, a.fecha_nacimiento, a.estado), "
        +"c.fecha, c.inicio, c.titulo, c.detalle, c.estado, c.mensaje) "
        +"FROM Cita c LEFT JOIN c.alumno a LEFT JOIN c.docente d "
        +"WHERE c.estado=?1 "
        +"AND c.docente.id = ?2")
    List<Cita> findAllBasicoByEstadoByDocente(int estado, Long idDocente);


    @Query("SELECT new Cita(c.id,"
        +"new Docente(d.id, d.dni, d.nombre, d.apellido, d.correo), "
        +"new Alumno(a.id, a.dni, a.nombre, a.apellido, a.genero, a.fecha_nacimiento, a.estado), "
        +"c.fecha, c.inicio, c.titulo, c.detalle, c.estado, c.mensaje ) "
        +"FROM Cita c LEFT JOIN c.alumno a LEFT JOIN c.docente d "
        +"WHERE c.id=?1")
    Optional<Cita> findByIdBasico(Long id);

    long countByEstado(int estado);

    
    @Query("SELECT NEW com.school.academic.dto.ContadorDto(MONTH(c.fecha), COUNT(c)) FROM Cita c "
            +"WHERE YEAR(c.fecha) = YEAR(CURRENT_DATE) "
            +"GROUP BY MONTH(c.fecha)")
    List<ContadorDto> obtenerCantidadPorMes();

}
