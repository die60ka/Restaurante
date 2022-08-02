package com.restaurante.dcarrasco.vista;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import com.restaurante.dcarrasco.modelo.Mesa;

public class MesaView {

    public String leerNumeroMesa() {
        System.out.println(".: AGREGANDO UNA NUEVA MESA :.");

        do {
            try {
                var sc = new Scanner(System.in);
                System.out.print("Ingrese el número de la mesa: ");
                var numero = sc.nextLine();
    
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

}
