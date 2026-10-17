package com.example.demo.Modelo.Baraja;

/**
 * Write a description of class Mazo here.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-2)
 */

import java.util.ArrayList;
import java.util.Collections;
import com.example.demo.Modelo.Listas.*;

public class Mazo {
    private ListaSimple<CartaInglesa> cartas;

    public Mazo() {
        cartas = new ListaSimple<>();
        llenar(); // crea todas las cartas, excluyendo Jokers
        // Se quitó el metodo mezclar() para aprovecharlo mejor en el metodo llenar()
    }

    /**
     * Obtiene todas las cartas del mazo.
     * Aqui se van a aplicar modificaciones parecidas a la clase Mano para retornar las cartas
     * @return ArrayList con todas las cartas que haya en el mazo.
     */
    public ListaSimple<CartaInglesa> getCartas() {
        ListaSimple<CartaInglesa> pilaAux = new ListaSimple<>();

        return pilaAux;
    }

    public CartaInglesa obtenerUnaCarta() {
        return cartas.eliminaInicio();
    }

    // A lo mejor tendré que reescribir esto
    private void mezclar(ArrayList<CartaInglesa> cartas) {
        Collections.shuffle(cartas);
    }

    private void llenar() {
        ArrayList<CartaInglesa> listaCartas = new ArrayList<>();

        for (int i = 2; i <=14 ; i++) {
            for (Palo palo : Palo.values()) {
                CartaInglesa c = new CartaInglesa(i,palo, palo.getColor());
                listaCartas.add(c);
            }
        }
        mezclar(listaCartas);
        insertarCartasAPila(listaCartas);

    }

    private void insertarCartasAPila(ArrayList<CartaInglesa> listaDeCartas) {
        listaDeCartas.forEach(c -> cartas.insertaInicio(c));
    }

    public void vaciarMazo() {
        while(!cartas.isEmpty()) {
            cartas.eliminaInicio();
        }
    }

    @Override
    public String toString() {
        return getCartas().toString();
    }
}
