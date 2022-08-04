package com.restaurante.dcarrasco.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.restaurante.dcarrasco.exception.ObjetoNoExistenteEsception;
import com.restaurante.dcarrasco.exception.PagoinsuficienteException;
import com.restaurante.dcarrasco.modelo.Carne;
import com.restaurante.dcarrasco.modelo.Ensalada;
import com.restaurante.dcarrasco.modelo.EstadoPedido;
import com.restaurante.dcarrasco.modelo.Jugo;
import com.restaurante.dcarrasco.modelo.Mesa;
import com.restaurante.dcarrasco.modelo.Principio;
import com.restaurante.dcarrasco.modelo.Sopa;
import com.restaurante.dcarrasco.vista.MesaView;
import com.restaurante.dcarrasco.vista.PedidoView;
import com.restaurante.dcarrasco.vista.PrincipalView;

public class RestauranteController {

    private MesaView mesaView;
    private PedidoView pedidoView;
    private PrincipalView principalView;

    private List<Mesa> mesas;
    private List<Sopa> sopas;
    private List<Principio> principios;
    private List<Carne> carnes;
    private List<Ensalada> ensaladas;
    private List<Jugo> jugos;

    public RestauranteController(Scanner entrada) {
        mesaView = new MesaView(entrada);
        pedidoView = new PedidoView(entrada, this);
        principalView = new PrincipalView(entrada, this);

        mesas = new ArrayList<>();
        sopas = new ArrayList<>();
        principios = new ArrayList<>();
        carnes = new ArrayList<>();
        ensaladas = new ArrayList<>();
        jugos = new ArrayList<>();
    }

    // TODO Es un método temporal
    public void iniciarBaseDatos() {
        mesas.add(new Mesa("01"));
        mesas.add(new Mesa("02"));
        mesas.add(new Mesa("03"));
        mesas.add(new Mesa("04"));
        mesas.add(new Mesa("05"));
        mesas.add(new Mesa("06"));
        mesas.add(new Mesa("07"));

        sopas.add(new Sopa("Pastas"));
        sopas.add(new Sopa("Arroz"));
        sopas.add(new Sopa("Consomé"));
        sopas.add(new Sopa("Verduras"));

        principios.add(new Principio("Fríjoles"));
        principios.add(new Principio("Lentejas"));
        principios.add(new Principio("Espaguetis"));
        principios.add(new Principio("Espinacas"));

        carnes.add(new Carne("Res a la plancha"));
        carnes.add(new Carne("Cerdo a la plancha"));
        carnes.add(new Carne("Pollo sudado"));
        carnes.add(new Carne("Sobrebarriga"));

        ensaladas.add(new Ensalada("tomate"));
        ensaladas.add(new Ensalada("Ensalada dulce"));
        ensaladas.add(new Ensalada("Jardinera"));
        ensaladas.add(new Ensalada("Remolacha"));

        jugos.add(new Jugo("Guanabana"));
        jugos.add(new Jugo("Naranja"));
        jugos.add(new Jugo("Limonada"));
        jugos.add(new Jugo("Mora"));
    }

    public void iniciarAplicacion() {
        principalView.iniciarAplicacion();
    }

    public void crearMesa() {
        // TODO Crear mesa
        // pedir al usuario el número de la mesa
        var numeroMesa = mesaView.leerNumeroMesa();

        // crear una nueva mesa en el sistema
        var mesa = new Mesa(numeroMesa);

        // Agregar a la lista de mesas en sistema
        mesas.add(new Mesa(numeroMesa));
        // Mostrar al usuario las mesas registradas
        mesaView.mostrarMesas(mesas);
    }

    public Mesa consultarMesa(String numero) throws ObjetoNoExistenteEsception {
        return mesas.stream()
                .filter(mesa -> mesa.getNumero().equals(numero))
                .findFirst()
                .orElseThrow(() -> new ObjetoNoExistenteEsception("No existe una mesa con el número: " + numero));
    }

    public void agregarPedido(Mesa mesa) {
        var pedido = pedidoView.cargarPedido();
        System.out.println("Pedido: " + pedido);

        mesa.agregarPedido(pedido);
    }

    public List<Sopa> listarSopas() {
        return sopas;
    }

    public List<Principio> listarPrincipios() {
        return principios;
    }

    public List<Carne> listarCarnes() {
        return carnes;
    }

    public List<Ensalada> listarEnsaladas() {
        return ensaladas;
    }

    public List<Jugo> listarJugos() {
        return jugos;
    }

    public Integer pagarCuenta(Mesa mesa) throws PagoinsuficienteException {
        var efectivo = mesaView.leerValorEfectivo();

        var total = mesa.calcularTotal();
        if (efectivo < total) {
            // TODO Lanzar excepcion por fondos insuficinetes
            throw new PagoinsuficienteException("El efectivo no es suficiente para pagar el total de la mesa");
        }
        // Eliminar pedidos
        mesa.limpiarPedidos();

        return efectivo - total;
    }

    public Mesa seleccionarMesa() {
        while (true) {
            principalView.mostrarMensaje("Listado de mesas existentes");
            for (int i = 0; i < mesas.size(); i++) {
                principalView.mostrarMensaje(
                    String.format("%d -> %s", (i + 1), mesas.get(i)));
            }
            var opcion = principalView.leerEntero("\nCual es su elección: ");
            if (opcion >= 1 && opcion <= mesas.size()) {
                return mesas.get(opcion -1);
            } else {
            principalView.mostrarError("Opción inválida");
            }
        }
    }

    public void mostrarEstadoMesa(Mesa mesa) {
        principalView.mostrarMensaje("Mesa: " + mesa);
        mesa.getPedidos().stream().map(p -> p.toString())
            .forEach(principalView::mostrarMensaje);
    }

    public void entregarPedido(Mesa mesa) {
            var pedidos = mesa.getPedidos().stream()
                .filter(p -> p.getEstado() == EstadoPedido.PENDIENTE_ENTREGAR)
                .collect(Collectors.toList());

            if (pedidos.isEmpty()) {
                principalView.mostrarError("La mesa no tiene pedidos a entregar");
                return;
            }
        
        while (true) {
            principalView.mostrarMensaje("Listado de pedidos pendientes");
            for (int i = 0; i < mesa.getPedidos().size(); i++) {
                var pedido = mesa.getPedidos().get(i);
                if (pedido.getEstado() != EstadoPedido.PENDIENTE_ENTREGAR) {
                    continue;
                }
                principalView.mostrarMensaje(String.format(
                        "%d -> %s", (i + 1), mesa.getPedidos().get(i)));
            }
            var opcion = principalView.leerEntero("\nCual es su elección: ");
            if (opcion >= 1 && opcion <= mesa.getPedidos().size()) {
                mesa.getPedidos().get(opcion - 1).entregar();
                break;
            } else {
            principalView.mostrarError("Opción inválida");
            }
        }
    }
}
