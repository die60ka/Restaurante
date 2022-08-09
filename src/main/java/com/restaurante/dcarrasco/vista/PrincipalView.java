package com.restaurante.dcarrasco.vista;

import java.sql.SQLException;
import java.util.Scanner;

import com.restaurante.dcarrasco.controlador.RestauranteController;
import com.restaurante.dcarrasco.exception.ObjetoNoExistenteEsception;
import com.restaurante.dcarrasco.exception.PagoinsuficienteException;

public class PrincipalView {

    private Scanner entrada;
    private RestauranteController controller;

    public PrincipalView(Scanner entrada, RestauranteController controller) {
        this.entrada = entrada;
        this.controller = controller;
    }

    public void iniciarAplicacion() {
        var mostrarMenu = true;
        while (mostrarMenu) {
            System.out.println(".: RESTAURANTE EL CORRIENTAZO :.");
            System.out.println("0 -> Salir de la aplicación");
            System.out.println("1 -> Gesition de Pedidos");
            System.out.println("2 -> Gesition de datos maestros\n");
            try {
                var opcion = leerEntero("Ingrese su opcion: ");
                switch (opcion) {
                    case 0:
                        mostrarMenu = false;
                        System.out.println("\nHasta pronto!");
                        break;
                    case 1:
                        gestionPedidos();
                        break;
                    case 2:
                        gestionDatosMaestros();
                        break;
                    default:
                        System.out.println("Opción invalida, intente de nuevo");
                        break;
                } 
            } catch (SQLException ex) {
                System.err.println("Ha ocurrido un error con la base de datos.");
                System.err.println("\t" + ex.getMessage());
            }
        }
    }
    

    private void gestionDatosMaestros() throws SQLException {
        
        var mostrarMenu = true;
        while (mostrarMenu) {
            System.out.println("\n.: GESTION DE DATOS MAESTROS :.");
            System.out.println("0 -> Regresar");
            System.out.println("1 -> creación de mesas");
            System.out.println("2 -> creación de Opciones de Sopa");
            System.out.println("3 -> creación de Opciones de Principio");
            System.out.println("4 -> creación de Carne");
            System.out.println("5 -> creación de Ensalada");
            System.out.println("6 -> creación de Jugo");
            System.out.println("7 -> creación de Adicional");
            var opcion = leerEntero("Ingrese su opcion: ");
            switch (opcion) {
                case 0:
                    mostrarMenu = false;
                    System.out.println("\nHasta pronto!");
                    break;
                case 1:
                    controller.crearMesa();
                    break;
                case 2:
                    //TODO: Implementar
                    break;
                case 3:
                    //TODO: Implementar
                    break;
                case 4:
                    //TODO: Implementar
                    break;
                case 5:
                    //TODO: Implementar
                    break;
                case 6:
                    //TODO: Implementar
                    break;
                case 7:
                    //TODO: Implementar
                    break;
                default:
                    System.out.println("Opción invalida, intente de nuevo");
                    break;
            }
        esperarEnter();
        }
    }

    private void gestionPedidos() throws SQLException {
        var mesa = controller.seleccionarMesa();

        System.out.println("\n.: GESTION DE PEDIDOS :.");
        System.out.println("0 -> Volver al menu principal");
        System.out.println("1 -> Agregar pedido a mesa");
        System.out.println("2 -> Agregar adicional a mesa");
        System.out.println("3 -> Entregar Pedido");
        System.out.println("4 -> Pagar cuenta");
        System.out.println("5 -> Consultar estado de la mesa\n");

        var opcion = leerEntero("Ingrese su opcion: ");
        switch (opcion) {
            case 0:

                break;
            case 1:
                controller.agregarPedido(mesa);
                break;
            case 2:
                break;
            case 3:
                controller.entregarPedido(mesa);
                break;
            case 4:
                try {
                    var devuelta = controller.pagarCuenta(mesa);
                    System.out.printf("La devuelta es de $ %,d %n", devuelta);
                } catch (PagoinsuficienteException e) {
                    mostrarError(e.getMessage());
                    e.printStackTrace();
                } catch (ObjetoNoExistenteEsception e) {
                    mostrarError("La información de los pedidos es  inconsistente");
                }
                break;
            case 5:
                controller.mostrarEstadoMesa(mesa);
                break;
            default:
                System.out.println("Opción invalida, intente de nuevo");
                break;
        }
        esperarEnter();
    }

    private void esperarEnter() {
        System.out.println("Presione Enter para continuar");
        entrada.nextLine();
    }

    public void mostrarError(String error) {
        System.err.println(error);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public Integer leerEntero(String mensaje) {
        return leerEntero(mensaje, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public Integer leerEntero(String mensaje, Integer minValue, Integer maxValue) {
        Integer opcion = null;
        do {
            try {
                System.out.print(mensaje);
                opcion = entrada.nextInt();
                if (opcion > maxValue) {
                    System.err.println("Opción inválida\nIntente de nuevo");
                    opcion = null;
                }
            } catch (Exception e) {
                System.err.println("Opción inválida\nIntente de nuevo");
            } finally {
                entrada.nextLine();
            }
        } while (opcion == null);
        return opcion;
    }
}
