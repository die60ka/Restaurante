package com.restaurante.dcarrasco.vista;

import java.util.List;
import java.util.Scanner;

import com.restaurante.dcarrasco.modelo.Mesa;

public class MesaView {

    private Scanner entrada;

    public MesaView(Scanner entrada) {
        this.entrada = entrada;
    }

    public String leerNumeroMesa() {
        System.out.println(".: AGREGANDO UNA NUEVA MESA :.");

        do {
            try {
                System.out.print("Ingrese el número de la mesa: ");
                var numero = entrada.nextLine();
    
                return numero;
            } catch (Exception ex) {
                System.err.println("Por favor ingrese un valor válido");
            }
        } while (true);
    }

    public void mostrarMesas(List<Mesa> mesas) {
        System.out.println(".: MESAS EXISTENTES :.");
        mesas.forEach(System.out::println);
    }

    public Integer leerValorEfectivo() {
        System.out.println(".: PAGO DE LA CUENTA :.");

        do {
            try {
                System.out.print("Ingrese el efectivo proporcionado por el cliente: ");
                var numero = entrada.nextInt();
                entrada.nextLine();
    
                return numero;
            } catch (Exception ex) {
                System.err.println("Por favor ingrese un valor válido");
            }
        } while (true);
    }

}
