package com.example.demo.Vista.ObjetosGUI;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;


public class CartaGUI extends ImageButton {
    public static final int ANCHO = 72;
    public static final int ALTO = 96;
    private CartaInglesa carta;

    /**
     * Crea la representación visual de una Carta
     * @param carta Carta a representar
     */
    public CartaGUI(CartaInglesa carta) {
        super(generarRuta(carta), ALTO, ANCHO);
        this.carta = carta;

        setPadding(Insets.EMPTY);
        setFocusTraversable(false);
    }

    /**
     * Metodo que genera la ruta donde se ubica la imagen correspondiente de la carta
     * @param carta Carta a mostrar
     * @return Ruta que contiene el nombre y ruta de la imagen de la carta
     */
    public static String generarRuta(CartaInglesa carta) {
        if (carta.getValor() == -1) {
            return "/recursos/baraja/Joker1.png";
        }
        String rango = switch (carta.getValor()) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";

            default -> String.valueOf(carta.getValor());
        };
        return "/recursos/baraja/" + rango + "_" +
                carta.getPalo().getFigura() + ".png";
    }

    /**
     * Metodo que cambia el color de una carta para que se muestre como seleccionada
     * @param seleccionada Booleano que indica si se seleccionó la carta
     */
    public void setSeleccionada(boolean seleccionada) {
        if (seleccionada) {
            if (!getStyleClass().contains("carta-seleccionada")) getStyleClass().add("carta-seleccionada");
        }
        else getStyleClass().remove("carta-seleccionada");
    }

    /**
     * Cambia el color de la carta para que se muestre como una pista
     * @param pista Booleano que indica si carta es parte de la pista
     */
    public void setPista(boolean pista) {
        if (pista) {
            if (!getStyleClass().contains("pista-carta")) getStyleClass().add("pista-carta");
        }
        else getStyleClass().remove("pista-carta");
    }

    /**
     * Getter del Ancho de la carta.
     * @return Ancho de la carta
     */
    public static int getAncho() {
        return ANCHO;
    }
}
