package com.example.demo.Modelo.Baraja;

/**
 * Write a description of class CartaInglesa here.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-2)
 */
public class CartaInglesa extends Carta {

    public CartaInglesa(int valor, Palo figura, String color) {
        super(valor, figura, color);
    }

    @Override
    public int compareTo(Carta o) {
        int valor = Integer.compare(getValor(), o.getValor());
        if (valor != 0) return valor;
        return Integer.compare(getPalo().getPeso(),
                o.getPalo().getPeso());
    }
}
