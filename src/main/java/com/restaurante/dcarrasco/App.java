package com.restaurante.dcarrasco;

import com.restaurante.dcarrasco.modelo.Adicional;
import com.restaurante.dcarrasco.modelo.Bandeja;
import com.restaurante.dcarrasco.modelo.Carne;
import com.restaurante.dcarrasco.modelo.Completo;
import com.restaurante.dcarrasco.modelo.Ensalada;
import com.restaurante.dcarrasco.modelo.Jugo;
import com.restaurante.dcarrasco.modelo.Mesa;
import com.restaurante.dcarrasco.modelo.Pedido;
import com.restaurante.dcarrasco.modelo.Principio;
import com.restaurante.dcarrasco.modelo.Sopa;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        var mesa = new Mesa("01");
        var sopa = new Sopa("Pastas");
        var principio = new Principio("Frijoles");
        var carne = new Carne("Res a la plancha");
        var ensalada = new Ensalada("Tomate");
        var jugo = new Jugo("Guayaba");

        var precio = 12_000;
        var opcion = new Completo(precio, sopa, principio, carne, ensalada, jugo);

        var pedido = new Pedido("Diego Carrasco", opcion);
        pedido.agregarAdicional(new Adicional("Chicharron", 3_000));
        mesa.agregarPedido(pedido);

        precio = 10_000;
        var opcion2 = new Bandeja(precio, principio, carne, ensalada, jugo);
        pedido = new Pedido("Simon Carrasco", opcion2);
        mesa.agregarPedido(pedido);

        System.out.printf("Total de la mesa: $ %,d \n", mesa.calcularTotal());

    }
}
