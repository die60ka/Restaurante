package com.restaurante.dcarrasco;

import java.util.Scanner;

import com.restaurante.dcarrasco.controlador.RestauranteController;
import com.restaurante.dcarrasco.exception.ObjetoNoExistenteEsception;
import com.restaurante.dcarrasco.exception.PagoinsuficienteException;

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

            controlador.iniciarAplicacion();


        }catch (Exception ex) {
            System.err.println("Ocurrio un error y salgo de la aplicación: \n" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void prueba2(RestauranteController controlador)
            throws ObjetoNoExistenteEsception, PagoinsuficienteException {
        var mesa = controlador.consultarMesa("01");
        System.out.println("La mesa consultada es: " + mesa.getNumero());

        controlador.agregarPedido(mesa);
        System.out.println("El total de la mesa es:  " + mesa.calcularTotal());


        var devuelta = controlador.pagarCuenta(mesa);
        System.out.printf("Los clientes de la mesa %s pagaron la cuenta y se devolvió $ %,d %n",
        mesa, devuelta);
    }
}