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


    public Movimiento(CartaInglesa carta, ZonaJuego zonaOrigen, int indiceOrigen,
                      ZonaJuego zonaDestino, int indiceDestino) {
        this.carta = carta;
        this.zonaOrigen = zonaOrigen;
        this.indiceOrigen = indiceOrigen;
        this.zonaDestino = zonaDestino;
        this.indiceDestino = indiceDestino;
        cantidad = 1;
    }

    public Movimiento(CartaInglesa carta, ZonaJuego zonaOrigen, int indiceOrigen,
                      ZonaJuego zonaDestino, int indiceDestino, int cantidad) {
        this.carta = carta;
        this.zonaOrigen = zonaOrigen;
        this.indiceOrigen = indiceOrigen;
        this.zonaDestino = zonaDestino;
        this.indiceDestino = indiceDestino;
        this.cantidad = cantidad;
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

    public int getCantidad() {
        return cantidad;
    }

    public ZonaJuego getZonaDestino() {
        return zonaDestino;
    }

    public int getIndiceDestino() {
        return indiceDestino;
    }

}
