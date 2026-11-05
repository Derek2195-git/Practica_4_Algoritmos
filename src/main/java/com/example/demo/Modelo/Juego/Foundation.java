package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import com.example.demo.Modelo.Listas.ListaSimple;

public class Foundation {
    private ListaSimple<CartaInglesa> cartasContenidas;

    /**
     * Crea una foundation, inicializando la lista de cartas que puede contener
     */
    public Foundation() {
        cartasContenidas = new ListaSimple<>();
    }

    /**
     * Metodo que agrega una carta a la foundation
     * @param carta Carta a ingresar
     * @return Booleano que indica si se realizó la inserción correctamente
     */
    public boolean agregarCarta(CartaInglesa carta) {
        if (!puedeRecibirCarta(carta)) return false;
        cartasContenidas.insertaInicio(carta);
        return true;
    }

    /**
     * Metodo que revisa si la foundation puede recibir una carta
     * @param carta Carta a ingresar
     * @return Booleano que indica si se puede recibir la carta en la foundation
     */
    public boolean puedeRecibirCarta(CartaInglesa carta) {
        // Sacamos la ultima carta de la foundation, si esta vacia, solo recibe un As
        CartaInglesa tope = cartasContenidas.peekInicio();
        if (tope == null) return carta.getValor() == 1;
        // Revisamos si tiene el mismo palo y el valor a ingresar es el siguiente en la secuencia
        return tope.tieneElMismoPalo(carta) && tope.esLaSiguiente(carta);
    }

    /**
     * Metodo que saca la ultima carta de la foundation
     * @return Ultima carta ingresada en la foundation
     */
    public CartaInglesa verTope() { return cartasContenidas.peekInicio(); }

    /**
     * Metodo que quita la ultima carta de la foundation
     * @return Ultima carta que tenía el foundation
     */
    public CartaInglesa quitarCarta() { return cartasContenidas.eliminaInicio(); }

    /**
     * Metodo que revisa si una foundation está vacia
     * @return Boolean que indica si la foundation está vacia
     */
    public boolean estaVacia() { return cartasContenidas.isEmpty(); }

    /**
     * Metodo que indica si la foundation ya se completó
     * @return Booleano que indica si ya están todas las cartas en la foundation
     */
    public boolean estaCompleta() {
        return cartasContenidas.size() == 13;
    }
}
