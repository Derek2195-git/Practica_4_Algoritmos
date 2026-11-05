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

    private ZonaJuego origenTipo;
    private int origenIndice;
    private int origenCantidad;

    public Controlador(Stage stage) {
        this.stage = stage;
        origenTipo = null;
        origenIndice = -1;
        origenCantidad = 1;
        stage.setTitle("Menu principal");
    }

    public void iniciar() {
        VentanaInicio ventanaInicio = new VentanaInicio();
        ventanaInicio.alJugar(this::empezarPartida);
        ventanaInicio.alSalir(Platform::exit);

        mostrar(ventanaInicio.getVentana());
    }

    public void empezarPartida() {
        juego = new Solitario();
        origenTipo = null;
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

    public void mostrar(Parent ventana) {
        Scene escena = new Scene(ventana, 800, 600);
        escena.getStylesheets().add("/estilos.css");

        stage.setScene(escena);
        stage.show();
    }

    private void alHacerClick(ZonaJuego tipo, int indice) {
        ventana.quitarMarcas();
        if (origenTipo != null) ventana.marcarSeleccion(origenTipo, origenIndice, origenCantidad);
        ventana.mostrarMensaje("");



        // Todavía no hay carta elegida: elegir esta (si hay)
        if (origenTipo == null) {
            if (hayCartaEn(tipo, indice)) seleccionar(tipo, indice);
            return;
        }

        // Clic otra vez en la misma carta: quitar la selección
        if (origenTipo == tipo && origenIndice == indice) {
            limpiarSeleccion();
            return;
        }

        // Ya hay carta elegida: intentar moverla aquí
        if (intentarMover(origenTipo, origenIndice, tipo, indice)) {
            limpiarSeleccion();
            despuesDeMover();
        } else if (hayCartaEn(tipo, indice)) {
            seleccionar(tipo, indice);        // no se pudo: se cambia la carta elegida
        } else {
            ventana.mostrarMensaje("Ese movimiento no está permitido");
        }
    }

    private boolean intentarMover(ZonaJuego origenTipo, int origenIndice,
                                  ZonaJuego destinoTipo, int destinoIndice) {
        return switch (origenTipo) {
            case COLUMNA -> switch (destinoTipo) {
                case COLUMNA -> juego.moverColumnaAColumna(origenIndice, destinoIndice);
                case RESERVA -> juego.moverColumnaAReserva(origenIndice, destinoIndice);
                case FOUNDATION -> juego.moverColumnaAFoundation(origenIndice, destinoIndice);
            };
            case RESERVA -> switch (destinoTipo) {
                case COLUMNA -> juego.moverReservaAColumna(origenIndice, destinoIndice);
                case FOUNDATION -> juego.moverReservaAFoundation(origenIndice, destinoIndice);
                case RESERVA ->  false;
            };
            case FOUNDATION -> false;
        };
    }

    private boolean hayCartaEn(ZonaJuego tipo, int indice) {
        return switch (tipo) {
            case COLUMNA -> !juego.getColumna(indice).estaVacia();
            case RESERVA -> !juego.getReserva(indice).estaVacia();
            case FOUNDATION -> false;
        };
    }

    private void seleccionar(ZonaJuego tipo, int indice) {
        ventana.quitarMarcas();
        origenTipo = tipo;
        origenIndice = indice;
        origenCantidad = (tipo == ZonaJuego.COLUMNA)
                ? juego.getColumna(indice).longitudSecuencia() : 1;
        ventana.marcarSeleccion(tipo, indice, origenCantidad);
    }

    private void limpiarSeleccion() {
        origenTipo = null;
        origenIndice = -1;
        ventana.quitarMarcas();
    }

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

    private void terminarPartida(String mensaje) {
        limpiarSeleccion();
        ventana.mostrarMensaje(mensaje);
        ventana.bloquearTablero(true);
        ventana.getSeccionOpciones().setDeshacerHabilitado(false);
        ventana.getSeccionOpciones().setPistaHabilitada(false);
    }

    private void manejarUndo() {
        if (juego.undo()) {
            limpiarSeleccion();
            ventana.redibujar(juego);
            ventana.mostrarMensaje("");
        } else {
            ventana.mostrarMensaje("No hay movimientos por deshacer");
        }
    }

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

    private void manejarSalir() {
        ventana.mostrarAviso("Abandonar partida", "¿Seguro que quieres abandonar la partida actual?");
        if (ventana.getConfirmacionCerrarPartida()) {
            iniciar();
        }
    }
}
