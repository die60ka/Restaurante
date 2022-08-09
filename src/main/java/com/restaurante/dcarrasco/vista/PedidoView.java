package com.restaurante.dcarrasco.vista;

import java.sql.SQLException;
import java.util.Scanner;

import com.restaurante.dcarrasco.controlador.RestauranteController;
import com.restaurante.dcarrasco.modelo.Bandeja;
import com.restaurante.dcarrasco.modelo.Carne;
import com.restaurante.dcarrasco.modelo.Completo;
import com.restaurante.dcarrasco.modelo.Ensalada;
import com.restaurante.dcarrasco.modelo.Jugo;
import com.restaurante.dcarrasco.modelo.OpcionPedido;
import com.restaurante.dcarrasco.modelo.Pedido;
import com.restaurante.dcarrasco.modelo.Principio;
import com.restaurante.dcarrasco.modelo.Sopa;

public class PedidoView {

    private Scanner entrada;
    private RestauranteController controller;

    public PedidoView(Scanner entrada, RestauranteController controller) {
        this.entrada = entrada;
        this.controller = controller;
    }

    public Pedido cargarPedido() throws SQLException {
        System.out.print("Ingrese el nombre (descripcion) del cliente: ");
        var cliente = entrada.nextLine();

        // Elegir entre diferentes opciones
        OpcionPedido opcion = null;
        do {
            System.out.print("Cual es su eleccion de almuerzo? C -> Completo, B -> Bandeja\n(C/B): ");
            var valorOpcion = entrada.nextLine();
            switch (valorOpcion.toUpperCase()) {
                case "C":
                    opcion = new Completo(12_000);
                    ((Completo)opcion).setSopa(elegirOpcionSopa());
                    opcion.setPrincipio(elegirOpcionPrincipio());
                    opcion.setCarne(elegirOpcionCarne());
                    opcion.setEnsalada(elegirOpcionEnsalada());
                    opcion.setJugo(elegirOpcionJugo());
                    break;
                case "B":
                    opcion = new Bandeja(10_000);
                    opcion.setPrincipio(elegirOpcionPrincipio());
                    opcion.setCarne(elegirOpcionCarne());
                    opcion.setEnsalada(elegirOpcionEnsalada());
                    opcion.setJugo(elegirOpcionJugo());
                    break;
                default:
                    System.err.println("Ha ingresado una opción no valida");
                    System.err.println("Vuelva a intentar");
                    break;
            }
        } while (opcion == null);

        return new Pedido(cliente, opcion);
    }

    private Sopa elegirOpcionSopa() throws SQLException {
        Sopa respuesta = null;
        var opciones = controller.listarSopas();
        do {
            System.out.println(".: OPCIONES DE SOPAS :.");
            for (int i = 0; i <  opciones.size(); i++) {
                System.out.printf("%d -> %s %n",
                i, opciones.get(i).getNombre());
            }
            System.out.print("¿Cúal es su elección?: ");
            var opcion = entrada.nextInt();
            entrada.nextLine();
            if (opcion >= 0 && opcion < opciones.size()) {
                respuesta = opciones.get(opcion);
            } else {
                System.err.println("Ha ingresado una opción no valida");
                System.err.println("Vuelva a intentar");
            }
        } while (respuesta == null);
        return respuesta;
    }

    /**
     * @return
     * @throws SQLException
     */
    private Principio elegirOpcionPrincipio() throws SQLException {
        Principio respuesta = null;
        var opciones = controller.listarPrincipios();
        do {
            System.out.println(".: OPCIONES DE PRINCIPIO :.");
            for (int i = 0; i <  opciones.size(); i++) {
                System.out.printf("%d -> %s %n",
                i, opciones.get(i).getNombre());
            }
            System.out.print("¿Cúal es su elección?: ");
            var opcion = entrada.nextInt();
            entrada.nextLine();
            if (opcion >= 0 && opcion < opciones.size()) {
                respuesta = opciones.get(opcion);
            } else {
                System.err.println("Ha ingresado una opción no valida");
                System.err.println("Vuelva a intentar");
            }
        } while (respuesta == null);
        return respuesta;
    }

    private Carne elegirOpcionCarne() throws SQLException {
        Carne respuesta = null;
        var opciones = controller.listarCarnes();
        do {
            System.out.println(".: OPCIONES DE CARNE :.");
            for (int i = 0; i <  opciones.size(); i++) {
                System.out.printf("%d -> %s %n",
                i, opciones.get(i).getNombre());
            }
            System.out.print("¿Cúal es su elección?: ");
            var opcion = entrada.nextInt();
            entrada.nextLine();
            if (opcion >= 0 && opcion < opciones.size()) {
                respuesta = opciones.get(opcion);
            } else {
                System.err.println("Ha ingresado una opción no valida");
                System.err.println("Vuelva a intentar");
            }
        } while (respuesta == null);
        return respuesta;
    }

    private Ensalada elegirOpcionEnsalada() throws SQLException {
        Ensalada respuesta = null;
        var opciones = controller.listarEnsaladas();
        do {
            System.out.println(".: OPCIONES DE Ensalada :.");
            System.out.printf("%d -> %s %n", -1, "Sin ensalada");
            for (int i = 0; i <  opciones.size(); i++) {
                System.out.printf("%d -> %s %n",
                i, opciones.get(i).getNombre());
            }
            System.out.print("¿Cúal es su elección?: ");
            var opcion = entrada.nextInt();
            entrada.nextLine();
            if (opcion == -1) {
                break;
            }
            else if (opcion >= 0 && opcion < opciones.size()) {
                respuesta = opciones.get(opcion);
            } else {
                System.err.println("Ha ingresado una opción no valida");
                System.err.println("Vuelva a intentar");
            }
        } while (respuesta == null);
        return respuesta;
    }

    private Jugo elegirOpcionJugo() throws SQLException {
        Jugo respuesta = null;
        var opciones = controller.listarJugos();
        do {
            System.out.println(".: OPCIONES DE JUGO :.");
            for (int i = 0; i <  opciones.size(); i++) {
                System.out.printf("%d -> %s %n",
                i, opciones.get(i).getNombre());
            }
            System.out.print("¿Cúal es su elección?: ");
            var opcion = entrada.nextInt();
            entrada.nextLine();
            if (opcion >= 0 && opcion < opciones.size()) {
                respuesta = opciones.get(opcion);
            } else {
                System.err.println("Ha ingresado una opción no valida");
                System.err.println("Vuelva a intentar");
            }
        } while (respuesta == null);
        return respuesta;
    }
}
