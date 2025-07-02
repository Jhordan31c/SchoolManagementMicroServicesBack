package com.school.academic.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.school.academic.dto.ParametroPagaDto;
import com.school.academic.dto.ContadorDto2;
import com.school.academic.dto.PagoRequestDto;

@FeignClient(name = "payment-service", url = "${services.payment.url}")
public interface PaymentClient {
    
    // ✅ OBTENER PARÁMETROS DE PAGO POR NIVEL
    @GetMapping("/parametros/nivel/{nivel}")
    ParametroPagaDto getParametrosPorNivel(@PathVariable("nivel") int nivel);
    
    // ✅ CREAR PAGOS MASIVOS PARA UN AULA
    @PostMapping("/pagos/masivos")
    void crearPagosMasivos(@RequestBody List<PagoRequestDto> pagos);
    
    // ✅ ELIMINAR PAGOS POR ALUMNO Y AULA
    @DeleteMapping("/pagos/alumno/{alumnoId}/aula/{aulaId}")
    void eliminarPagosPorAlumnoYAula(@PathVariable("alumnoId") Long alumnoId, 
                                   @PathVariable("aulaId") Long aulaId);
    
    // ✅ FINALIZAR PAGOS POR ALUMNO Y AULA
    @PutMapping("/pagos/alumno/{alumnoId}/aula/{aulaId}/finalizar")
    void finalizarPagosPorAlumnoYAula(@PathVariable("alumnoId") Long alumnoId, 
                                    @PathVariable("aulaId") Long aulaId);

    // ✅ NUEVOS MÉTODOS PARA DataService
    @GetMapping("/api/pagos/estadisticas/recaudacion-mensual")
    List<ContadorDto2> obtenerRecaudacionMensual();
    
    @GetMapping("/api/pagos/estadisticas/recaudacion-mensual/{year}")
    List<ContadorDto2> obtenerRecaudacionMensual(@PathVariable("year") int year);
    
}