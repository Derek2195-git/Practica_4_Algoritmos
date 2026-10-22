package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;

public class Reserva {
    private CartaInglesa carta;

    public Reserva() {
        carta = null;
    }

    public boolean agregarCarta(CartaInglesa carta) {
        if (!puedeRecibir()) return false;
        this.carta = carta;
        return true;
    }

    public boolean puedeRecibir() {
        return carta == null;
    }

    // Para el undo y reparto inicial
    public void colocarCarta(CartaInglesa carta) {
        this.carta = carta;
    }

    public CartaInglesa getCarta() {
        return carta;
    }

    public CartaInglesa quitarCarta() {
        CartaInglesa c = carta;
        carta = null;
        return c;
    }

    public boolean estaVacia() {
        return carta == null;
    }
}
