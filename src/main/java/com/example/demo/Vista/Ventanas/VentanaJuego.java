package com.example.demo.Vista.Ventanas;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Modelo.ZonaJuego;
import com.example.demo.Vista.Secciones.SeccionColumnas;
import com.example.demo.Vista.Secciones.SeccionFoundation;
import com.example.demo.Vista.Secciones.SeccionOpciones;
import com.example.demo.Vista.Secciones.SeccionReserva;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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

    public VentanaJuego(Solitario juego,
                        IntConsumer clickColumna,
                        IntConsumer clickReserva,
                        IntConsumer clickFoundation) {
        seccionColumnas = new SeccionColumnas(juego.NUM_COLUMNAS, clickColumna);
        seccionReserva = new SeccionReserva(juego.NUM_RESERVAS, clickReserva);
        seccionFoundation = new SeccionFoundation(juego.NUM_FOUNDATIONS, clickFoundation);
        seccionOpciones = new SeccionOpciones();

        VBox centro = new VBox(24, seccionReserva, seccionColumnas);

        raiz.setBackground(Background.fill(Color.DARKGREEN));
        raiz.setPadding(new Insets(20));
        raiz.setLeft(seccionFoundation);
        raiz.setCenter(centro);
        raiz.setBottom(seccionOpciones);

        BorderPane.setMargin(seccionFoundation, new Insets(0, 20, 0, 0));
        BorderPane.setMargin(seccionOpciones, new Insets(20, 0, 0, 0));

        redibujar(juego);
    }

    public void redibujar(Solitario juego) {
        seccionColumnas.redibujar(juego);
        seccionReserva.actualizar(juego);
        seccionFoundation.redibujar(juego);
    }

    public void quitarMarcas() {
        seccionReserva.quitarMarcas();
        seccionColumnas.quitarContorno();
    }

    public void marcarSeleccion(ZonaJuego tipo, int indice) {
        switch (tipo) {
            case COLUMNA -> seccionColumnas.getColumna(indice).setSeleccionada(true);
            case RESERVA -> seccionReserva.getCelda(indice).setSeleccionada(true);
            default -> {}
        }
    }

    public void mostrarMensaje(String texto) {
        seccionOpciones.mostrarMensaje(texto);
    }

    public void mostrarAviso(String titulo, String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(texto);
        alerta.show();
    }

    public SeccionOpciones getSeccionOpciones() {
        return seccionOpciones;
    }

    public BorderPane getRaiz() {
        return raiz;
    }
}
