package com.school.academic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.academic.models.Cita;
import com.school.academic.repositories.CitaRepository;
/* 
 * pendiente : 1, realizada : 2, cancelada :3
*/
@Service
public class CitaService {
    
    @Autowired
    CitaRepository cr;

    //LISTA (todos los datos de la cita, pero con la info basica de Alumno y Docente)
    @Transactional(readOnly = true)
    public List<Cita> findAll(){
        return (List<Cita>) cr.findAllBasico();
    } 

    //La lista de arriba pero filtrada por el estado
    @Transactional(readOnly = true)
    public List<Cita> findAllEstado(int estado){
        return (List<Cita>) cr.findAllBasicoByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Cita> findAllEstadoAlumno(int estado, Long id){
        return cr.findAllBasicoByEstadoByAlumno(estado, id);
    }

    @Transactional(readOnly = true)
    public List<Cita> findAllEstadoDocente(int estado, Long id){
        return cr.findAllBasicoByEstadoByDocente(estado, id);
    }

    
    @Transactional
    public Optional<Cita> findById(Long id){
        return cr.findByIdBasico(id);
    }

    


    //CRUD
    @Transactional
    public Optional<Cita> create(Cita c){
        c.setId(null);
        c.setEstado(1);
        Cita x = cr.save(c);
        return Optional.ofNullable(x);
    }

    @Transactional
    public Optional<Cita> update(Cita c, Long id){
        Optional<Cita> oc = cr.findById(id);

        if (oc.isPresent()) {
            Cita x = oc.orElseThrow();

            if (c.getDocente()  !=null) {
                x.setDocente(c.getDocente());
            }

            if (c.getAlumno()   !=null){
                x.setAlumno(c.getAlumno());
            }
            if (c.getFecha()    !=null) {
                x.setFecha  (c.getFecha()   );
            }
            if (c.getInicio()   !=null) {
                x.setInicio (c.getInicio()  );
            }
            if (c.getTitulo()   !=null) {
                x.setTitulo (c.getTitulo()  );
            }
            if (c.getDetalle()  !=null){
                x.setDetalle(c.getDetalle() );
            }
            if (c.getMensaje()  != null) {
                x.setMensaje(c.getMensaje());
            }

            x.setEstado (c.getEstado()  );

            Cita x2 = cr.save(x);
            return Optional.of(x2);
        }
        return oc;
    }


    @Transactional
    public Optional<Cita> delete(Long id){
        Optional<Cita> oc = cr.findById(id);
        oc.ifPresent( x -> cr.delete(x));
        return oc;
    }


    @Transactional
    public Optional<Cita> updateEstado(Long id, int estado){
        Optional<Cita> oc = cr.findById(id);

        if (oc.isPresent()) {
            Cita x = oc.orElseThrow();
            x.setEstado(estado);
            return  Optional.of(cr.save(x));
        }

        return oc;
    }
}
