package com.jml.cloud.control.notas;

public class Nota {

    private int id;
    private String identificacion;
    private String materia;
    private double nota;
    private int corte;

    public Nota(int id, String identificacion, String materia, double nota, int corte) {
        this.id = id;
        this.identificacion = identificacion;
        this.materia = materia;
        this.nota = nota;
        this.corte = corte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getCorte() {
        return corte;
    }

    public void setCorte(int corte) {
        this.corte = corte;
    }
}