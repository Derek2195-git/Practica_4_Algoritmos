package com.example.demo.Vista.Ventanas;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import com.example.demo.Modelo.Baraja.Palo;
import com.example.demo.Vista.ObjetosGUI.CartaGUI;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

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

        ImageView logo = new ImageView(new Image(getClass().
                getResourceAsStream("/recursos/baraja/Joker1.png")));
        logo.setFitWidth(96);
        logo.setFitHeight(128);
        logo.setOpacity(0.45);

        RotateTransition rotacionLogo = new RotateTransition(Duration.seconds(8),
                logo);
        rotacionLogo.setAxis(Rotate.Y_AXIS);
        rotacionLogo.setFromAngle(0);
        rotacionLogo.setToAngle(360);

        rotacionLogo.setInterpolator(Interpolator.LINEAR);
        rotacionLogo.setCycleCount(Animation.INDEFINITE);
        rotacionLogo.play();


        Label tituloJuego = new Label("Solitario: \n Eight Off");
        tituloJuego.getStyleClass().add("tituloJuego");

        StackPane titulo = new StackPane(logo, tituloJuego);

        botonJugar.getStyleClass().add("boton-titulo");
        botonSalir.getStyleClass().add("boton-titulo");
        VBox botones = new VBox(16, botonJugar, botonSalir);
        botones.setAlignment(Pos.CENTER);

        ventana.getChildren().addAll(titulo, botones);
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
