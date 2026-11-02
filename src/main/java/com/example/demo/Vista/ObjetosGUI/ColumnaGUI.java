package com.example.demo.Vista.ObjetosGUI;

import com.example.demo.Modelo.Juego.ColumnaCartas;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.function.IntConsumer;

public class ColumnaGUI extends Pane {
    private static final double ALTO_AREA = 364;      // altura disponible para la cascada
    private static final double SEPARACION_MAX = 22;  // cuánto se ve de cada carta tapada
    private static final double SEPARACION_MIN = 10;

    private final int indice;
    private final IntConsumer alHacerClic;
    private CartaGUI cartaTope;
    private ArrayList<CartaGUI> cartas;
    private Button hueco;

    public ColumnaGUI(int indice, IntConsumer alHacerClic) {
        this.indice = indice;
        this.alHacerClic = alHacerClic;
        cartas = new ArrayList<>();
        setPrefSize(CartaGUI.ANCHO, ALTO_AREA);
        setMinSize(CartaGUI.ANCHO, ALTO_AREA);
        setMaxSize(CartaGUI.ANCHO, ALTO_AREA);
        getChildren().add(crearHueco());
    }

    private Button crearHueco() {
        Button hueco = new Button();
        hueco.setPrefSize(CartaGUI.ANCHO, CartaGUI.ALTO);
        hueco.setFocusTraversable(false);
        hueco.getStyleClass().add("ranura-carta");
        hueco.setOnAction(e -> alHacerClic.accept(indice));
        this.hueco = hueco;
        return hueco;
    }

    public void actualizar(ColumnaCartas columna) {
        getChildren().clear();
        getChildren().add(crearHueco());
        cartaTope = null;
        cartas.clear();

        int n = columna.getTamano();
        double separacion = SEPARACION_MAX;
        if (n > 1) {
            // si la columna crece mucho, la cascada se comprime para que quepa
            double disponible = (ALTO_AREA - CartaGUI.ALTO) / (n - 1);
            separacion = Math.max(SEPARACION_MIN, Math.min(SEPARACION_MAX, disponible));
        }

        // En el modelo el índice 0 es el TOPE (insertaInicio), así que se dibuja
        // de n-1 a 0: la última en agregarse queda encima y más abajo en pantalla.
        for (int i = n - 1; i >= 0; i--) {
            CartaGUI carta = new CartaGUI(columna.getCarta(i));
            carta.setLayoutY((n - 1 - i) * separacion);
            carta.setOnAction(e -> alHacerClic.accept(indice));
            getChildren().add(carta);
            cartas.add(carta);
            cartaTope = carta;
        }
    }

    public void setSeleccionada(boolean seleccionada) {
        setSeleccionada(seleccionada, 1);
    }

    public void setSeleccionada(boolean seleccionada, int cantidad) {
        // al quitar la marca se limpian todas; al ponerla, solo las últimas "cantidad"
        int desde = seleccionada ? Math.max(0, cartas.size() - cantidad) : 0;
        for (int j = desde; j < cartas.size(); j++) {
            cartas.get(j).setSeleccionada(seleccionada);
        }
    }

    public void setPista(boolean pista) {
        if (cartaTope != null) cartaTope.setPista(pista);
        hueco.getStyleClass().remove("ranura-pista");
        if (pista && cartaTope == null) hueco.getStyleClass().add("ranura-pista");
    }

    public int getIndice() {
        return indice;
    }
}
