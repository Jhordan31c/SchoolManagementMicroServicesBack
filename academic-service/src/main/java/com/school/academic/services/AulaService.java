package com.school.academic.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.academic.dto.AulaDto;
import com.school.academic.models.Alumno;
import com.school.academic.models.AlumnoMateria;
import com.school.academic.models.Aula;
import com.school.academic.models.Bimestre;
import com.school.academic.models.Docente;
import com.school.academic.models.Grado;
import com.school.academic.models.Horario;
import com.school.academic.models.Materia;
/*import com.school.academic.models.Pago;
import com.school.academic.models.ParametroPaga;*/
import com.school.academic.repositories.AlumnoMateriaRepository;
import com.school.academic.repositories.AlumnoRepository;
import com.school.academic.repositories.AulaRepository;
import com.school.academic.repositories.DocenteRepository;
import com.school.academic.repositories.GradoRepository;
import com.school.academic.repositories.HorarioRepository;
import com.school.academic.repositories.MateriaRepository;
//import com.school.academic.repositories.ParametroPagaRepository;

@Service
public class AulaService {
    
    
    @Autowired
    private AulaRepository ar;

    @Autowired
    private DocenteRepository dr;

    @Autowired
    private GradoRepository gr;

    @Autowired
    private AlumnoRepository alr;

    @Autowired
    private MateriaRepository mr;

    @Autowired
    private HorarioRepository hr;

    @Autowired
    private ParametroPagaRepository ppr;


    //LISTA DE AULAS
    @Transactional(readOnly = true)
    public List<Aula> findAll() {
        return (List<Aula>) ar.findAllBasico();
    }

    @Transactional(readOnly = true)
    public List<Aula> findAllEstado(int estado) {
        return ar.findAllBasicoByEstado(estado);
    }




    //BUSQUEDA DE UN AULA
    @Transactional(readOnly = true)
    public Optional<Aula> find(Long id){

        Optional<Aula> oa = ar.findBase(id);

        if (oa.isPresent()) {
            Aula x = oa.orElseThrow();
            x.setHorarios(hr.findAllByAula(id));
            x.setAlumnos(alr.findAllByAula(id));
            return Optional.of(x);
        }

        return oa;
    }



    @Transactional
    public Optional<Aula> updateEstado(Long id, int estado){
        Optional<Aula> op = ar.findById(id);
        
        if (op.isPresent()) {
            Aula x = op.orElseThrow();
            x.setEstado(estado);


            if (estado == 0) {//EL AULA FUE UN ERROR, POR LO QUE ES ELIMINADA
                x.getAlumnos().forEach(a->{
                    List<AlumnoMateria> materias = amr.findByAlumnoAula(a.getId(), x.getId());
                    materias.forEach(m->{
                        a.removeMateria(m);
                    });
                    a.getPagos().clear();
                });
                x.getAlumnos() .clear();
                x.getHorarios().clear();
                ar.delete(x);
                return op;
            }

            if (estado == 2) {//EL AULA CUMPLIO SU TIEMPO
                x.getAlumnos().forEach(a->{
                    a.getPagos().clear();
                });
                x.getAlumnos() .clear();
                x.getHorarios().clear();
                return op;
            }

            return find(x.getId());
        }
        
        return op;
    }

    /*
     * REGISTRO DEL AULA A PARTIR DE UN DTO
     */
    @Transactional
    public Aula create(AulaDto a){
        Aula x = new Aula();
        
        //LLENANDO AULA BASE
        Optional<Docente> od = dr.findById(a.getId_docente());
        if (od.isPresent()) {
            x.setTutor(od.orElseThrow());
        }

        Optional<Grado> og = gr.findById(a.getId_grado());
        if (og.isPresent()) {
            x.setGrado(og.orElseThrow());
        }
        
        x.setSub_grado(a.getSub_grado());
        x.setEstado(1);
        x.setFecha_registro(new Date());

        
        //REGISTRO DE AULA BASE
        Aula x2 = ar.save(x);
        
        //ASIGNANDO ALUMNOS
        asignarAlumnos(a, x2);
        //ASIGNANDO HORARIOS
        if (a.getHorarios()!=null) {
            asignarHorarios(a, x2);
        }
        //ASIGNAR PAGOS
        asignarPagos(a, x2);
        //ASIGNAR RENDIMIENTO ACADEMICO
        asignarRendimientoAcademico(x2);

        return find(ar.save(x2).getId()).orElseThrow();
    }

    /*
     * 
    */
    public void asignarAlumnos(AulaDto a, Aula x){
        a.getAlumnos().forEach(id->{
            Optional<Alumno> oal = alr.findById(id);
            oal.ifPresent( alumno-> x.addAlumno(alumno));  
        });
    }


    //ASIGNAR HORARIO-------------------------------------------------------------------------------------------------
    /*
     * Se asignaran todos los horarios del aulaDto al Aula
     * Se validaran los datos de cada horario antes de agregar a la lista
     */
    @Transactional
    public void asignarHorarios(AulaDto a, Aula x){
        a.getHorarios().forEach(h->{
            Horario h2 = new Horario();
            
            
            Optional<Docente> od = dr.findById(h.getId_docente());
            if (od.isPresent()) {
                h2.setDocente(od.orElseThrow());
            }
            Optional<Materia> om = mr.findById(h.getId_materia());
            if (om.isPresent()) {
                h2.setMateria(om.orElseThrow());
            }
            h2.setDia   (h.getDia());
            h2.setInicio(h.getH_inicio());
            h2.setFin   (h.getH_fin());
            
            
            x.addHorario(h2);
        });
    }




    /*
     *Se asignaran los pagos pendientes del alumno 
     */
    @Transactional
    public void asignarPagos(AulaDto a, Aula x){
        String nivelTxt = x.getGrado().getNivel();
        int nivel = 1;

        if (nivelTxt.equalsIgnoreCase("inicial")) {
            nivel = 1;
        }else if (nivelTxt.equalsIgnoreCase("primaria")) {
            nivel = 2;
        }else if (nivelTxt.equalsIgnoreCase("secundaria")) {
            nivel = 3;
        }

        ParametroPaga pp = ppr.findByNivel(nivel).orElseThrow();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month= 2;
        //int day  = a.getDia_vencimiento();
        int day  = pp.getDia_vencimiento();

        x.getAlumnos().forEach(k->{
            try {
                k.addPago(new Pago("Matricula"  , pp.getPrecioMatricula(), sdf.parse(year+"-"+month+"-"+day)  ,0));

                k.addPago(new Pago("Marzo"      , pp.getPrecioPension(), sdf.parse(year+"-04-"+day)         ,0));
                k.addPago(new Pago("Abril"      , pp.getPrecioPension(), sdf.parse(year+"-05-"+day)         ,0));
                k.addPago(new Pago("Mayo"       , pp.getPrecioPension(), sdf.parse(year+"-06-"+day)         ,0));
                k.addPago(new Pago("Junio"      , pp.getPrecioPension(), sdf.parse(year+"-07-"+day)         ,0));
                k.addPago(new Pago("Julio"      , pp.getPrecioPension(), sdf.parse(year+"-08-"+day)         ,0));
                k.addPago(new Pago("Agosto"     , pp.getPrecioPension(), sdf.parse(year+"-09-"+day)         ,0));
                k.addPago(new Pago("Setiembre"  , pp.getPrecioPension(), sdf.parse(year+"-10-"+day)         ,0));
                k.addPago(new Pago("Octubre"    , pp.getPrecioPension(), sdf.parse(year+"-11-"+day)         ,0));
                k.addPago(new Pago("Noviembre"  , pp.getPrecioPension(), sdf.parse(year+"-12-"+day)         ,0));
                k.addPago(new Pago("Diciembre"  , pp.getPrecioPension(), sdf.parse((year+1)+"-01-"+day)     ,0));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }




    /*
     *Se asignaran las materias que participan en el horario del aula en el rendimiento del Alumno
     */
    @Transactional
    public void asignarRendimientoAcademico(Aula x){
        
        Set<Horario> horarios = hr.findDistinctMateriasByAula(x.getId());

        x.getAlumnos().forEach(a->{
            horarios.forEach(m->{
                AlumnoMateria am = new AlumnoMateria();
                
                am.setGrado(x.getGrado());
                am.setMateria(m.getMateria());
                am.setDocente(m.getDocente());
                am.setAula(x);
                am.addBimestre(new Bimestre(1,0,0,0,0));
                am.addBimestre(new Bimestre(2,0,0,0,0));
                am.addBimestre(new Bimestre(3,0,0,0,0));
                am.addBimestre(new Bimestre(4,0,0,0,0));
                a.addMateria(am);
            });
        });
    }


    @Autowired
    AlumnoMateriaRepository amr;



    //COMPROBAR SI UN HORARIO CHOCA CON OTROS REGISTRADOS EN EL AULA}
    /*
     * Confirma si existe algun choque entre el horario actual y los ya registrados
     */
    private boolean haySolapamiento(Long aulaId, Horario h)
    {
        List<Horario> horarios = hr.findAllByAulaAndDia(aulaId, h.getDia());
        
        for (Horario x : horarios) {
            if(hayChoque(x, h)){
                return true;
            }    
        }
        return false;
    }

    private boolean hayChoque(Horario h1, Horario h2){
        return h1.getInicio().isBefore(h2.getFin()) 
            && h1.getFin().isAfter(h2.getInicio());
    }

}
