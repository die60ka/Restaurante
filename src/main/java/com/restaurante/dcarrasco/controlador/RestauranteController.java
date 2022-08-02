package com.restaurante.dcarrasco.controlador;

import java.util.ArrayList;
import java.util.List;

import com.restaurante.dcarrasco.modelo.Mesa;
import com.restaurante.dcarrasco.vista.MesaView;

public class RestauranteController {

    private MesaView mesaView;

    private List<Mesa> mesas;

    public RestauranteController(){
        mesaView = new MesaView();
        mesas  = new ArrayList<>();
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
}
