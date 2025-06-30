package com.school.payment.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.school.payment.models.ParametroPaga;

public interface ParametroPagaRepository extends CrudRepository<ParametroPaga, Integer>{
    @Query("SELECT p FROM ParametroPaga p WHERE p.nivel =?1")
    Optional<ParametroPaga> findByNivel(int nivel);
}
