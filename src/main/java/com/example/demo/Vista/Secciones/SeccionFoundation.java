package com.example.demo.Vista.Secciones;

import com.example.demo.Modelo.Juego.Foundation;
import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Vista.Paneles.PanelFoundation;
import com.example.demo.Vista.Paneles.PanelReserva;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.function.IntConsumer;

public class SeccionFoundation extends VBox {
    private PanelFoundation[] foundations;

    public SeccionFoundation(int cantidad, IntConsumer alHacerClick) {
        super(33);
        setAlignment(Pos.TOP_CENTER);

        foundations = new PanelFoundation[cantidad];
        for (int i = 0; i < foundations.length; i++) {
            foundations[i] = new PanelFoundation(i, alHacerClick);
            getChildren().add(foundations[i]);
        }
    }

    public void redibujar(Solitario juego) {
        for (int i = 0; i < foundations.length; i++) {
            foundations[i].redibujar(juego.getFoundation(i));
        }
    }

    public void quitarMarcas() {
        for (PanelFoundation f : foundations) f.setPista(false);
    }

    public PanelFoundation getFoundation(int indice) {
        return foundations[indice];
    }
}
