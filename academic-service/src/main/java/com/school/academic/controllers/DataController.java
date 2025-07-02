package com.school.academic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.academic.dto.ContadorDto;
import com.school.academic.dto.ContadorDto2;
import com.school.academic.services.DataService;

@RestController
@RequestMapping("/datos")
public class DataController {

    @Autowired
    DataService ds;

    @GetMapping("/eventos")
    public List<ContadorDto> eventosPorMes() {
        return ds.eventosPorMes();
    }

    @GetMapping("/eventos/{x}") // Por año
    public List<ContadorDto> eventosPorMes(@PathVariable int x) {
        return ds.eventosPorMes(x);
    }

    @GetMapping("/recaudacion")
    public List<ContadorDto2> recaudacionPorMes() {
        return ds.recaudacionPorMeses();
    }

    @GetMapping("/recaudacion/{x}")
    public List<ContadorDto2> recaudacionPorMes(@PathVariable int x) {
        return ds.recaudacionPorMeses(x);
    }

    @GetMapping("/alumnos-estado")
    public List<ContadorDto> alumnosPorEstado() {
        return ds.alumnosPorEstado();
    }

    @GetMapping("/citas")
    public List<ContadorDto> citasPorMes() {
        return ds.citasPorMeses();
    }


    @GetMapping("/alumnos-estado/{x}")
    public long alumnosPorEstado(@PathVariable int x) {
        return ds.alumnosPorEstado(x);
    }

    @GetMapping("/docentes-estado")
    public List<ContadorDto> docentesPorEstado() {
        return ds.docentesPorEstado();
    }

    @GetMapping("/docentes-estado/{x}")
    public long docentesPorEstado(@PathVariable int x) {
        return ds.docentesPorEstado(x);
    }

    @GetMapping("/citas-estado/{x}")
    public long citasPorEstado(@PathVariable int x) {
        return ds.citasPorEstado(x);
    }

    @GetMapping("/aulas-estado/{x}")
    public long aulasPorEstado(@PathVariable int x) {
        return ds.aulasPorEstado(x);
    }

    @GetMapping("/aulas-estado")
    public List<ContadorDto> daulasPorEstado() {
        return ds.aulasPorEstado();
    }

    @GetMapping("/health/payment-service")
    public ResponseEntity<Boolean> checkPaymentService() {
        boolean isAvailable = ds.isPaymentServiceAvailable();
        return ResponseEntity.ok(isAvailable);
    }
}
