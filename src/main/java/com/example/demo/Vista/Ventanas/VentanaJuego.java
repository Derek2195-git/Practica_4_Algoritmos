package com.example.demo.Vista.Ventanas;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Vista.Secciones.SeccionColumnas;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class VentanaJuego {
    private final BorderPane raiz = new BorderPane();
    private final SeccionColumnas seccionColumnas;

    public VentanaJuego(Solitario juego) {
        seccionColumnas = new SeccionColumnas(juego.NUM_COLUMNAS, this::alClicColumna);
        seccionColumnas.redibujar(juego);

        raiz.setBackground(Background.fill(Color.DARKGREEN));
        raiz.setPadding(new Insets(20));
        raiz.setCenter(seccionColumnas);
    }

    // Prueba temporal: marca con borde amarillo la columna pulsada.
    // Esto lo reemplazará el controlador.
    private void alClicColumna(int indice) {
        System.out.println("Clic en la columna " + indice);
        seccionColumnas.quitarContorno();
        seccionColumnas.getColumna(indice).setSeleccionada(true);
    }

    public BorderPane getRaiz() {
        return raiz;
    }
}
