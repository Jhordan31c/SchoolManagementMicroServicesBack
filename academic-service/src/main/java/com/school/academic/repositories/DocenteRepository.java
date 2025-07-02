package com.school.academic.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import com.school.academic.dto.ContadorDto;
import com.school.academic.models.Docente;

public interface DocenteRepository extends CrudRepository<Docente, Long> {

    @Query("SELECT new Docente(d.id, d.dni, d.nombre, d.apellido, d.correo, d.fecha_registro, d.estado) FROM Docente d")
    List<Docente> findAllBasico();

    @Query("SELECT d FROM Docente d WHERE d.estado = ?1")
    List<Docente> findAllBasico(int estado);

    @Query("SELECT d FROM Docente d WHERE d.userId = :userId")
    Optional<Docente> findByIdUser(@Param("userId") Long userId);

    @Query("SELECT d FROM Docente d WHERE d.userId IS NOT NULL AND d.estado = :estado")
    List<Docente> findByUserIdNotNullAndEstado(@Param("estado") int estado);

    @Query("SELECT COUNT(d) > 0 FROM Docente d WHERE d.userId = :userId")
    boolean existsByUserId(@Param("userId") Long userId);

    @Query("SELECT NEW com.school.academic.dto.ContadorDto(d.estado, COUNT(d)) FROM Docente d "
            + "GROUP BY d.estado")
    List<ContadorDto> contarDocentesEstado();

    long countByEstado(int estado);

}
