package com.restaurante.dcarrasco.modelo;

public class Sopa {
    private String nombre;

    // Para manejo de base de datos
    private Integer id;

    public Sopa(String nombre) {
        this.nombre = nombre;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
