package com.restaurante.dcarrasco.modelo;

public class Bandeja extends OpcionPedido {

    public Bandeja(Integer precio) {
        super(precio);
        //TODO Auto-generated constructor stub
    }

    public Bandeja(Integer precio, Principio principio, Carne carne, Ensalada ensalada, Jugo jugo) {
        super(precio, principio, carne, ensalada, jugo);
    }

    public Bandeja(Integer precio, Principio principio, Carne carne, Jugo jugo) {
        super(precio, principio, carne, jugo);
    }

    @Override
    public String toString() {
        return "Bandeja [precio= " + getPrecio()
        + ", principio= " + getPrincipio() + ", carne= " + getCarne() + 
         ", ensalada= " + getEnsalada() + ", jugo= " + getJugo() + "]";
    }
    
}
