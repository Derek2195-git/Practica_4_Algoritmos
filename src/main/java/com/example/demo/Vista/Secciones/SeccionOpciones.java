package com.example.demo.Vista.Secciones;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class SeccionOpciones extends HBox {
    private Button botonDeshacer;
    private Button botonPista;
    private Button botonSalir;
    private Label labelJuego;

    public SeccionOpciones() {
        super(12);
        botonDeshacer = new Button("Deshacer movimiento");
        botonPista = new Button("Pedir pista");
        botonSalir = new Button("Salir al menu principal");
        labelJuego = new Label();

        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(8, 20, 8, 20));

        getStyleClass().add("barra-opciones");
        botonDeshacer.getStyleClass().add("boton-juego");
        botonPista.getStyleClass().add("boton-juego");
        botonSalir.getStyleClass().add("boton-juego");
        labelJuego.getStyleClass().add("mensaje");

        Region relleno = new Region();
        HBox.setHgrow(relleno, Priority.ALWAYS);
        getChildren().addAll(botonDeshacer, botonPista, botonSalir, labelJuego);
    }

    public void setOnDeshacer(Runnable accion) {
        botonDeshacer.setOnAction(e -> accion.run());
    }

    public void setOnPista(Runnable accion) {
        botonPista.setOnAction(e -> accion.run());
    }

    public void setOnSalir(Runnable accion) {
        botonSalir.setOnAction(e -> accion.run());
    }

    public void mostrarMensaje(String texto) {
        labelJuego.setText(texto);
    }
}
