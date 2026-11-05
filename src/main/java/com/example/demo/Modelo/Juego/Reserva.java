package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;

public class Reserva {
    private CartaInglesa carta;

    /**
     * Construye una reserva, dejando la carta como un valor nulo
     */
    public Reserva() {
        carta = null;
    }

    /**
     * Agrega una carta a la reserva
     * @param carta Carta a agregar
     * @return Boolean que indica si se pudo realizar la inserción correctamente o no
     */
    public boolean agregarCarta(CartaInglesa carta) {
        if (!estaVacia()) return false;
        this.carta = carta;
        return true;
    }

    /**
     * Setter de la carta de la reserva, ignorando si tiene contenido adentro
     * @param carta Carta a ingresar
     */
    public void setCarta(CartaInglesa carta) {
        this.carta = carta;
    }

    /**
     * Getter de la carta
     * @return Carta que contiene la reserva
     */
    public CartaInglesa getCarta() {
        return carta;
    }

    /**
     * Metodo que quita la carta que contenga la reserva, devolviendola
     * @return Carta eliminada de la reserva
     */
    public CartaInglesa quitarCarta() {
        CartaInglesa c = carta;
        carta = null;
        return c;
    }

    /**
     * Metodo que revisa si la reserva está vacia
     * @return Booleano que indica si esta vacia
     */
    public boolean estaVacia() {
        return carta == null;
    }
}
