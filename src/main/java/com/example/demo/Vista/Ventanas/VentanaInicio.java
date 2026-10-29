package com.example.demo.Vista.Ventanas;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VentanaInicio {
    private VBox ventana;
    private Button botonJugar;
    private Button botonSalir;

    public VentanaInicio() {
        ventana = new VBox(24);
        botonJugar = new Button("Jugar");
        botonSalir = new Button("Salir");

        ventana.setBackground(Background.fill(Color.rgb(40, 42, 54)));
        ventana.setAlignment(Pos.CENTER);

        Label tituloJuego = new Label("Solitario: \n Eight Off");
        tituloJuego.getStyleClass().add("tituloJuego");

        botonJugar.getStyleClass().add("boton-juego");
        botonSalir.getStyleClass().add("boton-juego");
        VBox botones = new VBox(botonJugar, botonSalir);
        botones.setAlignment(Pos.CENTER);

        ventana.getChildren().addAll(tituloJuego, botones);
    }

    public VBox getVentana() {
        return ventana;
    }

    public void alJugar(Runnable accion) {
        botonJugar.setOnAction(e -> accion.run());
    }

    public void alSalir(Runnable accion) {
        botonSalir.setOnAction(e -> accion.run());
    }
}
