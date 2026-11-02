package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import com.example.demo.Modelo.Baraja.Mazo;
import com.example.demo.Modelo.Listas.ListaSimple;
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

    /**
     * Inicializa el juego, con sus foundations, tableaus y reservas
     */
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

    /**
     * Metodo que reparte 8 cartas en cada tableau, y las 4 restantes en la reserva
     */
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

    // Clases para realizar los movimientos

    public boolean moverColumnaAColumna(int origen, int destino) {
        // Obtenemos la cantidad de cartas a mover
        int cantidad = cantidadAMover(origen, destino);
        if (cantidad == 0) return false;

        // Obtenemos la primera carta en la secuencia, realizamos el movimiento y lo guardamos en la "pila"
        CartaInglesa primeraCartaSecuencia = columnas[origen].getCarta(cantidad - 1);
        moverCartas(columnas[origen], columnas[destino], cantidad);
        historialMovimientos.agregarMovimiento(new Movimiento(
                primeraCartaSecuencia, ZonaJuego.COLUMNA, origen, ZonaJuego.COLUMNA, destino, cantidad
        ));
        return true;
    }

    public boolean moverColumnaAReserva(int col, int res) {
        CartaInglesa carta = columnas[col].verTope();
        if (carta == null || !reservas[res].agregarCarta(carta)) return false;
        columnas[col].quitarCarta();
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.COLUMNA, col, ZonaJuego.RESERVA, res));
        return true;
    }

    public boolean moverReservaAColumna(int res, int col) {
        CartaInglesa carta = reservas[res].getCarta();
        if (carta == null || !columnas[col].agregarCarta(carta)) return false;
        reservas[res].quitarCarta();
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.RESERVA, res, ZonaJuego.COLUMNA, col));
        return true;
    }

    public boolean moverColumnaAFoundation(int col, int foun) {
        CartaInglesa carta = columnas[col].verTope();
        if (carta == null || foun < 0 || !foundations[foun].agregarCarta(carta)) return false;
        columnas[col].quitarCarta();
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.COLUMNA, col, ZonaJuego.FOUNDATION, foun));
        return true;
    }

    public boolean moverReservaAFoundation(int res, int foun) {
        CartaInglesa carta = reservas[res].getCarta();
        if (carta == null || foun < 0 || !foundations[foun].agregarCarta(carta)) return false;
        reservas[res].quitarCarta();
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.RESERVA, res, ZonaJuego.FOUNDATION, foun));
        return true;
    }


    /**
     * Metodo que permite buscar si la foundation puede agarrar la carta ingresada
     * @param carta Carta a revisar con los foundations
     * @return indice del foundation que acepta la carta, o -1 en caso de que no se pueda
     */
    private int buscarFoundation(CartaInglesa carta) {
        for (int i = 0; i < foundations.length; i++) {
            if (foundations[i].puedeRecibirCarta(carta)) return i;
        }
        return -1;
    }

    /**
     * Metodo que revisa si el jugador
     * ganó el juego al poner todas las cartas en cada foundation
     * @return Booleano que revisa si el jugador ganó
     */
    public boolean juegoGanado() {
        for (Foundation f : foundations) {
            if (!f.estaCompleta()) return false;
        }
        return true;
    }

    /**
     * Metodo que contiene la lógica relacionada con el deshacer de un movimiento
     * @return Booleano que indica si el undo se pudo realizar o no
     */
    public boolean undo() {
        Movimiento movimiento = historialMovimientos.sacarMovimiento();
        if (movimiento == null) return false;

        if (movimiento.getZonaOrigen() == ZonaJuego.COLUMNA &&
        movimiento.getZonaDestino() == ZonaJuego.COLUMNA) {
            moverCartas(columnas[movimiento.getIndiceDestino()],
                    columnas[movimiento.getIndiceOrigen()], movimiento.getCantidad());
            return true;
        }

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

    /**
     * Metodo que contiene la lógica relacionada con pedir una pista
     * @return
     */
    public Movimiento pedirPista() {
        ListaSimple<Movimiento> posibles = movimientosPosibles();
        Movimiento mejor = null;
        int mejorPuntos = -1;
        for (int i = 0; i < posibles.size(); i++) {
            Movimiento m = posibles.obtenerX(i);
            int puntos = puntuar(m);
            if (puntos > mejorPuntos) {          // ">" estricto: en empate gana el primero
                mejor = m;
                mejorPuntos = puntos;
            }
        }
        return mejor;                            // null si no hay ningún movimiento
    }

    private int puntuar(Movimiento m) {
        if (m.getZonaDestino() == ZonaJuego.FOUNDATION) return 100;

        if (m.getZonaDestino() == ZonaJuego.COLUMNA) {
            boolean destinoVacio = columnas[m.getIndiceDestino()].estaVacia();
            if (m.getZonaOrigen() == ZonaJuego.RESERVA) return destinoVacio ? 30 : 90;
            if (!destinoVacio) return 80;
            boolean mueveTodo = m.getCantidad() == columnas[m.getIndiceOrigen()].getTamano();
            return mueveTodo ? 20 : 70;          // pasar toda una columna a otra vacía no cambia nada
        }

        // Destino reserva: siempre sale de una columna
        int col = m.getIndiceOrigen();
        CartaInglesa revelada = columnas[col].getCarta(1);
        if (revelada != null && puedeMoverseFuera(revelada, col)) return 60;   // descubre una carta útil
        if (!estaSobreSuPadre(col)) return 50;                                  // no rompe una secuencia
        return 40;
    }

    public void moverCartas(ColumnaCartas colOrigen, ColumnaCartas colDestino, int cantidad) {
        ListaSimple<CartaInglesa> temp = new ListaSimple<>();
        for (int i = 0; i < cantidad; i++) {
            temp.insertaInicio(colOrigen.quitarCarta());
        }
        while (!temp.isEmpty()) {
            colDestino.colocarCarta(temp.eliminaInicio());
        }
    }

    private int cantidadAMover(int origen, int destino) {
        if (origen == destino) return 0;
        int maximo = columnas[origen].longitudSecuencia();
        for (int i = maximo; i >= 1; i--) {
            CartaInglesa primeraCartaSecuencia = columnas[origen].getCarta(i - 1);
            if (columnas[destino].puedeRecibir(primeraCartaSecuencia)) return i;
        }
        return 0;
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

    private ListaSimple<Movimiento> todosLosMovimientos() {
        ListaSimple<Movimiento> lista = new ListaSimple<>();

        // A foundation, desde columnas y desde reserva
        for (int col = 0; col < NUM_COLUMNAS; col++) {
            CartaInglesa carta = columnas[col].verTope();
            if (carta == null) continue;
            int f = buscarFoundation(carta);
            if (f != -1) lista.insertaFin(new Movimiento(carta, ZonaJuego.COLUMNA, col, ZonaJuego.FOUNDATION, f));
        }
        for (int res = 0; res < NUM_RESERVAS; res++) {
            CartaInglesa carta = reservas[res].getCarta();
            if (carta == null) continue;
            int f = buscarFoundation(carta);
            if (f != -1) lista.insertaFin(new Movimiento(carta, ZonaJuego.RESERVA, res, ZonaJuego.FOUNDATION, f));
        }

        // De reserva a columna
        for (int res = 0; res < NUM_RESERVAS; res++) {
            CartaInglesa carta = reservas[res].getCarta();
            if (carta == null) continue;
            for (int col = 0; col < NUM_COLUMNAS; col++) {
                if (columnas[col].puedeRecibir(carta)) {
                    lista.insertaFin(new Movimiento(carta, ZonaJuego.RESERVA, res, ZonaJuego.COLUMNA, col));
                }
            }
        }

        // De columna a columna (secuencias incluidas)
        for (int ori = 0; ori < NUM_COLUMNAS; ori++) {
            for (int dest = 0; dest < NUM_COLUMNAS; dest++) {
                int k = cantidadAMover(ori, dest);
                if (k > 0) {
                    lista.insertaFin(new Movimiento(columnas[ori].getCarta(k - 1),
                            ZonaJuego.COLUMNA, ori, ZonaJuego.COLUMNA, dest, k));
                }
            }
        }

        // De columna a reserva: solo a la primera celda libre (todas las vacías son equivalentes)
        int celda = encontrarPrimeraReservaVacia();
        if (celda != -1) {
            for (int col = 0; col < NUM_COLUMNAS; col++) {
                CartaInglesa carta = columnas[col].verTope();
                if (carta != null) {
                    lista.insertaFin(new Movimiento(carta, ZonaJuego.COLUMNA, col, ZonaJuego.RESERVA, celda));
                }
            }
        }
        return lista;
    }

    // Para evitar que al seleccionar una carta a la reserva y de la reserva
    // a la misma posición no cuente como un movimiento real
    public boolean esInverso(Movimiento a, Movimiento b) {
        return a != null && b != null
                && (a.getZonaOrigen() == b.getZonaDestino())
                && (a.getIndiceOrigen() == b.getIndiceDestino())
                && (a.getZonaDestino() == b.getZonaOrigen())
                && (a.getIndiceDestino() == b.getIndiceOrigen());
    }

    public boolean noQuedanMovimientos() {
        return !juegoGanado() && movimientosPosibles().isEmpty();
    }

    private ListaSimple<Movimiento> movimientosPosibles() {
        Movimiento ultimo = historialMovimientos.verUltimo();
        ListaSimple<Movimiento> todos = todosLosMovimientos();
        ListaSimple<Movimiento> posibles = new ListaSimple<>();
        for (int i = 0; i < todos.size(); i++) {
            Movimiento m = todos.obtenerX(i);
            if (!esInverso(m, ultimo)) posibles.insertaFin(m);
        }
        return posibles;
    }

    private boolean estaSobreSuPadre(int col) {
        CartaInglesa carta = columnas[col].verTope();
        CartaInglesa debajo = columnas[col].getCarta(1);
        return carta != null && debajo != null
                && carta.tieneElMismoPalo(debajo) && carta.esLaSiguiente(debajo);
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
