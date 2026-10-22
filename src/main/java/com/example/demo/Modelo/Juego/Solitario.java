package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import com.example.demo.Modelo.Baraja.Mazo;

public class Solitario {
    public final int NUM_COLUMNAS = 8;
    public final int NUM_RESERVAS = 8;
    public final int NUM_FOUNDATIONS = 4;
    private final int CARTAS_POR_COLUMNA = 6;
    private final int CARTAS_EN_RESERVA_INICIAL = 4;

    private ColumnaCartas[] columnas;
    private Reserva[] reservas;
    private Foundation[] foundations;
    private Mazo mazo;
    private boolean soloReyes;

    public Solitario(boolean soloReyes) {
        this.soloReyes = soloReyes;
        iniciarJuego();
    }

    public void iniciarJuego() {
        mazo = new Mazo();

        columnas = new ColumnaCartas[NUM_COLUMNAS];
        for (int i = 0; i < NUM_COLUMNAS; i++) {
            columnas[i] = new ColumnaCartas(soloReyes);
        }

        reservas = new Reserva[NUM_RESERVAS];
        for (int i = 0; i < NUM_RESERVAS; i++) {
            reservas[i] = new Reserva();
        }

        foundations = new Foundation[NUM_FOUNDATIONS];
        for (int i = 0; i < NUM_FOUNDATIONS; i++) {
            foundations[i] = new Foundation();
        }

        repartir();
    }

    private void repartir() {
        for (int i = 0; i < NUM_COLUMNAS; i++) {
            for (int j = 0; j < CARTAS_POR_COLUMNA; j++) {
                columnas[i].colocarCarta(mazo.obtenerUnaCarta());
            }
        }
        for (int i = 0; i < CARTAS_EN_RESERVA_INICIAL; i++) {
            reservas[i].colocarCarta(mazo.obtenerUnaCarta());
        }
    }

    // Movimientos para cuando hagamos el undo, por mientras los vuelvo booleans
    // y no les pongo nada que almacene el movimeitno

    public boolean columnaAColumna(int origen, int destino) {
        if (origen == destino) return false;
        CartaInglesa carta = columnas[origen].verTope();
        if (carta == null || !columnas[destino].agregarCarta(carta)) return false;
        columnas[origen].quitarCarta();
        return true;
    }

    public boolean columnaAReserva(int col, int res) {
        CartaInglesa carta = columnas[col].verTope();
        if (carta == null || !reservas[res].agregarCarta(carta)) return false;
        columnas[col].quitarCarta();
        return true;
    }

    public boolean reservaAColumna(int res, int col) {
        CartaInglesa carta = reservas[res].getCarta();
        if (carta == null || !columnas[col].agregarCarta(carta)) return false;
        reservas[res].quitarCarta();
        return true;
    }

    public boolean columnaAFoundation(int col) {
        CartaInglesa carta = columnas[col].verTope();
        if (carta == null || !agregarAFoundation(carta)) return false;
        columnas[col].quitarCarta();
        return true;
    }

    public boolean reservaAFoundation(int res) {
        CartaInglesa carta = reservas[res].getCarta();
        if (carta == null || !agregarAFoundation(carta)) return false;
        reservas[res].quitarCarta();
        return true;
    }

    private boolean agregarAFoundation(CartaInglesa carta) {
        for (Foundation f : foundations) {
            if (f.agregarCarta(carta)) return true;
        }
        return false;
    }

    public boolean juegoGanado() {
        for (Foundation f : foundations) {
            if (!f.estaCompleta()) return false;
        }
        return true;
    }

    // Todo: hacer metodos de pista, undo y acabar el juego si no hay movimientos posibles



}
