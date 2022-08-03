package com.restaurante.dcarrasco.controlador;

import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.restaurante.dcarrasco.exception.ObjetoNoExistenteEsception;
import com.restaurante.dcarrasco.modelo.Carne;
import com.restaurante.dcarrasco.modelo.Ensalada;
import com.restaurante.dcarrasco.modelo.Jugo;
import com.restaurante.dcarrasco.modelo.Mesa;
import com.restaurante.dcarrasco.modelo.Pedido;
import com.restaurante.dcarrasco.modelo.Principio;
import com.restaurante.dcarrasco.modelo.Sopa;
import com.restaurante.dcarrasco.vista.MesaView;
import com.restaurante.dcarrasco.vista.PedidoView;

public class RestauranteController {

    private MesaView mesaView;
    private PedidoView pedidoView;

    private List<Mesa> mesas;
    private List<Sopa> sopas;
    private List<Principio> principios;
    private List<Carne> carnes;
    private List<Ensalada> ensaladas;
    private List<Jugo> jugos;

    public RestauranteController(Scanner entrada){
        mesaView = new MesaView(entrada);
        pedidoView = new PedidoView(entrada, this);
        mesas  = new ArrayList<>();
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
    
    public void crearMesa() {
        // TODO Crear mesa
        // pedir al usuario el número de la mesa
        var numeroMesa = mesaView.leerNumeroMesa();

        // crear una nueva mesa en el sistema
        var mesa = new Mesa(numeroMesa);

        //Agregar a la lista de mesas en sistema
        mesas.add(new Mesa(numeroMesa));
        //Mostrar al usuario las mesas registradas
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
        System.out.println("Pedido: " +  pedido);

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

}
