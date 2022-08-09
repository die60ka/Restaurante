package com.restaurante.dcarrasco.modelo;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private Integer id;
    private String numero;
    private List<Pedido> pedidos;

    public Mesa(String numero) {
        this.numero = numero;
        this.pedidos = new ArrayList<>();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    @Override
    public String toString() {
        return numero;
    }

}
