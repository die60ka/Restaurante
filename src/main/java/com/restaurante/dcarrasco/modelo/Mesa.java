package com.restaurante.dcarrasco.modelo;

import java.util.ArrayList;
import java.util.List;

import com.restaurante.dcarrasco.exception.PagoinsuficienteException;

public class Mesa {
    private String numero;
    private List<Pedido> pedidos;

    public Mesa(String numero) {
        this.numero = numero;
        this.pedidos = new ArrayList<>();
    }

    public String getNumero() {
        return numero;
    }

    public void entregarPedido(Pedido pedido) {
        pedido.entregar();
    }

    public void agregarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    public Integer calcularTotal() {
        return this.pedidos.stream()
                .map(pedido -> pedido.calcularValor())
                .reduce((a, b) -> a + b)
                .orElse(0);
    }

    public Integer pago(Integer efectivo) throws PagoinsuficienteException{
        var total = calcularTotal();
        if (efectivo < total) {
            // TODO Lanzar excepcion por fondos insuficinetes
            throw new PagoinsuficienteException("El efectivo no es suficiente para pagar el total de la mesa");
        }
        // Eliminar pedidos
        this.pedidos.clear();
        
        return efectivo - total;
    }

    @Override
    public String toString() {
        return numero;
    }

}
