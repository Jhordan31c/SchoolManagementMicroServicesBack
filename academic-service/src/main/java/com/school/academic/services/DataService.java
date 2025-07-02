package com.school.academic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.academic.clients.PaymentClient;
import com.school.academic.dto.ContadorDto;
import com.school.academic.dto.ContadorDto2;
import com.school.academic.repositories.AlumnoRepository;
import com.school.academic.repositories.AulaRepository;
import com.school.academic.repositories.CitaRepository;
import com.school.academic.repositories.DocenteRepository;
import com.school.academic.repositories.EventoRepository;
//import com.school.academic.repositories.PagoRepository;

@Service
public class DataService {

    @Autowired
    EventoRepository er;

    @Autowired
    PaymentClient paymentClient;

    @Autowired
    CitaRepository cr;

    @Autowired
    AlumnoRepository alr;

    @Autowired
    DocenteRepository dr;

    @Autowired
    AulaRepository ar;

    @Transactional(readOnly = true)
    public List<ContadorDto> eventosPorMes() {
        return er.contarEventosPorMes2();
    }

    @Transactional(readOnly = true)
    public List<ContadorDto> eventosPorMes(int year) {
        return er.contarEventosPorMes(year);
    }

    @Transactional(readOnly = true)
    public List<ContadorDto2> recaudacionPorMeses(){
        try {
            // ✅ USAR PAYMENT-SERVICE via client
            return paymentClient.obtenerRecaudacionMensual();
        } catch (Exception e) {
            System.err.println("Error obteniendo recaudación mensual: " + e.getMessage());
            // ✅ Retornar lista vacía si payment-service no responde
            return List.of();
        }
    }
    
    @Transactional(readOnly = true)
    public List<ContadorDto2> recaudacionPorMeses(int year){
        try {
            // ✅ USAR PAYMENT-SERVICE via client
            return paymentClient.obtenerRecaudacionMensual(year);
        } catch (Exception e) {
            System.err.println("Error obteniendo recaudación mensual para año " + year + ": " + e.getMessage());
            // ✅ Retornar lista vacía si payment-service no responde
            return List.of();
        }
    }

    public boolean isPaymentServiceAvailable() {
        try {
            paymentClient.obtenerRecaudacionMensual();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<ContadorDto> alumnosPorEstado() {
        return alr.contarAlumnosEstado();
    }

    @Transactional(readOnly = true)
    public long alumnosPorEstado(int estado) {
        return alr.countByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<ContadorDto> docentesPorEstado() {
        return dr.contarDocentesEstado();
    }

    @Transactional(readOnly = true)
    public long docentesPorEstado(int estado) {
        return dr.countByEstado(estado);
    }

    @Transactional(readOnly = true)
    public long citasPorEstado(int estado) {
        return cr.countByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<ContadorDto> aulasPorEstado() {
        return ar.contarAulasEstado();
    }

    @Transactional(readOnly = true)
    public long aulasPorEstado(int estado) {
        return ar.countByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<ContadorDto> citasPorMeses() {
        return cr.obtenerCantidadPorMes();
    }

}
