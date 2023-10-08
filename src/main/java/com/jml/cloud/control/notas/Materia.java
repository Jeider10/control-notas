package com.jml.cloud.control.notas;

public class Materia {

    int id;
    String nombre_materia;
    String codigo;

    public Materia(int id, String nombre_materia, String codigo) {
        this.id = id;
        this.nombre_materia = nombre_materia;
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_materia() {
        return nombre_materia;
    }

    public void setNombre_materia(String nombre_materia) {
        this.nombre_materia = nombre_materia;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}