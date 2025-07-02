package com.school.payment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.school.payment.dto.ContadorDto2;
import com.school.payment.models.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

        // ✅ TUS QUERIES ORIGINALES ADAPTADAS A MICROSERVICIOS

        // ❌ ANTES: @Query("SELECT p FROM Pago p WHERE p.alumno.id = ?1 ORDER BY
        // p.fecha_vencimiento")
        // ✅ DESPUÉS: Usar alumnoId en lugar de alumno.id
        @Query("SELECT p FROM Pago p WHERE p.alumnoId = ?1 ORDER BY p.fecha_vencimiento")
        List<Pago> findAllByIdAlumno(Long id);

        // ❌ ANTES: @Query("SELECT p FROM Pago p WHERE p.alumno = ?1")
        // ✅ DESPUÉS: Cambiar por alumnoId (ya no puedes pasar entidad Alumno)
        @Query("SELECT p FROM Pago p WHERE p.alumnoId = ?1")
        List<Pago> findAllByAlumnoId(Long alumnoId);

        // ❌ ANTES: @Query("SELECT p FROM Pago p WHERE p.alumno.id = ?1 AND EXTRACT(YEAR
        // FROM p.fecha_vencimiento) = ?2")
        // ✅ DESPUÉS: Usar alumnoId
        @Query("SELECT p FROM Pago p WHERE p.alumnoId = ?1 AND EXTRACT(YEAR FROM p.fecha_vencimiento) = ?2")
        List<Pago> findAllByIdAlumnoAndYear(Long id, Integer year);

        // ✅ TUS QUERIES DE RECAUDACIÓN SIN CAMBIOS (no usan relaciones)
        @Query("SELECT NEW com.school.payment.dto.ContadorDto2(MONTH(p.fecha_pago), SUM(p.monto)) FROM Pago p "
                        + "WHERE YEAR(p.fecha_pago) = YEAR(CURRENT_DATE) "
                        + "GROUP BY MONTH(p.fecha_pago)")
        List<ContadorDto2> obtenerRecaudacionMensual();

        @Query("SELECT NEW com.school.payment.dto.ContadorDto2(MONTH(p.fecha_pago), SUM(p.monto)) FROM Pago p "
                        + "WHERE EXTRACT(YEAR FROM p.fecha_pago) = ?1 "
                        + "GROUP BY MONTH(p.fecha_pago)")
        List<ContadorDto2> obtenerRecaudacionMensual(int year);

        // ✅ QUERIES ADICIONALES PARA MICROSERVICIOS (Mis propuestas + las tuyas)

        // Para compatibilidad con el nombre que ya usas
        @Query("SELECT p FROM Pago p WHERE p.alumnoId = :alumnoId")
        List<Pago> findByAlumnoId(@Param("alumnoId") Long alumnoId);

        @Query("SELECT p FROM Pago p WHERE p.alumnoId = :alumnoId AND p.estado = :estado")
        List<Pago> findByAlumnoIdAndEstado(@Param("alumnoId") Long alumnoId, @Param("estado") int estado);

        @Query("SELECT p FROM Pago p WHERE p.alumnoId = :alumnoId AND p.estado = 0")
        List<Pago> findPagosVencidosByAlumno(@Param("alumnoId") Long alumnoId);

        @Query("SELECT p FROM Pago p WHERE p.alumnoId = :alumnoId AND p.estado = 1")
        List<Pago> findPagosPagadosByAlumno(@Param("alumnoId") Long alumnoId);

        // ✅ QUERIES PARA ESTADÍSTICAS
        @Query("SELECT COUNT(p) FROM Pago p WHERE p.alumnoId = :alumnoId AND p.estado = 0")
        int countPagosPendientesByAlumno(@Param("alumnoId") Long alumnoId);

        @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.alumnoId = :alumnoId AND p.estado = 1")
        Double sumMontosPagadosByAlumno(@Param("alumnoId") Long alumnoId);

        @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.alumnoId = :alumnoId AND p.estado = 0")
        Double sumMontosPendientesByAlumno(@Param("alumnoId") Long alumnoId);

        // ✅ QUERIES GENERALES (combinando las mías con tu estilo)
        @Query("SELECT p FROM Pago p WHERE p.estado = :estado")
        List<Pago> findByEstado(@Param("estado") int estado);

        @Query("SELECT p FROM Pago p WHERE p.fecha_vencimiento < CURRENT_DATE AND p.estado = 0")
        List<Pago> findPagosVencidos();

        // ✅ VERIFICAR EXISTENCIA
        @Query("SELECT COUNT(p) > 0 FROM Pago p WHERE p.alumnoId = :alumnoId")
        boolean existsByAlumnoId(@Param("alumnoId") Long alumnoId);

        // ✅ QUERIES PARA REPORTES (estilo tuyo + funcionalidad microservicios)
        @Query("SELECT COUNT(DISTINCT p.alumnoId) FROM Pago p WHERE p.estado = 0")
        long countAlumnosConDeuda();

        @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.estado = 1 AND MONTH(p.fecha_pago) = MONTH(CURRENT_DATE)")
        Double getTotalRecaudadoMesActual();

        @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.estado = 0")
        Double getTotalPendiente();

        // ✅ QUERIES ADICIONALES CON TU ESTILO DE EXTRACT
        @Query("SELECT p FROM Pago p WHERE p.alumnoId = :alumnoId AND EXTRACT(MONTH FROM p.fecha_vencimiento) = :mes")
        List<Pago> findByAlumnoIdAndMes(@Param("alumnoId") Long alumnoId, @Param("mes") int mes);

        @Query("SELECT p FROM Pago p WHERE p.alumnoId = :alumnoId AND p.fecha_vencimiento BETWEEN :fechaInicio AND :fechaFin")
        List<Pago> findByAlumnoIdAndFechaRange(@Param("alumnoId") Long alumnoId,
                        @Param("fechaInicio") java.util.Date fechaInicio,
                        @Param("fechaFin") java.util.Date fechaFin);

        // ✅ RECAUDACIÓN POR ALUMNO (nuevo, estilo tuyo)
        @Query("SELECT NEW com.school.payment.dto.ContadorDto2(p.alumnoId, SUM(p.monto)) FROM Pago p "
                        + "WHERE p.estado = 1 AND YEAR(p.fecha_pago) = YEAR(CURRENT_DATE) "
                        + "GROUP BY p.alumnoId")
        List<ContadorDto2> obtenerRecaudacionPorAlumno();

        // ✅ PAGOS VENCIDOS POR MES (nuevo, estilo tuyo)
        @Query("SELECT NEW com.school.payment.dto.ContadorDto2(MONTH(p.fecha_vencimiento), COUNT(p)) FROM Pago p "
                        + "WHERE p.estado = 0 AND p.fecha_vencimiento < CURRENT_DATE "
                        + "GROUP BY MONTH(p.fecha_vencimiento)")
        List<ContadorDto2> obtenerPagosVencidosPorMes();

        @Query("SELECT p FROM Pago p WHERE p.alumnoId = :alumnoId AND p.aulaId = :aulaId")
        List<Pago> findByAlumnoIdAndAulaId(@Param("alumnoId") Long alumnoId, @Param("aulaId") Long aulaId);
}