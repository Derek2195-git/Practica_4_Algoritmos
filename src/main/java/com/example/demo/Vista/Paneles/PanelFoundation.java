package com.example.demo.Vista.Paneles;

import com.example.demo.Modelo.Juego.Foundation;
import com.example.demo.Vista.ObjetosGUI.CartaGUI;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.function.IntConsumer;

public class PanelFoundation extends StackPane {
    private int indice;
    private IntConsumer alHacerClick;

    public PanelFoundation(int indice, IntConsumer alHacerClick) {
        this.indice = indice;
        this.alHacerClick = alHacerClick;
        setPrefSize(CartaGUI.ANCHO, CartaGUI.ALTO);
        setMinSize(CartaGUI.ANCHO, CartaGUI.ALTO);
        setMaxSize(CartaGUI.ANCHO, CartaGUI.ALTO);
        getChildren().add(crearHueco());
    }

    private Button crearHueco() {
        Button hueco = new Button();
        hueco.getStyleClass().add("ranura-carta");
        hueco.setPrefSize(CartaGUI.ANCHO, CartaGUI.ALTO);
        hueco.setFocusTraversable(false);
        hueco.setOnAction(e -> alHacerClick.accept(indice));
        return hueco;
    }

    public void redibujar(Foundation foundation) {
        getChildren().clear();

        if (foundation.estaVacia()) {
            getChildren().add(crearHueco());
        } else {
            CartaGUI tope = new CartaGUI(foundation.verTope());
            tope.setOnAction(e -> alHacerClick.accept(indice));
            getChildren().add(tope);
        }
    }

    public int getIndice() {
        return indice;
    }

}
