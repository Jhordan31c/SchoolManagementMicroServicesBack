package com.school.auth.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.school.auth.models.Rol;

public interface RolRepository extends CrudRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre);
}
