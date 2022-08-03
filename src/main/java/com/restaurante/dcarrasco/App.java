package com.restaurante.dcarrasco;

import java.util.Scanner;

import com.restaurante.dcarrasco.controlador.RestauranteController;
import com.restaurante.dcarrasco.exception.PagoinsuficienteException;
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
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        // pruebaVenta();

        try (var sc = new Scanner(System.in)) {
            
            var controlador = new RestauranteController(sc);

            controlador.iniciarBaseDatos();


            var mesa = controlador.consultarMesa("01");
            System.out.println("La mesa consultada es: " + mesa.getNumero());

            controlador.agregarPedido(mesa);
            System.out.println("El total de la mesa es:  " + mesa.calcularTotal());

        }catch (Exception ex) {
            System.err.println("Ocurrio un error y salgo de la aplicación: \n" + ex.getMessage());
        }
    }

    private static void pruebaVenta() {
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

        var efectivo = 20_000;
        try {
            var devuelta = mesa.pago(efectivo);
            System.out.printf("Paga la cuenta con %,d y recibe una devuelta de %,d",
                    efectivo, devuelta);
        } catch (PagoinsuficienteException ex) {
            System.out.println("Dinero insuficiente");
        }
    }
}