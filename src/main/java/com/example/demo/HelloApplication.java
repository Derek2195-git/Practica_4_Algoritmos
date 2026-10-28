package com.example.demo;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Vista.Ventanas.VentanaJuego;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Solitario juego = new Solitario(false);
        VentanaJuego ventana = new VentanaJuego(juego);

        stage.setTitle("Eight Off (prototipo)");
        Scene escena = new Scene(ventana.getRaiz());
        escena.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());
        stage.setScene(escena);
        stage.show();
    }
}
