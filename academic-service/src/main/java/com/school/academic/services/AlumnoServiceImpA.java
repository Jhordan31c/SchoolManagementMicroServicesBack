package com.school.academic.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.school.academic.dto.AlumnoMateriaDto2;
import com.school.academic.dto.AlumnoMateriaDto3;
import com.school.academic.models.Alumno;
import com.school.academic.models.AlumnoMateria;
import com.school.academic.models.Aula;
import com.school.academic.models.Grado;
import com.school.academic.repositories.AlumnoMateriaRepository;
import com.school.academic.repositories.AlumnoRepository;
import com.school.academic.repositories.ApoderadoRepository;
import com.school.academic.repositories.AulaRepository;
import com.school.academic.repositories.HorarioRepository;
import com.school.academic.clients.PaymentClient;
import com.school.academic.clients.UserClient;
import com.school.academic.dto.UserDto;

@Service
public class AlumnoServiceImpA implements AlumnoService {

    private static final Logger log = LoggerFactory.getLogger(AlumnoServiceImpA.class);

    @Autowired
    ApoderadoRepository apr;

    @Autowired
    AlumnoRepository ar;

    @Autowired
    AlumnoMateriaRepository amr;

    @Autowired
    private HorarioRepository hr;

    @Autowired
    AulaRepository aur;

    @Autowired
    private UserClient userClient;

    @Autowired
    private PaymentClient paymentClient;

    // LISTA BASE (id, dni, nombre, apellido, genero, fecha_naciemiento, estado,
    // apoderado)
    @Transactional(readOnly = true)
    @Override
    public List<Alumno> findAll() {
        return (List<Alumno>) ar.findAllBasico();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Alumno> findAll(int estado) {
        return (List<Alumno>) ar.findAllBasico(estado);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Alumno> findById(Long id) {
        return ar.findById(id);
    }

    // EXTRAS
    @Transactional(readOnly = true)
    @Override
    public Optional<Alumno> findByIdWithMaterias(Long id) {
        return ar.findByIdWithMaterias(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Alumno> findByIdWithPagos(Long id) {
        return ar.findByIdWithPagos(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Alumno> findAllByEstado(Integer estado) {
        return ar.findAllByEstado(estado);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Alumno> findByIdWithMateriasPago(Long id) {
        return ar.findByIdWithApoderadoMateriasPagos(id);
    }

    // CRUD
    @Transactional
    @Override
    public Optional<Alumno> create(Alumno a) {
        a.setId(null);
        a.setFecha_registro(new Date());
        a.setEstado(1);

        if (a.getUser() != null) {
            try {
                // Crear usuario en auth-service
                UserDto userRequest = a.getUser();
                UserDto createdUser = userClient.createUser(userRequest);

                a.setUser(createdUser); // Para retornar los datos completos

            } catch (Exception e) {
                log.error("Error creando usuario para alumno: {}", e.getMessage());
                throw new RuntimeException("No se pudo crear el usuario para el alumno");
            }
        }

        Alumno x = ar.save(a);
        return Optional.ofNullable(x);
    }

    @Override
    @Transactional
    public Optional<Alumno> update(Alumno a, Long id) {
        Optional<Alumno> op = ar.findByIdWithApoderado(id);
        if (op.isPresent()) {
            Alumno x = op.orElseThrow();
            
            if (a.getDni() != null) {
                x.setDni(a.getDni());
            }
            if (a.getNombre() != null) {
                x.setNombre(a.getNombre());
            }
            if (a.getApellido() != null) {
                x.setApellido(a.getApellido());
            }
            if (a.getGenero() == 'M' || a.getGenero() == 'F') {
                x.setGenero(a.getGenero());
            }
            if (a.getFecha_nacimiento() != null) {
                x.setFecha_nacimiento(a.getFecha_nacimiento());
            }
            if (a.getApoderado() != null) {
                x.setApoderado(a.getApoderado());
            }

            try {
                if (x.getUser() != null && a.getUser() != null) {
                    // Actualizar usuario existente
                    UserDto updatedUser = userClient.updateUser(x.getUserId(), a.getUser());
                    x.setUser(updatedUser);
                    
                } else if (x.getUser() == null && a.getUser() != null) {
                    // Crear nuevo usuario
                    UserDto createdUser = userClient.createUser(a.getUser());
                    x.setUser(createdUser);
                }
            } catch (Exception e) {
                log.error("Error actualizando usuario para alumno {}: {}", id, e.getMessage());
            }
            
            Alumno x2 = ar.save(x);
            return Optional.of(x2);
        }
        return op;
    }

    @Transactional
    @Override
    public Optional<Alumno> delete(Long id) {
        Optional<Alumno> op = ar.findById(id);
        op.ifPresent(x -> ar.delete(x));
        return op;
    }

    @Transactional
    @Override
    public Optional<Alumno> updateEstado(Long id, int estado) {
        Optional<Alumno> op = ar.findByIdWithApoderado(id);

        if (op.isPresent()) {
            Alumno x = op.orElseThrow();
            x.setEstado(estado);
            return Optional.of(ar.save(x));
        }

        return op;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Alumno> findByUser(Long id) {
        return ar.findByIdUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Aula> findAulaByAlumno(Long id) {

        Optional<Aula> oa = aur.findByAlumno(id);

        if (oa.isPresent()) {
            Aula x = oa.orElseThrow();
            x.setHorarios(hr.findAllByAula(x.getId()));
            x.setAlumnos(ar.findAllByAula(x.getId()));
            return Optional.of(x);
        }

        return oa;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Alumno> findAllDisponible(int x) {
        return ar.findAllDisponible(x);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Grado> findGradosByAlumno(Long id) {
        return amr.findAllGradosByAlumno(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AlumnoMateriaDto2> findMaterias(Long idAlumno, Long grado) {
        List<AlumnoMateriaDto2> lista = new ArrayList<>();

        List<AlumnoMateria> materias = amr.findAllByIdAlumnoGrado(idAlumno, grado);
        materias.forEach(m -> {
            AlumnoMateriaDto2 dto = new AlumnoMateriaDto2(
                    m.getId(),
                    m.getMateria().getNombre(),
                    m.getMateria().getArea().getNombre());

            lista.add(dto);
        });

        return lista;
    }

    @Transactional(readOnly = true)
    @Override
    public AlumnoMateriaDto3 findMateria(Long idMateria) {
        AlumnoMateria am = amr.findById(idMateria).orElseThrow();
        AlumnoMateriaDto3 dto = new AlumnoMateriaDto3(
                am.getId(),
                am.getDocente().getApellido().concat(", ").concat(am.getDocente().getNombre()),
                am.getMateria().getNombre(),
                am.getBimestres());
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AlumnoMateria> findAllByAlumno(Long id) {
        return amr.findAllByIdAlumno(id);
    }

}
