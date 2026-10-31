package com.example.demo.Vista.Paneles;

import com.example.demo.Modelo.Juego.Reserva;
import com.example.demo.Vista.ObjetosGUI.CartaGUI;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.function.IntConsumer;

public class PanelReserva extends StackPane {

    private final int indice;
    private final IntConsumer alHacerClic;
    private CartaGUI carta;
    Button hueco;

    public PanelReserva(int indice, IntConsumer alHacerClic) {
        this.indice = indice;
        this.alHacerClic = alHacerClic;
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
        hueco.setOnAction(e -> alHacerClic.accept(indice));
        this.hueco = hueco;
        return hueco;
    }

    public void actualizar(Reserva reserva) {
        getChildren().clear();
        carta = null;
        hueco = null;

        if (reserva.estaVacia()) {
            getChildren().add(crearHueco());
        } else {
            carta = new CartaGUI(reserva.getCarta());
            carta.setOnAction(e -> alHacerClic.accept(indice));
            getChildren().add(carta);
        }
    }

    public void setSeleccionada(boolean seleccionada) {
        if (carta != null) carta.setSeleccionada(seleccionada);
    }

    public void setPista(boolean pista) {
        if (carta != null) carta.setPista(pista);
        if (hueco != null) {
            hueco.getStyleClass().remove("ranura-pista");
            if (pista) hueco.getStyleClass().add("ranura-pista");
        }
    }

    public int getIndice() {
        return indice;
    }

}
