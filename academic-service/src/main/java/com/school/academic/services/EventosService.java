package com.school.academic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.academic.models.Evento;
import com.school.academic.repositories.EventoRepository;

@Service
public class EventosService {

    @Autowired
    EventoRepository er;

    @Transactional(readOnly = true)
    public List<Evento> findAll(){
        return (List<Evento>) er.findAll();
    }

    @Transactional(readOnly = true)
    public List<Evento> findAllByYear(int year){
        return er.findAllByYear(year);
    }

    @Transactional(readOnly = true)
    public List<Evento> findAllByMonth(int year, int moth){
        return er.findAllByYear(year, moth);
    }

    @Transactional(readOnly = true)
    public Optional<Evento> findById(Long id){
        return er.findById(id);
    }


    @Transactional
    public Optional<Evento> create(Evento e){
        e.setId(null);
        e.setEstado(1);
        Evento x = er.save(e);
        return Optional.ofNullable(x);
    }


    @Transactional
    public Optional<Evento> update(Evento e, Long id){
        Optional<Evento> oe = er.findById(id);
        if (oe.isPresent()) {
            Evento x = oe.orElseThrow();

            if (e.getNombre()   !=null) {
                x.setNombre     (e.getNombre()      );
            }
            if (e.getDescripcion()!=null) {
                x.setDescripcion(e.getDescripcion());
            }
            if (e.getFecha()    !=null) {
                x.setFecha      (e.getFecha()       );
            }
            if (e.getInicio()   !=null) {
                x.setInicio     (e.getInicio()      );
            }
            if (e.getFin()      !=null) {
                x.setFin        (e.getFin()         );
            }
            x.setEstado     (e.getEstado()      );

            Evento x2 = er.save(x);                        
            return Optional.of(x2);
        }
        return oe;
    }


    @Transactional
    public Optional<Evento> delete(Long id){
        Optional<Evento> oe = er.findById(id);
        oe.ifPresent(x-> er.delete(x));
        return oe;
    }


    @Transactional
    public Optional<Evento> updateEstado(Long id, int estado){
        Optional<Evento> oe = er.findById(id);
        
        if (oe.isPresent()) {
            Evento x = oe.orElseThrow();
            x.setEstado(estado);
            return Optional.of(er.save(x));
        }

        return oe;
    }
}
