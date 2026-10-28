package com.example.demo.Vista.ObjetosGUI;

import com.example.demo.Modelo.Juego.ColumnaCartas;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.function.IntConsumer;

public class ColumnaGUI extends Pane {
    private static final double ALTO_AREA = 440;      // altura disponible para la cascada
    private static final double SEPARACION_MAX = 30;  // cuánto se ve de cada carta tapada
    private static final double SEPARACION_MIN = 12;

    private final int indice;
    private final IntConsumer alHacerClic;
    private CartaGUI cartaTope;

    public ColumnaGUI(int indice, IntConsumer alHacerClic) {
        this.indice = indice;
        this.alHacerClic = alHacerClic;
        setPrefSize(CartaGUI.ALTO, ALTO_AREA);
        setMinSize(CartaGUI.ANCHO, ALTO_AREA);
        setMaxSize(CartaGUI.ANCHO, ALTO_AREA);
        getChildren().add(crearHueco());
    }

    private Button crearHueco() {
        Button hueco = new Button();
        hueco.setPrefSize(CartaGUI.ANCHO, CartaGUI.ALTO);
        hueco.setFocusTraversable(false);
        hueco.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.08), new CornerRadii(8), Insets.EMPTY)));
        hueco.setBorder(new Border(new BorderStroke(
                Color.rgb(255, 255, 255, 0.5), BorderStrokeStyle.DASHED,
                new CornerRadii(8), new BorderWidths(2))));
        hueco.setOnAction(e -> alHacerClic.accept(indice));
        return hueco;
    }

    public void actualizar(ColumnaCartas columna) {
        getChildren().clear();
        getChildren().add(crearHueco());
        cartaTope = null;

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
            cartaTope = carta;
        }
    }

    public void setSeleccionada(boolean seleccionada) {
        if (cartaTope != null) cartaTope.setSeleccionada(seleccionada);
    }

    public void setPista(boolean pista) {
        if (cartaTope != null) cartaTope.setPista(pista);
    }

    public int getIndice() {
        return indice;
    }
}
