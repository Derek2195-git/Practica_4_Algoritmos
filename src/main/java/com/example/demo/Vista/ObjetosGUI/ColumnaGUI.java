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

    /**
     * Crea un tableau
     * @param indice Indice del tableau a visualizar
     * @param alHacerClic Evento que ocurre cuando se haga click
     */
    public ColumnaGUI(int indice, IntConsumer alHacerClic) {
        this.indice = indice;
        this.alHacerClic = alHacerClic;
        cartas = new ArrayList<>();
        setPrefSize(CartaGUI.getAncho(), ALTO_AREA);
        setMinSize(CartaGUI.getAncho(), ALTO_AREA);
        setMaxSize(CartaGUI.getAncho(), ALTO_AREA);
        getChildren().add(crearHueco());
    }

    /**
     * Crea y visualiza el tableau cuando no hay cartas en el
     * @return Botón que representa el espacio vacio en el tableau
     */
    private Button crearHueco() {
        Button hueco = new Button();
        hueco.setPrefSize(CartaGUI.ANCHO, CartaGUI.ALTO);
        hueco.setFocusTraversable(false);
        hueco.getStyleClass().add("ranura-carta");
        hueco.setOnAction(e -> alHacerClic.accept(indice));
        this.hueco = hueco;
        return hueco;
    }

    /**
     * Metodo que redibuja la columna
     * @param columna Columna a redibujar
     */
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

        // Se dibuja como si fuera una pila
        for (int i = n - 1; i >= 0; i--) {
            CartaGUI carta = new CartaGUI(columna.getCarta(i));
            carta.setLayoutY((n - 1 - i) * separacion);
            carta.setOnAction(e -> alHacerClic.accept(indice));
            getChildren().add(carta);
            cartas.add(carta);
            cartaTope = carta;
        }
    }

    /**
     * Metodo que ilumina una columna como seleccionada
     * @param seleccionada Booleano que indica si se seleccionó o no
     */
    public void setSeleccionada(boolean seleccionada) {
        setSeleccionada(seleccionada, 1);
    }

    /**
     * Metodo sobrecargado que ilumina una columna
     * @param seleccionada Booleano que indica si se seleccionó la columna
     * @param cantidad Cantidad de cartas a iluminar en la columna
     */
    public void setSeleccionada(boolean seleccionada, int cantidad) {
        int desde = seleccionada ? Math.max(0, cartas.size() - cantidad) : 0;
        for (int j = desde; j < cartas.size(); j++) {
            cartas.get(j).setSeleccionada(seleccionada);
        }
    }

    /**
     * Metodo que ilumina la ultima carta de la columna como si fuera una pista
     * @param pista Booleano que indica si se pidió una pista
     */
    public void setPista(boolean pista) {
        if (cartaTope != null) cartaTope.setPista(pista);
        hueco.getStyleClass().remove("ranura-pista");
        if (pista && cartaTope == null) hueco.getStyleClass().add("ranura-pista");
    }

}
