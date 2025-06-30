package com.school.academic.dto;


public class AlumnoMateriaDto2 {
    
    private Long id;
    private String materia;
    private String area;
    

    public AlumnoMateriaDto2(Long id, String materia, String area) {
        this.id = id;
        this.materia = materia;
        this.area = area;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMateria() {
        return materia;
    }
    public void setMateria(String materia) {
        this.materia = materia;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    
    
}
