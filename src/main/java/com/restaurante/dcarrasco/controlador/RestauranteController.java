package com.restaurante.dcarrasco.controlador;

import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
import com.restaurante.dcarrasco.modelo.dao.MesaDao;
import com.restaurante.dcarrasco.modelo.dao.OpcionAlimentoDao;
import com.restaurante.dcarrasco.modelo.dao.PedidoDao;
import com.restaurante.dcarrasco.vista.MesaView;
import com.restaurante.dcarrasco.vista.PedidoView;
import com.restaurante.dcarrasco.vista.PrincipalView;

public class RestauranteController {

    private MesaView mesaView;
    private PedidoView pedidoView;
    private PrincipalView principalView;

    private MesaDao mesaDao;
    private PedidoDao pedidoDao;
    private OpcionAlimentoDao alimentoDao;

    private List<Sopa> sopas;
    private List<Principio> principios;
    private List<Carne> carnes;
    private List<Ensalada> ensaladas;
    private List<Jugo> jugos;

    public RestauranteController(Scanner entrada) {
        mesaView = new MesaView(entrada);
        pedidoView = new PedidoView(entrada, this);
        principalView = new PrincipalView(entrada, this);

        mesaDao = new MesaDao();
        pedidoDao = new PedidoDao();
        alimentoDao = new OpcionAlimentoDao();

        sopas = new ArrayList<>();
        principios = new ArrayList<>();
        carnes = new ArrayList<>();
        ensaladas = new ArrayList<>();
        jugos = new ArrayList<>();
    }

    // TODO Es un método temporal
    public void iniciarBaseDatos() throws SQLException {
        /**
         * CREA NÚMERO DE MESA
         * mesaDao.crear(new Mesa("01"));
         * mesaDao.crear(new Mesa("02"));
         * mesaDao.crear(new Mesa("03"));
         * mesaDao.crear(new Mesa("04"));
         * mesaDao.crear(new Mesa("05"));
         * mesaDao.crear(new Mesa("06"));
         * mesaDao.crear(new Mesa("07"));
         */

        // sopas.add(new Sopa("Pastas"));
        // sopas.add(new Sopa("Arroz"));
        // sopas.add(new Sopa("Consomé"));
        // sopas.add(new Sopa("Verduras"));

    }

    public void iniciarAplicacion() {
        principalView.iniciarAplicacion();
    }

    public void crearMesa() throws SQLException {
        // TODO Crear mesa
        // pedir al usuario el número de la mesa
        var numeroMesa = mesaView.leerNumeroMesa();

        // crear una nueva mesa en el sistema
        var mesa = new Mesa(numeroMesa);

        // Agregar a la lista de mesas en sistema
        mesaDao.crear(mesa);
        // Mostrar al usuario las mesas registradas
        mesaView.mostrarMesas(mesaDao.listar());
    }

    public Mesa consultarMesa(String numero) throws ObjetoNoExistenteEsception, SQLException {
        return mesaDao.consultar(numero);
    }

    public void agregarPedido(Mesa mesa) throws SQLException {
        // Pedir al usuario los datos del pedido
        var pedido = pedidoView.cargarPedido();
        System.out.println("Pedido: " + pedido);

        // Agregar el pedido a la mesa
        mesa.agregarPedido(pedido);

        // Guardar pedido en base de datos
        pedido.setMesa(mesa);
        ;
        pedidoDao.crear(pedido);
    }

    public List<Sopa> listarSopas() throws SQLException {
        return alimentoDao.listarSopas();
    }

    public List<Principio> listarPrincipios() throws SQLException {
        return alimentoDao.listarPrincipio();
    }

    public List<Carne> listarCarnes() throws SQLException {
        return alimentoDao.listarCarnes();
    }

    public List<Ensalada> listarEnsaladas() throws SQLException {
        return alimentoDao.listarEnsaladas();
    }

    public List<Jugo> listarJugos() throws SQLException {
        return alimentoDao.listarJugos();
    }

    private Integer calcularTotalMesa(Mesa mesa) throws SQLException, ObjetoNoExistenteEsception {
        var pedidos = pedidoDao.listarPedidos(mesa);
        return pedidos.stream()
                .filter(pedido -> pedido.getEstado() == EstadoPedido.PENDIENTE_COBRAR)
                .map(pedido -> pedido.calcularValor())
                .reduce((a, b) -> a + b)
                .orElse(0);
    }

    public Integer pagarCuenta(Mesa mesa) throws PagoinsuficienteException, SQLException, ObjetoNoExistenteEsception {
        var total = calcularTotalMesa(mesa);
        principalView.mostrarMensaje(String.format("La cuenta es de $ %,d", total));

        var efectivo = mesaView.leerValorEfectivo();

        if (efectivo < total) {
            // TODO Lanzar excepcion por fondos insuficinetes
            throw new PagoinsuficienteException("El efectivo no es suficiente para pagar el total de la mesa");
        }
        // Eliminar pedidos
        pedidoDao.borrarPedidosMesa(mesa);

        return efectivo - total;
    }

    public Mesa seleccionarMesa() throws SQLException {
        while (true) {
            principalView.mostrarMensaje("Listado de mesas existentes");
            var mesas = mesaDao.listar();
            for (int i = 0; i < mesas.size(); i++) {
                principalView.mostrarMensaje(
                        String.format("%d -> %s", (i + 1), mesas.get(i)));
            }
            var opcion = principalView.leerEntero("\nCual es su elección: ");
            if (opcion >= 1 && opcion <= mesas.size()) {
                return mesas.get(opcion - 1);
            } else {
                principalView.mostrarError("Opción inválida");
            }
        }
    }

    public void mostrarEstadoMesa(Mesa mesa) throws SQLException {
        principalView.mostrarMensaje("Mesa: " + mesa);
        try {
            pedidoDao.listarPedidos(mesa).stream().map(p -> p.toString())
                    .forEach(principalView::mostrarMensaje);

            var total = calcularTotalMesa(mesa);
            principalView.mostrarMensaje(String.format("Estan debiendo $ %,d.", total));
        } catch (ObjetoNoExistenteEsception ex) {
            principalView.mostrarMensaje("No hay pedidos para esta mesa");
        }
    }

    public void entregarPedido(Mesa mesa) throws SQLException {
        try {
            var pedidosMesa = pedidoDao.listarPedidos(mesa);
            var pedidos = pedidosMesa.stream()
                    .filter(p -> p.getEstado() == EstadoPedido.PENDIENTE_ENTREGAR)
                    .collect(Collectors.toList());

            if (pedidos.isEmpty()) {
                principalView.mostrarError("La mesa no tiene pedidos a entregar");
                return;
            }

            while (true) {
                principalView.mostrarMensaje("Listado de pedidos pendientes");
                for (int i = 0; i < pedidos.size(); i++) {
                    var pedido = pedidos.get(i);
                    if (pedido.getEstado() != EstadoPedido.PENDIENTE_ENTREGAR) {
                        continue;
                    }
                    principalView.mostrarMensaje(String.format(
                            "%d -> %s", (i + 1), pedidos.get(i)));
                }
                var opcion = principalView.leerEntero("\nCual es su elección: ");
                if (opcion >= 1 && opcion <= pedidosMesa.size()) {
                    var pedido = pedidos.get(opcion - 1);
                    pedido.entregar();
                    pedidoDao.actualizarEstadoPedido(pedido);

                    break;
                } else {
                    principalView.mostrarError("Opción inválida");
                }
            }
        } catch (ObjetoNoExistenteEsception ex) {
            principalView.mostrarError("No hay pedidos pendientes por entregar");
        }
    }
}
