package com.example.demo.Modelo.Baraja;

/**
 * Write a description of class Mazo here.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-2)
 */

import java.util.ArrayList;
import java.util.Random;

import com.example.demo.Modelo.Listas.*;

public class Mazo {
    private ListaDobleCircular<CartaInglesa> cartas;

    public Mazo() {
        cartas = new ListaDobleCircular<>();
        llenar(); // crea todas las cartas, excluyendo Jokers
        mezclar();
    }

    public CartaInglesa obtenerUnaCarta() {
        return cartas.eliminaInicio();
    }

    // A lo mejor tendré que reescribir esto
    private void mezclar() {
        ListaDobleCircular<CartaInglesa> cartasMezcladas = new ListaDobleCircular<>();
        Random rnd = new Random();
        while (!cartas.isEmpty()) {
            int pos = rnd.nextInt(cartas.getTamano());
            cartasMezcladas.insertaInicio(cartas.eliminaPosicion(pos));
        }
        cartas = cartasMezcladas;
    }

    private void llenar() {
        ArrayList<CartaInglesa> listaCartas = new ArrayList<>();

        for (int i = 1; i <=13 ; i++) {
            for (Palo palo : Palo.values()) {
                cartas.insertaFin(new CartaInglesa(i, palo, palo.getColor()));
            }
        }
    }

    public boolean isEmpty() {
        return cartas.isEmpty();
    }

    public int size() {
        return cartas.getTamano();
    }

    @Override
    public String toString() {
        return cartas.recorrer();
    }


}
