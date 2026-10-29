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


    public Movimiento(CartaInglesa carta, ZonaJuego zonaOrigen,
                      int indiceOrigen, ZonaJuego zonaDestino, int indiceDestino) {
        this.carta = carta;
        this.zonaOrigen = zonaOrigen;
        this.indiceOrigen = indiceOrigen;
        this.zonaDestino = zonaDestino;
        this.indiceDestino = indiceDestino;
    }

    public CartaInglesa getCarta() {
        return carta;
    }

    public ZonaJuego getZonaOrigen() {
        return zonaOrigen;
    }

    public int getIndiceOrigen() {
        return indiceOrigen;
    }

    public ZonaJuego getZonaDestino() {
        return zonaDestino;
    }

    public int getIndiceDestino() {
        return indiceDestino;
    }

}
