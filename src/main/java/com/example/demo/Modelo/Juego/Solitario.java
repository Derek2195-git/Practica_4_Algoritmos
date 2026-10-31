package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import com.example.demo.Modelo.Baraja.Mazo;
import com.example.demo.Modelo.ZonaJuego;

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
    private ContenedorMovimiento historialMovimientos;
    private boolean soloReyes;

    public Solitario(boolean soloReyes) {
        this.soloReyes = soloReyes;
        iniciarJuego();
    }

    public void iniciarJuego() {
        historialMovimientos = new ContenedorMovimiento();
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
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.COLUMNA, origen, ZonaJuego.COLUMNA, destino));
        return true;
    }

    public boolean columnaAReserva(int col, int res) {
        CartaInglesa carta = columnas[col].verTope();
        if (carta == null || !reservas[res].agregarCarta(carta)) return false;
        columnas[col].quitarCarta();
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.COLUMNA, col, ZonaJuego.RESERVA, res));
        return true;
    }

    public boolean reservaAColumna(int res, int col) {
        CartaInglesa carta = reservas[res].getCarta();
        if (carta == null || !columnas[col].agregarCarta(carta)) return false;
        reservas[res].quitarCarta();
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.RESERVA, res, ZonaJuego.COLUMNA, col));
        return true;
    }

    public boolean columnaAFoundation(int col, int foun) {
        CartaInglesa carta = columnas[col].verTope();
        if (carta == null || foun < 0 || !foundations[foun].agregarCarta(carta)) return false;
        columnas[col].quitarCarta();
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.COLUMNA, col, ZonaJuego.FOUNDATION, foun));
        return true;
    }

    public boolean reservaAFoundation(int res, int foun) {
        CartaInglesa carta = reservas[res].getCarta();
        if (carta == null || foun < 0 || !foundations[foun].agregarCarta(carta)) return false;
        reservas[res].quitarCarta();
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.RESERVA, res, ZonaJuego.FOUNDATION, foun));
        return true;
    }

    private int agregarAFoundation(CartaInglesa carta) {
        for (int i = 0; i < foundations.length; i++) {
            if (foundations[i].agregarCarta(carta)) return i;
        }
        return -1;
    }

    private int buscarFoundation(CartaInglesa carta) {
        for (int i = 0; i < foundations.length; i++) {
            if (foundations[i].puedeRecibirCarta(carta)) return i;
        }
        return -1;
    }

    public boolean juegoGanado() {
        for (Foundation f : foundations) {
            if (!f.estaCompleta()) return false;
        }
        return true;
    }

    // Todo: hacer metodos de pista,acabar el juego si no hay movimientos posibles

    public boolean undo() {
        Movimiento movimiento = historialMovimientos.sacarMovimiento();
        if (movimiento == null) return false;

        switch (movimiento.getZonaDestino()) {
            case COLUMNA -> columnas[movimiento.getIndiceDestino()].quitarCarta();
            case RESERVA -> reservas[movimiento.getIndiceDestino()].quitarCarta();
            case FOUNDATION -> foundations[movimiento.getIndiceDestino()].quitarCarta();
        }

        switch (movimiento.getZonaOrigen()) {
            case COLUMNA -> columnas[movimiento.getIndiceOrigen()]
                    .colocarCarta(movimiento.getCarta());
            case RESERVA -> reservas[movimiento.getIndiceOrigen()]
                    .colocarCarta(movimiento.getCarta());
            case null, default -> {
            }
        }

        return true;
    }

    public Movimiento pedirPista() {
        Movimiento mov;
        if ((mov = encontrarMovimientoFoundation()) != null) return mov;
        if ((mov = encontrarMovimientoReservaAColumna(false)) != null) return mov;
        if ((mov = encontrarMovimientoColumnaAColumna(true)) != null) return mov;
        if ((mov = encontrarMovimientoColumnaAReserva()) != null) return mov;

        if ((mov = encontrarMovimientoReservaAColumna(true)) != null) return mov;
        return encontrarMovimientoColumnaAColumna(false);
    }

    public Movimiento encontrarMovimientoFoundation() {
        for (int col = 0; col < NUM_COLUMNAS; col++) {
            CartaInglesa carta = columnas[col].verTope();
            if (carta == null) continue;
            int f = buscarFoundation(carta);
            if (f != -1) {
                return new Movimiento(carta, ZonaJuego.COLUMNA, col, ZonaJuego.FOUNDATION, f);
            }
        }

        for (int res = 0; res < NUM_RESERVAS; res++) {
            CartaInglesa carta = reservas[res].getCarta();
            if (carta == null) continue;
            int f = buscarFoundation(carta);
            if (f != -1) {
                return new Movimiento(carta, ZonaJuego.RESERVA, res, ZonaJuego.FOUNDATION, f);
            }
        }
        return null;
    }

    public Movimiento encontrarMovimientoReservaAColumna(boolean permitirReservasVacias) {
        for (int res = 0; res < NUM_RESERVAS; res++) {
            CartaInglesa carta = reservas[res].getCarta();
            if (carta == null) continue;

            for (int col = 0; col < NUM_COLUMNAS; col++) {
                if (columnas[col].estaVacia() && !permitirReservasVacias) continue;
                if (columnas[col].puedeRecibir(carta)) {
                    return new Movimiento(carta, ZonaJuego.RESERVA, res, ZonaJuego.COLUMNA, col);
                }
            }
        }
        return null;
    }

    public Movimiento encontrarMovimientoColumnaAColumna(boolean soloCartasUtiles) {
        for (int ori = 0; ori < NUM_COLUMNAS; ori++) {
            CartaInglesa carta = columnas[ori].verTope();
            if (carta == null) continue;
            for (int dest = 0; dest < NUM_COLUMNAS; dest++) {
                if (dest == ori || columnas[dest].estaVacia()) continue;
                if (columnas[dest].puedeRecibir(carta))
                    return new Movimiento(carta, ZonaJuego.COLUMNA, ori, ZonaJuego.COLUMNA, dest);
            }
        }

        for (int ori = 0; ori < NUM_COLUMNAS; ori++) {
            CartaInglesa carta = columnas[ori].verTope();
            if (carta == null) continue;
            if (soloCartasUtiles && columnas[ori].getTamano() == 1) continue;

            for (int dest = 0; dest < NUM_COLUMNAS; dest++) {
                if (ori == dest || !columnas[dest].estaVacia()) continue;
                if (columnas[dest].puedeRecibir(carta))
                    return new Movimiento(carta, ZonaJuego.COLUMNA, ori, ZonaJuego.COLUMNA, dest);
            }
        }
        return null;
    }

    public Movimiento encontrarMovimientoColumnaAReserva() {
        int res = encontrarPrimeraReservaVacia();
        if (res == -1) return null;

        for (int col = 0; col < NUM_COLUMNAS; col++) {
            CartaInglesa carta = columnas[col].verTope();
            CartaInglesa revelada = columnas[col].getCarta(1);
            if (carta == null || revelada == null) continue;

            if (puedeMoverseFuera(revelada, col))
                return new Movimiento(carta, ZonaJuego.COLUMNA, col, ZonaJuego.RESERVA, res);
        }

        for (int col = 0; col < NUM_COLUMNAS; col++) {
            CartaInglesa carta = columnas[col].verTope();
            if (carta != null)
                return new Movimiento(carta, ZonaJuego.COLUMNA, col, ZonaJuego.RESERVA, res);
        }
        return null;
    }

    public int encontrarPrimeraReservaVacia() {
        for (int i = 0; i < NUM_RESERVAS; i++) {
            if (reservas[i].estaVacia()) return i;
        }
        return -1;
    }

    public boolean puedeMoverseFuera(CartaInglesa carta, int columnaExcluida) {
        if (buscarFoundation(carta) != -1) return true;
        for (int col = 0; col < NUM_COLUMNAS; col++) {
            if (col == columnaExcluida || columnas[col].estaVacia()) continue;
            if (columnas[col].puedeRecibir(carta)) return true;
        }
        return false;
    }

    public boolean noQuedanMovimientos() {
        return !juegoGanado() && pedirPista() == null;
    }


    public ColumnaCartas getColumna(int indice) {
        return columnas[indice];
    }

    public Reserva getReserva(int indice) {
        return reservas[indice];
    }

    public Foundation getFoundation(int indice) {
        return foundations[indice];
    }


}
