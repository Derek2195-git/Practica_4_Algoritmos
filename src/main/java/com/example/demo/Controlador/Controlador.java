package com.example.demo.Controlador;

import com.example.demo.Modelo.Juego.Movimiento;
import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Modelo.ZonaJuego;
import com.example.demo.Vista.Ventanas.VentanaInicio;
import com.example.demo.Vista.Ventanas.VentanaJuego;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controlador {
    private Stage stage;
    private Solitario juego;
    private VentanaJuego ventana;

    private ZonaJuego origenZona;
    private int origenIndice;
    private int origenCantidad;

    /**
     * Crea el controlador de juego
     * @param stage Ventana a usar para el juego
     */
    public Controlador(Stage stage) {
        this.stage = stage;
        origenZona = null;
        origenIndice = -1;
        origenCantidad = 1;
        stage.setTitle("Menu principal");
    }

    /**
     * Muestra la ventana de inicio de juego
     */
    public void iniciar() {
        VentanaInicio ventanaInicio = new VentanaInicio();
        ventanaInicio.alJugar(this::empezarPartida);
        ventanaInicio.alSalir(Platform::exit);

        mostrar(ventanaInicio.getVentana());
    }

    /**
     * Empieza la partida
     */
    public void empezarPartida() {
        juego = new Solitario();
        origenZona = null;
        origenIndice = -1;
        ventana = new VentanaJuego(juego,
                i -> alHacerClick(ZonaJuego.COLUMNA, i),
                i -> alHacerClick(ZonaJuego.RESERVA, i),
                i -> alHacerClick(ZonaJuego.FOUNDATION, i));

        ventana.getSeccionOpciones().setOnDeshacer(this::manejarUndo);
        ventana.getSeccionOpciones().setOnPista(this::manejarPista);
        ventana.getSeccionOpciones().setOnSalir(this::manejarSalir);

        mostrar(ventana.getRaiz());
    }

    /**
     * Metodo que muestra una ventana
     * @param ventana Ventana a mostrar
     */
    public void mostrar(Parent ventana) {
        Scene escena = new Scene(ventana, 800, 600);
        escena.getStylesheets().add("/estilos.css");

        stage.setScene(escena);
        stage.show();
    }

    /**
     * Metodo que asigna un evento al hacer click en una zona
     * @param zona Zona de juego a marcar
     * @param indice Indice de donde se contenga la carta a marcar
     */
    private void alHacerClick(ZonaJuego zona, int indice) {
        ventana.quitarMarcas();
        if (origenZona != null) ventana.marcarSeleccion(origenZona, origenIndice, origenCantidad);
        ventana.mostrarMensaje("");

        // Elegimos la carta por primera vez
        if (origenZona == null) {
            if (hayCartaEn(zona, indice)) seleccionar(zona, indice);
            return;
        }

        // Quitamos la seleccion si se seleccionó la misma
        if (origenZona == zona && origenIndice == indice) {
            limpiarSeleccion();
            return;
        }

        // Si son diferentes, la intentamos mover
        if (intentarMover(origenZona, origenIndice, zona, indice)) {
            limpiarSeleccion();
            despuesDeMover();
        } else if (hayCartaEn(zona, indice)) {
            seleccionar(zona, indice);        // Si no se pudo, seleccionamos la ultima carta clickeada
        } else {
            ventana.mostrarMensaje("Ese movimiento no está permitido");
        }
    }

    /**
     * Metodo que intenta mover la carta seleccionada
     * @param origenZona Zona de origen de la carta
     * @param origenIndice Indice de origen de la carta
     * @param destinoZona Zona hacia donde ira la carta
     * @param destinoIndice Indice del lugar a donde ira la carta
     * @return Booleano que indica si hubo exito o no
     */
    private boolean intentarMover(ZonaJuego origenZona, int origenIndice,
                                  ZonaJuego destinoZona, int destinoIndice) {
        return switch (origenZona) {
            case COLUMNA -> switch (destinoZona) {
                case COLUMNA -> juego.moverColumnaAColumna(origenIndice, destinoIndice);
                case RESERVA -> juego.moverColumnaAReserva(origenIndice, destinoIndice);
                case FOUNDATION -> juego.moverColumnaAFoundation(origenIndice, destinoIndice);
            };
            case RESERVA -> switch (destinoZona) {
                case COLUMNA -> juego.moverReservaAColumna(origenIndice, destinoIndice);
                case FOUNDATION -> juego.moverReservaAFoundation(origenIndice, destinoIndice);
                case RESERVA ->  false;
            };
            case FOUNDATION -> false;
        };
    }

    /**
     * Metodo que revisa si hay una carta en la posición indicada
     * @param zona Zona a buscar
     * @param indice Indice de la zona donde se buscará
     * @return Booleano que indica si hay una carta en esa zona o no
     */
    private boolean hayCartaEn(ZonaJuego zona, int indice) {
        return switch (zona) {
            case COLUMNA -> !juego.getColumna(indice).estaVacia();
            case RESERVA -> !juego.getReserva(indice).estaVacia();
            case FOUNDATION -> false;
        };
    }

    /**
     * Metodo que selecciona una carta
     * @param zona Zona donde se seleccionará la carta
     * @param indice Indice de la zona a seleccionar
     */
    private void seleccionar(ZonaJuego zona, int indice) {
        ventana.quitarMarcas();
        origenZona = zona;
        origenIndice = indice;
        origenCantidad = (zona == ZonaJuego.COLUMNA)
                ? juego.getColumna(indice).longitudSecuencia() : 1;
        ventana.marcarSeleccion(zona, indice, origenCantidad);
    }

    /**
     * Metodo que limpia/quita la carta seleccionada
     */
    private void limpiarSeleccion() {
        origenZona = null;
        origenIndice = -1;
        ventana.quitarMarcas();
    }

    /**
     * Metodo que revisa el estado del juego despues de mover la carta
     */
    private void despuesDeMover() {
        ventana.redibujar(juego);
        String mensaje;
        if (juego.juegoGanado()) {
            terminarPartida("Victoria!");
        } else if (juego.noQuedanMovimientos()) {
            terminarPartida("Fin del juego.");
            ventana.mostrarAviso("Fin del juego",
                    "Ya no quedan movimientos posibles. Pulsa Salir para volver al menú y empezar otra partida.");
            if (ventana.getConfirmacionCerrarPartida()) {
                iniciar();
            }
        }
    }

    /**
     * Metodo que termina la partida en caso de ganar o perder
     * @param mensaje Mensaje a mostrar
     */
    private void terminarPartida(String mensaje) {
        limpiarSeleccion();
        ventana.mostrarMensaje(mensaje);
        ventana.bloquearTablero(true);
        ventana.getSeccionOpciones().setDeshacerHabilitado(false);
        ventana.getSeccionOpciones().setPistaHabilitada(false);
    }

    /**
     * Metodo que maneja lo referente al undo
     */
    private void manejarUndo() {
        if (juego.undo()) {
            limpiarSeleccion();
            ventana.redibujar(juego);
            ventana.mostrarMensaje("");
        } else {
            ventana.mostrarMensaje("No hay movimientos por deshacer");
        }
    }

    /**
     * Metodo que maneja lo referente a pedir una pista
     */
    private void manejarPista() {
        Movimiento pista = juego.pedirPista();
        if (pista == null) {
            ventana.mostrarMensaje("No hay movimientos posibles");
            return;
        }
        limpiarSeleccion();
        ventana.marcarPista(pista.getZonaOrigen(), pista.getIndiceOrigen());
        ventana.marcarPista(pista.getZonaDestino(), pista.getIndiceDestino());
        ventana.mostrarMensaje("Movimiento posible encontrado");
    }

    /**
     * Metodo que maneja lo referente al salir de la partida
     */
    private void manejarSalir() {
        ventana.mostrarAviso("Abandonar partida", "¿Seguro que quieres abandonar la partida actual?");
        if (ventana.getConfirmacionCerrarPartida()) {
            iniciar();
        }
    }
}
