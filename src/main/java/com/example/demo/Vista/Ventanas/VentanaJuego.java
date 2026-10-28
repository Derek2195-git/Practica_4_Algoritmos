package com.example.demo.Vista.Ventanas;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Vista.Secciones.SeccionColumnas;
import com.example.demo.Vista.Secciones.SeccionFoundation;
import com.example.demo.Vista.Secciones.SeccionReserva;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VentanaJuego {
    private final BorderPane raiz = new BorderPane();
    private SeccionReserva seccionReserva;
    private SeccionColumnas seccionColumnas;
    private SeccionFoundation seccionFoundation;

    public VentanaJuego(Solitario juego) {
        seccionColumnas = new SeccionColumnas(juego.NUM_COLUMNAS, this::alClicColumna);
        seccionReserva = new SeccionReserva(juego.NUM_RESERVAS, this::alClicReserva);
        seccionFoundation = new SeccionFoundation(juego.NUM_FOUNDATIONS, this::alClickFoundation);

        seccionColumnas.redibujar(juego);
        seccionReserva.actualizar(juego);
        seccionFoundation.redibujar(juego);

        VBox centro = new VBox(24, seccionReserva, seccionColumnas);

        raiz.setBackground(Background.fill(Color.DARKGREEN));
        raiz.setPadding(new Insets(20));
        raiz.setLeft(seccionFoundation);
        raiz.setCenter(centro);
        BorderPane.setMargin(seccionFoundation, new Insets(0, 20, 0,0));
    }

    // Pruebas temporales: las reemplazará el controlador
    private void alClicColumna(int indice) {
        System.out.println("Clic en la columna " + indice);
        quitarMarcas();
        seccionColumnas.getColumna(indice).setSeleccionada(true);
    }

    private void alClicReserva(int indice) {
        System.out.println("Clic en la reserva " + indice);
        quitarMarcas();
        seccionReserva.getCelda(indice).setSeleccionada(true);
    }

    private void alClickFoundation(int indice) {
        System.out.println("Clic en la foundation " + indice);
        quitarMarcas();
    }

    private void quitarMarcas() {
        seccionReserva.quitarMarcas();
        seccionColumnas.quitarContorno();
    }

    public BorderPane getRaiz() {
        return raiz;
    }
}
