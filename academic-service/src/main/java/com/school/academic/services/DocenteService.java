package com.school.academic.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.academic.dto.AlumnoMateriaDto;
import com.school.academic.dto.AulaAsignadaDto;
import com.school.academic.dto.HorarioDocenteDto;
import com.school.academic.models.Alumno;
import com.school.academic.models.AlumnoMateria;
import com.school.academic.models.Docente;
import com.school.academic.models.Horario;
//import com.school.academic.models.User;
import com.school.academic.repositories.AlumnoMateriaRepository;
import com.school.academic.repositories.AlumnoRepository;
import com.school.academic.repositories.DocenteRepository;
import com.school.academic.repositories.HorarioRepository;

@Service
public class DocenteService {
    
    @Autowired
    DocenteRepository dr;
    
    @Autowired
    UserService us;

    @Autowired
    HorarioRepository hr;

    @Autowired
    AlumnoMateriaRepository amr;

    @Autowired
    AlumnoRepository alr;


    @Transactional(readOnly = true)
    public List<Docente> findAll(){
        return (List<Docente>) dr.findAll();
    }

    @Transactional(readOnly = true)
    public List<Docente> findAll(int estado){
        return (List<Docente>) dr.findAllBasico(estado);
    }

    @Transactional(readOnly = true)
    public List<Docente> findAllBasico(){
        return (List<Docente>) dr.findAllBasico();
    }


    @Transactional(readOnly = true)
    public Optional<Docente> findById(Long id){
        return dr.findById(id);
    }



    @Transactional
    public Docente create(Docente d){
        d.setId(null);
        d.setFecha_registro(new Date());
        d.setEstado(1);

        if (d.getUser()!=null) {
            User u = d.getUser();
            d.setUser(us.create(u,2));
        }
        
        return dr.save(d);
    }


    @Transactional
    public Optional<Docente> update(Long id, Docente d){
        Optional<Docente> od = dr.findById(id);

        if(od.isPresent()){ 
            Docente x = od.orElseThrow();
            
            if(d.getDni()       !=null){
                x.setDni        (d.getDni()     );
            }
            if(d.getNombre()    !=null) {
                x.setNombre     (d.getNombre()  );
            }
            if(d.getApellido()  !=null){
                x.setApellido   (d.getApellido());
            }
            if(d.getCorreo()    !=null) {
                x.setCorreo     (d.getCorreo()  );
            }
            
            x.setEstado     (d.getEstado()  );
            
            if(x.getUser() !=null   &&  d.getUser() !=null){
                us.update(x.getUser(), d.getUser());
            } 
            else if (x.getUser() ==null  && d.getUser() != null) {
                User u = d.getUser();
                x.setUser(us.create(u,2));
            }
            return Optional.of(dr.save(x));
        }
            
        return od;
    }


    @Transactional
    public Optional<Docente> delete(Long id){
        Optional<Docente> od = dr.findById(id);
        od.ifPresent(x->dr.delete(x));
        return od;
    }



    
    @Transactional
    public Optional<Docente> updateEstado(Long id, int estado){
        Optional<Docente> od = dr.findById(id);

        if (od.isPresent()) {
            Docente x = od.orElseThrow();
            x.setEstado(estado);
            return Optional.of(dr.save(x));
        }
        
        return od;
    }


    /*
    @Transactional(readOnly = true)
    public List<Horario> HorariosPorDocente(Long id, int year){
        return hr.findAllByDocente(id, year);
    }*/
    @Transactional(readOnly = true)
    public List<HorarioDocenteDto> HorariosPorDocente(Long id){
        List<HorarioDocenteDto> horariosDto = new ArrayList<>(); 

        List<Horario> horarios = hr.findAllByDocente(id);
        horarios.forEach(h->{
            HorarioDocenteDto dto = new HorarioDocenteDto();

            dto.setTutor    (h.getAula().getTutor().getNombre().concat(" ").concat(h.getAula().getTutor().getApellido()));
            dto.setNivel     (h.getAula().getGrado().getNivel());
            dto.setGrado     (h.getAula().getGrado().getSubNivel());
            dto.setSubGrado  (h.getAula().getSub_grado());
            dto.setMateria   (h.getMateria().getNombre());
            dto.setDia       (h.getDia());
            dto.setH_inicio  (h.getInicio());
            dto.setH_fin     (h.getFin());

            horariosDto.add(dto);
        });

        return horariosDto;
    }


    @Transactional(readOnly = true)
    public List<Alumno> getAlumnosCorrespondientes(Long id){
        List<Alumno> alumnos = new ArrayList<>();

        Set<Horario> horarios = hr.findDistinctMateriasByDocente(id);
        horarios.forEach(h->{
            h.getAula().getAlumnos().forEach(a->{
                Alumno x = alr.findBasicoById(a.getId()).orElseThrow();
                if (!contiene(alumnos, x)) {
                    alumnos.add(x);
                } 
            });
        });
        
        return alumnos;
    }

    private boolean contiene(List<Alumno> alumnos, Alumno x){
        for (Alumno a : alumnos) {
            if (a.getId() == x.getId()) {
                return true;   
            }
        }
        return false;
    }



    @Transactional(readOnly = true)
    public List<AulaAsignadaDto> getAulasAsignadas(Long id){
        List<AulaAsignadaDto> aulas = new ArrayList<>();

        Set<Horario> horarios = hr.findDistinctMateriasByDocente(id);
        horarios.forEach(h->{
            AulaAsignadaDto dto = new AulaAsignadaDto();
            
            dto.setId_aula      (h.getAula().getId());
            dto.setTutor        (h.getAula().getTutor().getNombre().concat(" ").concat(h.getAula().getTutor().getApellido()));
            dto.setNivel        (h.getAula().getGrado().getNivel());
            dto.setGrado        (h.getAula().getGrado().getSubNivel());
            dto.setSubGrado     (h.getAula().getSub_grado());
            dto.setAlumnos      (h.getAula().getAlumnos().size());
            dto.setMateria      (h.getMateria().getNombre());
            dto.setId_materia   (h.getMateria().getId());
            aulas.add(dto);
        });
        
        return aulas;
    }    


    /*
    public List<AlumnoMateria> getAlumnosMaterias(Long idDocente, Long idAula, Long idMateria){
        return amr.findByDocenteAulaMateria(idDocente, idAula, idMateria);
    } */

    @Transactional(readOnly = true)
    public List<AlumnoMateriaDto> getAlumnosMateria(Long idDocente, Long idAula, Long idMateria){
        List<AlumnoMateriaDto> lista = new ArrayList<>();
        List<AlumnoMateria> alumnos = amr.findByDocenteAulaMateria(idDocente, idAula, idMateria);
        alumnos.forEach(a->{
            AlumnoMateriaDto dto = new AlumnoMateriaDto();

            dto.setId       (a.getId());
            dto.setDni      (a.getAlumno().getDni());
            dto.setAlumno   (a.getAlumno().getApellido().concat(", ").concat(a.getAlumno().getNombre()));
            dto.setNivel    (a.getAula().getGrado().getNivel());
            dto.setGrado    (a.getAula().getGrado().getSubNivel());
            dto.setSubGrado (a.getAula().getSub_grado());
            dto.setMateria  (a.getMateria().getNombre());
            dto.setBimestres(a.getBimestres());
            
            lista.add(dto);
        });

        return lista;
    }

    
    @Transactional
    public void updateAlumnosMateria(List<AlumnoMateriaDto> lista){
        lista.forEach(x->{
            Optional<AlumnoMateria> oam = amr.findById(x.getId());
            oam.ifPresent(am->{
                am.getBimestres().clear();
                x.getBimestres().forEach(b->{
                    am.addBimestre(b);
                });

                amr.save(am);
            });
        });
    }

    @Transactional(readOnly = true)
    public Optional<Docente> findByUser(Long id){
        return dr.findByIdUser(id);
    }

    
}
