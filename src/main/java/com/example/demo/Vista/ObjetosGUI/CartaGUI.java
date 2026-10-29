package com.example.demo.Vista.ObjetosGUI;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CartaGUI extends ImageButton {
    public static final int ANCHO = 72;
    public static final int ALTO = 96;
    private CartaInglesa carta;

    public CartaGUI(CartaInglesa carta) {
        super(generarRuta(carta), ALTO, ANCHO);
        this.carta = carta;

        setPadding(Insets.EMPTY);
        setFocusTraversable(false);
        if (getGraphic() instanceof ImageView image) {
            image.setSmooth(false);
        }
    }

    public static String generarRuta(CartaInglesa carta) {
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

    public CartaInglesa getCarta() {
        return carta;
    }

    public void setSeleccionada(boolean seleccionada) {
        cambiarContorno(seleccionada ? Color.GOLD : null);
    }

    public void setPista(boolean pista) {
        cambiarContorno(pista ? Color.CYAN : null);
    }

    public void cambiarContorno(Color color) {
        if (color == null) {
            setBorder(Border.EMPTY);
        } else {
            setBorder(new Border(new BorderStroke(
                    color, BorderStrokeStyle.SOLID,
                    new CornerRadii(6), new BorderWidths(2)
            )));
        }
    }

    public int getAncho() {
        return ANCHO;
    }
}
