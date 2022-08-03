package com.restaurante.dcarrasco.modelo;

public class Principio {
    private String nombre;

    public Principio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
