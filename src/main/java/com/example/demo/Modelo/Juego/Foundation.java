package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import com.example.demo.Modelo.Listas.ListaSimple;

public class Foundation {
    private ListaSimple<CartaInglesa> cartasContenidas;

    public Foundation() {
        cartasContenidas = new ListaSimple<>();
    }

    public boolean puedeRecibirCarta(CartaInglesa carta) {
        CartaInglesa tope = cartasContenidas.peekInicio();
        // el primer elemento es un as
        if (tope == null) return carta.getValor() == 1;
        return tope.tieneElMismoPalo(carta) && tope.esLaSiguiente(carta);
    }

    public boolean agregarCarta(CartaInglesa carta) {
        if (!puedeRecibirCarta(carta)) return false;
        cartasContenidas.insertaInicio(carta);
        return true;
    }

    public CartaInglesa verTope() { return cartasContenidas.peekInicio(); }

    public CartaInglesa quitarCarta() { return cartasContenidas.eliminaInicio(); }

    public boolean estaVacia() { return cartasContenidas.isEmpty(); }

    public boolean estaCompleta() {
        return cartasContenidas.size() == 13;
    }
}
