package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Listas.ListaSimple;

public class Movimiento {
    private ListaSimple<Reserva> reservas;
    private ListaSimple<ColumnaCartas> columnas;
    private ListaSimple<Foundation> foundations;

    public Movimiento(ListaSimple<Reserva> reservas,
                      ListaSimple<ColumnaCartas> columnas,
                      ListaSimple<Foundation> foundations) {
        this.reservas = reservas;
        this.columnas = columnas;
        this.foundations = foundations;
    }

    public ListaSimple<Reserva> getReservas() {
        return reservas;
    }

    public ListaSimple<ColumnaCartas> getColumnas() {
        return columnas;
    }

    public ListaSimple<Foundation> getFoundations() {
        return foundations;
    }
}
