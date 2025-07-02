package com.school.payment.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.payment.clients.AlumnoClient;
import com.school.payment.dto.AlumnoDto;
import com.school.payment.dto.ContadorDto2;
import com.school.payment.models.Pago;
import com.school.payment.models.ParametroPaga;
import com.school.payment.repositories.PagoRepository;
import com.school.payment.dto.PagoConAlumnoDto;
import com.school.payment.repositories.ParametroPagaRepository;

@Service
public class PagoServiceImpA implements PagoService {

    @Autowired
    AlumnoClient ac;

    @Autowired
    PagoRepository pr;

    @Autowired
    ParametroPagaRepository ppr;

    @Transactional(readOnly = true)
    @Override
    public List<Pago> findAllByIdAlumno(Long id) {
        return pr.findAllByIdAlumno(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pago> findAllByIdAlumnoByYear(Long id, Integer year) {
        return pr.findAllByIdAlumnoAndYear(id, year);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pago> findAll() {
        return (List<Pago>) pr.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Pago> findById(Long id) {
        return pr.findById(id);
    }

    @Transactional
    @Override
    public Pago create(Pago p) {
        p.setId(null);
        p.setEstado(1);
        return pr.save(p);
    }

    @Transactional
    @Override
    public Optional<Pago> update(Pago p, Long id) {
        Optional<Pago> op = pr.findById(id);
        if (op.isPresent()) {
            Pago x = op.orElseThrow();

            if (p.getNombre() != null) {
                x.setNombre(p.getNombre());
            }
            if (p.getDetalle() != null) {
                x.setDetalle(p.getDetalle());
            }
            if (p.getFecha_vencimiento() != null) {
                x.setFecha_vencimiento(p.getFecha_vencimiento());
            }
            if (p.getFecha_pago() != null) {
                x.setFecha_pago(p.getFecha_pago());
            }
            if (p.getAlumnoId() != null) {
                x.setAlumnoId(p.getAlumnoId());
            }

            x.setMonto(p.getMonto());
            x.setEstado(p.getEstado());

            Pago x2 = pr.save(x);
            return Optional.of(x2);
        }
        return op;
    }

    @Transactional
    @Override
    public Optional<Pago> delete(Long id) {
        Optional<Pago> op = pr.findById(id);
        op.ifPresent(x -> pr.delete(x));
        return op;
    }

    @Transactional
    @Override
    public void generalCreate(Pago pagoTemplate, Integer estado) {
        try {
            // 1. Obtener alumnos desde academic-service
            List<AlumnoDto> alumnos = ac.findAllByEstado(estado);

            // 2. Crear pagos para cada alumno
            List<Pago> pagosToCreate = alumnos.stream()
                    .map(alumno -> {
                        Pago nuevoPago = new Pago();
                        // Copiar datos del template
                        nuevoPago.setNombre(pagoTemplate.getNombre());
                        nuevoPago.setDetalle(pagoTemplate.getDetalle());
                        nuevoPago.setMonto(pagoTemplate.getMonto());
                        nuevoPago.setFecha_vencimiento(pagoTemplate.getFecha_vencimiento());
                        nuevoPago.setEstado(1); // Estado inicial

                        // ✅ Solo guardar el ID del alumno, no la entidad
                        nuevoPago.setAlumnoId(alumno.getId());

                        return nuevoPago;
                    })
                    .collect(Collectors.toList());

            // 3. Guardar todos los pagos en batch
            pr.saveAll(pagosToCreate);

        } catch (Exception e) {
            // Log del error y re-lanzar
            System.err.println("Error al crear pagos generales: " + e.getMessage());
            throw new RuntimeException("Error al comunicarse con academic-service", e);
        }
    }

    @Transactional(readOnly = true)
    public List<PagoConAlumnoDto> findAllByIdAlumnoWithAlumnoData(Long alumnoId) {
        try {
            // 1. Obtener pagos del repositorio local
            List<Pago> pagos = pr.findAllByIdAlumno(alumnoId);

            // 2. Obtener datos del alumno desde academic-service
            AlumnoDto alumno = ac.findById(alumnoId);

            // 3. Combinar datos
            return pagos.stream()
                    .map(pago -> new PagoConAlumnoDto(pago, alumno))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error al obtener datos del alumno: " + e.getMessage());
            // Devolver solo los pagos sin datos del alumno
            List<Pago> pagos = pr.findAllByIdAlumno(alumnoId);
            return pagos.stream()
                    .map(pago -> new PagoConAlumnoDto(pago, (AlumnoDto) null))
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    @Override
    public Optional<Pago> updateEstado(Long id, int estado) {
        Optional<Pago> op = pr.findById(id);

        if (op.isPresent()) {
            Pago x = op.orElseThrow();
            x.setEstado(estado);
            return Optional.of(pr.save(x));
        }

        return op;
    }

    @Transactional
    @Override
    public List<ParametroPaga> createParametrosPago(List<ParametroPaga> lista) {
        lista.forEach(pp -> ppr.save(pp));

        return (List<ParametroPaga>) ppr.findAll();
    }

    @Transactional
    @Override
    public List<ParametroPaga> updateParametrosPago(List<ParametroPaga> lista) {
        lista.forEach(pp -> {
            Optional<ParametroPaga> op = ppr.findById(pp.getId());
            op.ifPresent(x -> {
                x.setPrecioMatricula(pp.getPrecioMatricula());
                x.setPrecioPension(pp.getPrecioPension());
                x.setDia_vencimiento(pp.getDia_vencimiento());
                x.setMora(pp.getMora());
            });
        });

        return (List<ParametroPaga>) ppr.findAll();
    }

    @Transactional
    @Override
    public void eliminarPagosPorAlumnoYAula(Long alumnoId, Long aulaId) {
        List<Pago> pagos = pr.findByAlumnoIdAndAulaId(alumnoId, aulaId);
        pr.deleteAll(pagos);
    }

    @Transactional
    @Override
    public void finalizarPagosPorAlumnoYAula(Long alumnoId, Long aulaId) {
        List<Pago> pagos = pr.findByAlumnoIdAndAulaId(alumnoId, aulaId);
        pagos.forEach(pago -> pago.setEstado(2)); // Estado finalizado
        pr.saveAll(pagos);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParametroPaga> findParametroPagas() {
        return (List<ParametroPaga>) ppr.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ContadorDto2> obtenerRecaudacionMensual() {
        return pr.obtenerRecaudacionMensual();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ContadorDto2> obtenerRecaudacionMensual(int year) {
        return pr.obtenerRecaudacionMensual(year);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pago> findPagosVencidosByAlumno(Long alumnoId) {
        return pr.findPagosVencidosByAlumno(alumnoId);
    }

    @Transactional(readOnly = true)
    @Override
    public int countPagosPendientesByAlumno(Long alumnoId) {
        return pr.countPagosPendientesByAlumno(alumnoId);
    }

    @Transactional(readOnly = true)
    @Override
    public Double sumMontosPagadosByAlumno(Long alumnoId) {
        return pr.sumMontosPagadosByAlumno(alumnoId);
    }

}
