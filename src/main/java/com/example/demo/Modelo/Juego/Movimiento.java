package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import com.example.demo.Modelo.Listas.ListaSimple;
import com.example.demo.Modelo.ZonaJuego;

public class Movimiento {
    private CartaInglesa carta;
    private ZonaJuego zonaOrigen;
    private int indiceOrigen;
    private ZonaJuego zonaDestino;
    private int indiceDestino;
    private int cantidad;

    /**
     * Crea un objeto que contiene los movimientos realizados en la ronda,
     * teniendo en cuenta que solo se movió una carta
     * @param carta Carta que se movió
     * @param zonaOrigen Zona de donde provenia la carta
     * @param indiceOrigen Indice de la zona donde provenia dicha carta
     * @param zonaDestino Zona hacia donde se movió la carta
     * @param indiceDestino Indice de la zona donde se movió la carta
     */
    public Movimiento(CartaInglesa carta, ZonaJuego zonaOrigen, int indiceOrigen,
                      ZonaJuego zonaDestino, int indiceDestino) {
        this.carta = carta;
        this.zonaOrigen = zonaOrigen;
        this.indiceOrigen = indiceOrigen;
        this.zonaDestino = zonaDestino;
        this.indiceDestino = indiceDestino;
        cantidad = 1;
    }

    /**
     * Mismo constructor, pero ahora revisa la cantidad de cartas movidas
     * @param carta Carta que se movió
     * @param zonaOrigen Zona de donde provenia la carta
     * @param indiceOrigen Indice de la zona donde provenia dicha carta
     * @param zonaDestino Zona hacia donde se movió la carta
     * @param indiceDestino Indice de la zona donde se movió la carta
     * @param cantidad Cantidad de cartas que se movieron
     */
    public Movimiento(CartaInglesa carta, ZonaJuego zonaOrigen, int indiceOrigen,
                      ZonaJuego zonaDestino, int indiceDestino, int cantidad) {
        this.carta = carta;
        this.zonaOrigen = zonaOrigen;
        this.indiceOrigen = indiceOrigen;
        this.zonaDestino = zonaDestino;
        this.indiceDestino = indiceDestino;
        this.cantidad = cantidad;
    }

    /**
     * Getter de la carta
     * @return Carta ingresada
     */
    public CartaInglesa getCarta() {
        return carta;
    }

    /**
     * Getter de la zona donde proviene la carta
     * @return Enumeración que dice de que zona proviene la carta
     */
    public ZonaJuego getZonaOrigen() {
        return zonaOrigen;
    }

    /**
     * Getter del indice de donde proviene la carta
     * @return Indice en donde se ubica la carta antes del movimiento
     */
    public int getIndiceOrigen() {
        return indiceOrigen;
    }

    /**
     * Getter de la cantidad de cartas movidas
     * @return Cantidad de cartas que se movieron para el movimiento
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Getter de la zona hacia donde se movió la carta
     * @return Enumeración que indica a que parte del juego se movió la carta
     */
    public ZonaJuego getZonaDestino() {
        return zonaDestino;
    }

    /**
     * Getter del indice de la ubicación donde se movió la carta
     * @return Indice en donde se ubica la carta despues del movimiento
     */
    public int getIndiceDestino() {
        return indiceDestino;
    }

}
