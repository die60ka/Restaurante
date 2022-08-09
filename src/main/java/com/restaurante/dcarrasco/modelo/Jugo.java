package com.restaurante.dcarrasco.modelo;

public class Jugo {
    // Para manejo de base de datos
    private Integer id;

    private String nombre;

    public Jugo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
