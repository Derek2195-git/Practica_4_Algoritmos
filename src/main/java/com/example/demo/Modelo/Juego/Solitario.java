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
    private final int CANTIDAD_UNDOS = 1;
    private final int CANTIDAD_PISTAS = 3;
    private int undosRestantes = CANTIDAD_UNDOS;
    private int pistasRestantes = CANTIDAD_PISTAS;

    private ColumnaCartas[] columnas;
    private Reserva[] reservas;
    private Foundation[] foundations;
    private Mazo mazo;
    private ContenedorMovimiento historialMovimientos;

    public Solitario() {
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
            columnas[i] = new ColumnaCartas(true);
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

    /**
     * Mueve una cantidad de cartas de una columna a otra
     * @param origen Indice de la columna de origen
     * @param destino Indice de la columna de destino
     * @return Booleano que indica si el movimiento fue exitoso
     */
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

    /**
     * Mueve la ultima carta de un tableau a una reserva disponible
     * @param col
     * @param res
     * @return Booleano que indica si el movimiento fue exitoso
     */
    public boolean moverColumnaAReserva(int col, int res) {
        // Obtenemos la ultima carta de la columna
        CartaInglesa carta = columnas[col].verTope();
        // Revisamos si la carta se ingresó correctamente y luego se quita de la columna si si
        if (carta == null || !reservas[res].agregarCarta(carta)) return false;
        columnas[col].quitarCarta();
        // Agregamos el movimiento realizado pal undo
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.COLUMNA, col, ZonaJuego.RESERVA, res));
        return true;
    }

    /**
     * Mueve la carta de una reserva a la ultima de un tableau
     * @param res Indice de la reserva donde se encuentra la carta
     * @param col Columna de destino donde se insertará
     * @return Booleano que indica si el movimiento fue exitoso
     */
    public boolean moverReservaAColumna(int res, int col) {
        // Sacamos la carta de la reserva que indicamos, revisamos que se haya ingresado exitosamente
        CartaInglesa carta = reservas[res].getCarta();
        if (carta == null || !columnas[col].agregarCarta(carta)) return false;
        // Quitamos la carta de la reserva en caso de que si y metemos el movimiento a la "pila"
        reservas[res].quitarCarta();
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.RESERVA, res, ZonaJuego.COLUMNA, col));
        return true;
    }

    /**
     * Mueve la ultima carta de una columna a una foundation
     * @param col Indice de la columna de la carta a retirar
     * @param foun Indice del foundation donde meteremos la carta
     * @return Booleano que indica si el movimiento fue exitoso
     */
    public boolean moverColumnaAFoundation(int col, int foun) {
        // Sacamos la ultima carta de la columna
        CartaInglesa carta = columnas[col].verTope();
        // Revisamos si el movimiento no es posible o exitoso y en caso de que si, eliminamos la carta de la columna
        if (carta == null || foun < 0 || !foundations[foun].agregarCarta(carta)) return false;
        columnas[col].quitarCarta();
        historialMovimientos.agregarMovimiento(new Movimiento(carta,
                ZonaJuego.COLUMNA, col, ZonaJuego.FOUNDATION, foun));
        return true;
    }

    /**
     * Mueve una carta de una reserva a una foundation
     * @param res Indice de la reserva donde se ubica la carta
     * @param foun Indice del foundation donde se insertará la carta
     * @return Booleano que indica si el movimiento fue exitoso
     */
    public boolean moverReservaAFoundation(int res, int foun) {
        // Sacamos la carta de la reserva, revisamos que el movimiento haya sido exitoso
        CartaInglesa carta = reservas[res].getCarta();
        if (carta == null || foun < 0 || !foundations[foun].agregarCarta(carta)) return false;
        reservas[res].quitarCarta();
        // Quitamos la carta de la reserva y guardamos el movimiento
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
        if (undosRestantes == 0) return false;
        // Obtenemos el ultimo movimiento de la "pila" y revisamos que exista
        Movimiento movimiento = historialMovimientos.sacarMovimiento();
        if (movimiento == null) return false;

        // Si el movimiento trata de mover columnas tenemos que mover todas las cartas afectadas originalmente
        if (movimiento.getZonaOrigen() == ZonaJuego.COLUMNA &&
        movimiento.getZonaDestino() == ZonaJuego.COLUMNA) {
            moverCartas(columnas[movimiento.getIndiceDestino()],
                    columnas[movimiento.getIndiceOrigen()], movimiento.getCantidad());
            return true;
        }

        // Para lo demás, usamos un switch para quitar la carta movida
        switch (movimiento.getZonaDestino()) {
            case COLUMNA -> columnas[movimiento.getIndiceDestino()].quitarCarta();
            case RESERVA -> reservas[movimiento.getIndiceDestino()].quitarCarta();
            case FOUNDATION -> foundations[movimiento.getIndiceDestino()].quitarCarta();
        }
        // Y otro para ingresar la carta original
        switch (movimiento.getZonaOrigen()) {
            case COLUMNA -> columnas[movimiento.getIndiceOrigen()]
                    .colocarCarta(movimiento.getCarta());
            case RESERVA -> reservas[movimiento.getIndiceOrigen()]
                    .colocarCarta(movimiento.getCarta());
            case null, default -> {}
        }

        undosRestantes--;

        return true;
    }

    public Movimiento pista() {
        if (pistasRestantes == 0) return null;
        Movimiento pista = pedirPista();
        if (pista != null) --pistasRestantes;
        return pista;
    }

    /**
     * Metodo que contiene la lógica relacionada con pedir una pista
     * @return Booleano que indica si la pista fue pedida con exito
     */
    public Movimiento pedirPista() {
        ListaSimple<Movimiento> posibles = movimientosPosibles();
        // Obtenemos una lista de todos los movimientos posibles e intentamos buscar cual es el "mejor"
        Movimiento mejor = null;
        int mejorPuntos = -1;
        for (int i = 0; i < posibles.size(); i++) {
            Movimiento movimiento = posibles.obtenerX(i);
            int puntos = puntuar(movimiento);
            // Si detectamos un movimiento que sea mejor que el actual, lo cambiamos
            if (puntos > mejorPuntos) {
                mejor = movimiento;
                mejorPuntos = puntos;
            }
        }
        // Al acabar el ciclo devolvemos el movimiento, independientemente si encontramos uno o no
        return mejor;
    }

    /**
     * Revisa un movimiento y le asigna un puntaje del 0 al 100, según su prioridad
     * @param mov Movimiento a analizar
     * @return Valor númerico que indica la prioridad del movimiento, en una escala del 0 al 100
     */
    private int puntuar(Movimiento mov) {
        // La foundation es lo más importante
        if (mov.getZonaDestino() == ZonaJuego.FOUNDATION) return 100;
        // De ahi siguen las columnas, si es a una reserva vacia no es tan importante como a otro tableau
        if (mov.getZonaDestino() == ZonaJuego.COLUMNA) {
            boolean destinoVacio = columnas[mov.getIndiceDestino()].estaVacia();
            if (mov.getZonaOrigen() == ZonaJuego.RESERVA) return destinoVacio ? 30 : 90;
            // Si pasamos de una reserva con una carta a un otra carta del tableau, es más importante que lo anterior
            // pero menos que de un tableau a otro
            if (!destinoVacio) return 80;
            // Si estamos moviendo todas las cartas del tableau, es menos imporante que lo anterior
            boolean mueveTodo = mov.getCantidad() == columnas[mov.getIndiceOrigen()].getTamano();
            // Si pasamos una secuencia a una columna vacia, es de lo menos prioritario del juego
            return mueveTodo ? 20 : 70;
        }


        int col = mov.getIndiceOrigen();
        CartaInglesa revelada = columnas[col].getCarta(1);
        // Si la carta que movimos a la reserva es "util", adquiere bastante prioridad
        if (revelada != null && puedeMoverseFuera(revelada, col)) return 60;
        // Si dicha carta a mover no rompe con la secuencia, le damos menos prioridad
        if (!estaSobreSuPadre(col)) return 50;
        // En caso de que no haya sido ninguno de estos movimientos, le damos mucha menos prioridad
        return 40;
    }

    /**
     * Metodo que mueve una cantidad de cartas de un tableau a otro
     * @param colOrigen Indice del tableau donde se ubica la secuencia
     * @param colDestino Indice del tableau a donde moveremos dicha secuencia
     * @param cantidad Cantidad de cartas que contiene la secuencia a mover
     */
    public void moverCartas(ColumnaCartas colOrigen, ColumnaCartas colDestino, int cantidad) {
        ListaSimple<CartaInglesa> temp = new ListaSimple<>();
        // Primero se meten las cartas a una lista temporal, y luego se ingresan a la columna de destino
        for (int i = 0; i < cantidad; i++) {
            temp.insertaInicio(colOrigen.quitarCarta());
        }
        while (!temp.isEmpty()) {
            colDestino.colocarCarta(temp.eliminaInicio());
        }
    }

    /**
     * Metodo que calcula la cantidad de cartas a mover en el tablero
     * @param origen Indice de donde se ubica la columna a mover
     * @param destino Indice de a que columna moveremos las cartas
     * @return Valor que indica la cantidad de cartas que se moverán
     */
    private int cantidadAMover(int origen, int destino) {
        if (origen == destino) return 0;
        int maximo = columnas[origen].longitudSecuencia();
        for (int i = maximo; i >= 1; i--) {
            CartaInglesa primeraCartaSecuencia = columnas[origen].getCarta(i - 1);
            if (columnas[destino].puedeRecibir(primeraCartaSecuencia)) return i;
        }
        return 0;
    }

    /**
     * Metodo rapido que nos sirve para ubicar una carta en la primera reserva vacia que se encuentre
     * @return Indice de la reserva a ubicar una carta, o -1 en caso de que todas estén vacias
     */
    public int encontrarPrimeraReservaVacia() {
        for (int i = 0; i < NUM_RESERVAS; i++) {
            if (reservas[i].estaVacia()) return i;
        }
        return -1;
    }

    /**
     * Metodo que sirve para
     * @param carta Carta a mover
     * @param columnaExcluida
     * @return
     */
    public boolean puedeMoverseFuera(CartaInglesa carta, int columnaExcluida) {
        if (buscarFoundation(carta) != -1) return true;
        for (int col = 0; col < NUM_COLUMNAS; col++) {
            if (col == columnaExcluida || columnas[col].estaVacia()) continue;
            if (columnas[col].puedeRecibir(carta)) return true;
        }
        return false;
    }

    /**
     * Metodo que revisa todos los movimientos que pueden hacerse en el juego
     * @return Lista con todos los movimientos que hay actualmente en el juego
     */
    private ListaSimple<Movimiento> todosLosMovimientos() {
        ListaSimple<Movimiento> lista = new ListaSimple<>();

        // Movimientos posibles desde las columnas y reservas a la foundation
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

        // Movimientos posibles de reservas a columnas
        for (int res = 0; res < NUM_RESERVAS; res++) {
            CartaInglesa carta = reservas[res].getCarta();
            if (carta == null) continue;
            for (int col = 0; col < NUM_COLUMNAS; col++) {
                if (columnas[col].puedeRecibir(carta)) {
                    lista.insertaFin(new Movimiento(carta, ZonaJuego.RESERVA, res, ZonaJuego.COLUMNA, col));
                }
            }
        }

        // Movimientos posibles de columna a columna
        for (int ori = 0; ori < NUM_COLUMNAS; ori++) {
            for (int dest = 0; dest < NUM_COLUMNAS; dest++) {
                int k = cantidadAMover(ori, dest);
                if (k > 0) {
                    lista.insertaFin(new Movimiento(columnas[ori].getCarta(k - 1),
                            ZonaJuego.COLUMNA, ori, ZonaJuego.COLUMNA, dest, k));
                }
            }
        }

        // De columna a reserva, pero aqui solamente se revisa la primera que esta libre
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

    /**
     * Metodo hecho para evitar que al seleccionar una carta a la reserva
     * y de la reserva a la misma posición no cuente como un movimiento real
     * @param a Movimiento actual
     * @param b Movimiento anterior
     * @return booleano que indica si se realizó esencialmente el mismo movimiento
     */
    public boolean esInverso(Movimiento a, Movimiento b) {
        return a != null && b != null
                && (a.getZonaOrigen() == b.getZonaDestino())
                && (a.getIndiceOrigen() == b.getIndiceDestino())
                && (a.getZonaDestino() == b.getZonaOrigen())
                && (a.getIndiceDestino() == b.getIndiceOrigen());
    }

    /**
     * Metodo que revisa si ya no hay movimientos posibles
     * @return Booleano que indica si ya no quedan movimientos posibles en este turno
     */
    public boolean noQuedanMovimientos() {
        return !juegoGanado() && movimientosPosibles().isEmpty();
    }

    /**
     * Metodo que obtiene una lista de movimientos posibles en el turno dado
     * @return Lista con todos los movimientos posibles
     */
    private ListaSimple<Movimiento> movimientosPosibles() {
        // Creamos las listas para los movimientos, y obtenemos el ultimo movimiento realizado
        Movimiento ultimo = historialMovimientos.verUltimo();
        ListaSimple<Movimiento> todos = todosLosMovimientos();
        ListaSimple<Movimiento> posibles = new ListaSimple<>();
        // Obtenemos un movimiento, y si no es esencialmente el mismo, se inserta a la lista de posibles
        for (int i = 0; i < todos.size(); i++) {
            Movimiento m = todos.obtenerX(i);
            if (!esInverso(m, ultimo)) posibles.insertaFin(m);
        }
        return posibles;
    }

    /**
     * Metodo que revisa si una carta es parte de una secuencia
     * @param col Columna donde se ubica la carta
     * @return Booleano que indica si la carta es parte de una secuencia
     */
    private boolean estaSobreSuPadre(int col) {
        // Obtenemos la carta actual y la anterior
        CartaInglesa carta = columnas[col].verTope();
        CartaInglesa debajo = columnas[col].getCarta(1);
        // Revisamos si ambas tienen el mismo palo y si la primera es la siguiente de la anterior
        return carta != null && debajo != null
                && carta.tieneElMismoPalo(debajo) && carta.esLaSiguiente(debajo);
    }

    /**
     * Getter de un tableau del juego
     * @param indice Indice de la columna que se quiere obtener
     * @return Tableau deseado, con las cartas o atributos que posea
     */
    public ColumnaCartas getColumna(int indice) {
        return columnas[indice];
    }

    /**
     * Getter de una reserva del juego
     * @param indice Indice de la reserva que queremos obtener
     * @return Reserva deseada, con la carta que contenga
     */
    public Reserva getReserva(int indice) {
        return reservas[indice];
    }

    /**
     * Getter de una foundation del juego
     * @param indice Indice de la foundation buscada
     * @return Foundation deseada, con las cartas que pueda o no contener
     */
    public Foundation getFoundation(int indice) {
        return foundations[indice];
    }

    public int getPistasRestantes() {
        return pistasRestantes;
    }

    public int getUndosRestantes() {
        return undosRestantes;
    }
}
