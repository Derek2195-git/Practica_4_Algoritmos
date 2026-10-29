package com.example.demo.Controlador;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Modelo.ZonaJuego;
import com.example.demo.Vista.Ventanas.VentanaInicio;
import com.example.demo.Vista.Ventanas.VentanaJuego;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controlador {
    private Stage stage;
    private Solitario juego;
    private VentanaJuego ventana;

    private ZonaJuego origenTipo = null;
    private int origenIndice = -1;

    public Controlador(Stage stage) {
        this.stage = stage;
        stage.setTitle("Menu principal");
    }

    public void iniciar() {
        VentanaInicio ventanaInicio = new VentanaInicio();
        ventanaInicio.alJugar(this::empezarPartida);
        ventanaInicio.alSalir(Platform::exit);

        mostrar(ventanaInicio.getVentana());
    }

    public void empezarPartida() {
        juego = new Solitario(true);
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
                case COLUMNA -> juego.columnaAColumna(origenIndice, destinoIndice);
                case RESERVA -> juego.columnaAReserva(origenIndice, destinoIndice);
                case FOUNDATION -> juego.columnaAFoundation(origenIndice);
            };
            case RESERVA -> switch (destinoTipo) {
                case COLUMNA -> juego.reservaAColumna(origenIndice, destinoIndice);
                case FOUNDATION -> juego.reservaAFoundation(origenIndice);
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
        ventana.marcarSeleccion(tipo, indice);
    }

    private void limpiarSeleccion() {
        origenTipo = null;
        origenIndice = -1;
        ventana.quitarMarcas();
    }

    private void despuesDeMover() {
        ventana.redibujar(juego);

        if (juego.juegoGanado()) {
            ventana.mostrarMensaje("Victoria!");
        }
        // TODO: Aqui debe ir lo de revisar cuando no hay movimientos posibles
    }

    private void manejarUndo() {
        // TODO: Deshacer el movimiento
        ventana.mostrarMensaje("Falta de implementar");
    }

    private void manejarPista() {
        // TODO: Pedir la pista
        ventana.mostrarMensaje("Falta de implementar");
    }

    private void manejarSalir() {
        iniciar();
    }
}
