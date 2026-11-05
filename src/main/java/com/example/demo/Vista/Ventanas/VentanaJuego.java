package com.example.demo.Vista.Ventanas;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Modelo.ZonaJuego;
import com.example.demo.Vista.Secciones.SeccionColumnas;
import com.example.demo.Vista.Secciones.SeccionFoundation;
import com.example.demo.Vista.Secciones.SeccionOpciones;
import com.example.demo.Vista.Secciones.SeccionReserva;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.function.IntConsumer;

public class VentanaJuego {
    private final BorderPane raiz = new BorderPane();
    private SeccionReserva seccionReserva;
    private SeccionColumnas seccionColumnas;
    private SeccionFoundation seccionFoundation;
    private SeccionOpciones seccionOpciones;
    private boolean confirmacionCerrarPartida = false;

    public VentanaJuego(Solitario juego,
                        IntConsumer clickColumna,
                        IntConsumer clickReserva,
                        IntConsumer clickFoundation) {
        seccionColumnas = new SeccionColumnas(juego.NUM_COLUMNAS, clickColumna);
        seccionReserva = new SeccionReserva(juego.NUM_RESERVAS, clickReserva);
        seccionFoundation = new SeccionFoundation(juego.NUM_FOUNDATIONS, clickFoundation);
        seccionOpciones = new SeccionOpciones();

        VBox centro = new VBox(24, seccionReserva, seccionColumnas);

        BorderPane tablero = new BorderPane();
        tablero.setPadding(new Insets(20));
        tablero.setLeft(seccionFoundation);
        tablero.setCenter(centro);
        BorderPane.setMargin(seccionFoundation, new Insets(0, 20, 0, 0));


        raiz.setTop(seccionOpciones);
        raiz.setCenter(tablero);
        raiz.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());
        redibujar(juego);
    }

    public void redibujar(Solitario juego) {
        seccionColumnas.redibujar(juego);
        seccionReserva.actualizar(juego);
        seccionFoundation.redibujar(juego);
    }

    public void quitarMarcas() {
        seccionFoundation.quitarContorno();
        seccionReserva.quitarContorno();
        seccionColumnas.quitarContorno();
    }

    public void marcarSeleccion(ZonaJuego tipo, int indice, int cantidad) {
        switch (tipo) {
            case COLUMNA -> seccionColumnas.getColumna(indice).setSeleccionada(true, cantidad);
            case RESERVA -> seccionReserva.getCelda(indice).setSeleccionada(true);
            default -> {}
        }
    }

    public void mostrarMensaje(String texto) {
        seccionOpciones.mostrarMensaje(texto);
    }

    public void mostrarAviso(String titulo, String texto) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(texto);
        Button botonConfirmar = (Button) alerta.getDialogPane().lookupButton(ButtonType.OK);
        botonConfirmar.setText("Salir del juego");
        botonConfirmar.setOnAction(e-> confirmacionCerrarPartida = true);
        Button botonNegar = (Button) alerta.getDialogPane().lookupButton(ButtonType.CANCEL);
        botonNegar.setText("Quedarse en la partida actual");

        alerta.showAndWait();
    }

    public boolean getConfirmacionCerrarPartida() {
        return confirmacionCerrarPartida;
    }

    public void marcarPista(ZonaJuego zona, int indice) {
        switch (zona) {
            case COLUMNA -> seccionColumnas.getColumna(indice).setPista(true);
            case RESERVA -> seccionReserva.getCelda(indice).setPista(true);
            case FOUNDATION -> seccionFoundation.getFoundation(indice).setPista(true);
        }
    }

    public void bloquearTablero(boolean habilitado) {
        seccionReserva.setDisable(habilitado);
        seccionFoundation.setDisable(habilitado);
    }

    public SeccionOpciones getSeccionOpciones() {
        return seccionOpciones;
    }

    public BorderPane getRaiz() {
        return raiz;
    }
}
