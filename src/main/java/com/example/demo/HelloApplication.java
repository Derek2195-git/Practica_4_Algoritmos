package com.example.demo;

import com.example.demo.Controlador.Controlador;
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
        new Controlador(stage).iniciar();
    }
}
