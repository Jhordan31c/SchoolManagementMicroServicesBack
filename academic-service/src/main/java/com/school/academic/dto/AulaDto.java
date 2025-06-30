package com.school.academic.dto;

import java.util.List;

public class AulaDto {
    private Long id_docente;
    private Long id_grado;
    private char sub_grado;
    private double pago_matricula;
    private double pago_mensual;
    private int dia_vencimiento;
    private List<Long> alumnos;
    private List<HorarioDto> horarios;
    
    
    public AulaDto() {
    }

    public AulaDto(Long id_docente, Long id_grado, char sub_grado, double pago_matricula, double pago_mensual,
            int dia_vencimiento, List<Long> alumnos, List<HorarioDto> horarios) {
        this.id_docente = id_docente;
        this.id_grado = id_grado;
        this.sub_grado = sub_grado;
        this.pago_matricula = pago_matricula;
        this.pago_mensual = pago_mensual;
        this.dia_vencimiento = dia_vencimiento;
        this.alumnos = alumnos;
        this.horarios = horarios;
    }



    @Override
    public String toString() {
        return "AulaDto [id_docente=" + id_docente + ", id_grado=" + id_grado + ", sub_grado=" + sub_grado
                + ", pago_matricula=" + pago_matricula + ", pago_mensual=" + pago_mensual + ", dia_vencimiento="
                + dia_vencimiento + ", alumnos=" + alumnos + ", horarios=" + horarios + "]";
    }


    public Long getId_docente() {
        return id_docente;
    }

    public void setId_docente(Long id_docente) {
        this.id_docente = id_docente;
    }

    public Long getId_grado() {
        return id_grado;
    }

    public void setId_grado(Long id_grado) {
        this.id_grado = id_grado;
    }

    public char getSub_grado() {
        return sub_grado;
    }

    public void setSub_grado(char sub_grado) {
        this.sub_grado = sub_grado;
    }

    public List<Long> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Long> alumnos) {
        this.alumnos = alumnos;
    }

    public List<HorarioDto> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioDto> horarios) {
        this.horarios = horarios;
    }

    public double getPago_matricula() {
        return pago_matricula;
    }

    public void setPago_matricula(double pago_matricula) {
        this.pago_matricula = pago_matricula;
    }

    public double getPago_mensual() {
        return pago_mensual;
    }

    public void setPago_mensual(double pago_mensual) {
        this.pago_mensual = pago_mensual;
    }

    public int getDia_vencimiento() {
        return dia_vencimiento;
    }

    public void setDia_vencimiento(int dia_vencimiento) {
        this.dia_vencimiento = dia_vencimiento;
    }

    
}
