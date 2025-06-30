package com.school.payment.controllers;

import java.util.List;

import com.school.payment.clients.AlumnoClient;
import com.school.payment.dto.AlumnoDto;
import com.school.payment.dto.PagoConAlumnoDto;
import com.school.payment.dto.PagoDto;
import com.school.payment.models.Pago;
import com.school.payment.models.ParametroPaga;
import com.school.payment.services.PagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos")
@CrossOrigin(origins = "*")
public class PagoController {
   @Autowired
    private PagoService pagoService;

    @Autowired
    private AlumnoClient alumnoClient; // ✅ Reemplaza AlumnoService

    // =============================================
    // ENDPOINTS BÁSICOS DE PAGOS
    // =============================================

    @GetMapping
    public ResponseEntity<List<Pago>> listaPagos() {
        List<Pago> pagos = pagoService.findAll();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        Optional<Pago> pago = pagoService.findById(id);
        return pago.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pago> registrar(@RequestBody Pago pago) {
        // ✅ Validar que el alumno existe antes de crear el pago
        try {
            if (pago.getAlumnoId() != null) {
                AlumnoDto alumno = alumnoClient.findById(pago.getAlumnoId());
                if (alumno == null) {
                    return ResponseEntity.badRequest().build();
                }
            }
        } catch (Exception e) {
            // Si academic-service no responde, log del error pero continúa
            System.err.println("Error al validar alumno: " + e.getMessage());
        }
        
        Pago nuevoPago = pagoService.create(pago);
        return ResponseEntity.ok(nuevoPago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(@PathVariable Long id, @RequestBody Pago pago) {
        Optional<Pago> pagoActualizado = pagoService.update(pago, id);
        return pagoActualizado.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pago> eliminar(@PathVariable Long id) {
        Optional<Pago> pagoEliminado = pagoService.delete(id);
        return pagoEliminado.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }

    // =============================================
    // 👨‍🎓 ENDPOINTS POR ALUMNO
    // =============================================

    // ❌ ELIMINADO: /alumnos - esto va en academic-service
    // ✅ NUEVO: Endpoint que combina datos de ambos servicios
    @GetMapping("/alumnos/{id}")
    public ResponseEntity<List<PagoConAlumnoDto>> listaPorAlumno(@PathVariable Long id) {
        try {
            List<PagoConAlumnoDto> pagosConAlumno = pagoService.findAllByIdAlumnoWithAlumnoData(id);
            return ResponseEntity.ok(pagosConAlumno);
        } catch (Exception e) {
            // Si hay error, devolver solo pagos sin datos del alumno
            List<Pago> pagos = pagoService.findAllByIdAlumno(id);
            // Convertir a DTO sin datos de alumno
            List<PagoConAlumnoDto> pagosDto = pagos.stream()
                .map(pago -> new PagoConAlumnoDto(pago, null))
                .collect(java.util.stream.Collectors.toList());
            return ResponseEntity.ok(pagosDto);
        }
    }

      @GetMapping("/alumnos/{id}/{año}")
    public ResponseEntity<List<Pago>> listaPorAlumnoYAño(@PathVariable Long id, @PathVariable Integer año) {
        List<Pago> pagos = pagoService.findAllByIdAlumnoByYear(id, año);
        return ResponseEntity.ok(pagos);
    }

    // ✅ NUEVO: Pagos pendientes por alumno
    @GetMapping("/alumnos/{id}/pendientes")
    public ResponseEntity<List<Pago>> pagosPendientesPorAlumno(@PathVariable Long id) {
        List<Pago> pagos = pagoService.findPagosVencidosByAlumno(id);
        return ResponseEntity.ok(pagos);
    }

    @PutMapping("/{id}/estado/{estado}")
    public ResponseEntity<Pago> actualizarEstado(@PathVariable Long id, @PathVariable int estado) {
        Optional<Pago> pagoActualizado = pagoService.updateEstado(id, estado);
        return pagoActualizado.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.badRequest().build());
    }


    // =============================================
    // ⚙️ ENDPOINTS DE PARÁMETROS
    // =============================================

    @GetMapping("/parametros")
    public ResponseEntity<List<ParametroPaga>> obtenerParametros() {
        List<ParametroPaga> parametros = pagoService.findParametroPagas();
        return ResponseEntity.ok(parametros);
    }

    @PutMapping("/parametros")
    public ResponseEntity<List<ParametroPaga>> actualizarParametros(@RequestBody List<ParametroPaga> lista) {
        List<ParametroPaga> parametrosActualizados = pagoService.updateParametrosPago(lista);
        return ResponseEntity.ok(parametrosActualizados);
    }

}