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

    /**
     * Genera la seccion de opciones
     */
    public SeccionOpciones() {
        super(12);
        botonDeshacer = new Button("Deshacer movimiento");
        botonPista = new Button("Pedir pista");
        botonSalir = new Button("Salir al menu principal");
        labelJuego = new Label();

        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(0, 20, 0, 20));
        setPrefHeight(60);
        setMinHeight(60);

        getStyleClass().add("barra-opciones");
        botonDeshacer.getStyleClass().add("boton-juego");
        botonPista.getStyleClass().add("boton-juego");
        botonSalir.getStyleClass().add("boton-juego");
        labelJuego.getStyleClass().add("mensaje");

        Region relleno = new Region();
        HBox.setHgrow(relleno, Priority.ALWAYS);
        getChildren().addAll(botonDeshacer, botonPista, botonSalir, labelJuego);
    }

    /**
     * Pone un evento al boton de Deshacer
     * @param accion Accion a realizar
     */
    public void setOnDeshacer(Runnable accion) {
        botonDeshacer.setOnAction(e -> accion.run());
    }

    /**
     * Pone un evento al boton de pista
     * @param accion Accion a realizar
     */
    public void setOnPista(Runnable accion) {
        botonPista.setOnAction(e -> accion.run());
    }

    /**
     * Pone un evento al boton de salir
     * @param accion Accion a realizar
     */
    public void setOnSalir(Runnable accion) {
        botonSalir.setOnAction(e -> accion.run());
    }

    /**
     * Muestra un mensaje en la sección de opciones
     * @param texto Texto a mostrar
     */
    public void mostrarMensaje(String texto) {
        labelJuego.setText(texto);
    }

    /**
     * Habilita o deshabilita el boton de deshacer
     * @param habilitado Habilitador del boton
     */
    public void setDeshacerHabilitado(boolean habilitado) {
        botonDeshacer.setDisable(!habilitado);
    }

    /**
     * Habilita o deshabilita el de pista
     * @param habilitada Habilitador
     */
    public void setPistaHabilitada(boolean habilitada) {
        botonPista.setDisable(!habilitada);
    }
}
