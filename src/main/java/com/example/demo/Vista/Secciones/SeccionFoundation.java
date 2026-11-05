package com.example.demo.Vista.Secciones;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Vista.Paneles.PanelFoundation;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.function.IntConsumer;

public class SeccionFoundation extends VBox {
    private PanelFoundation[] foundations;

    /**
     * Genera la sección de las foundations
     * @param cantidad Cantidad de foundations a generar
     * @param alHacerClick Evento que ocurre cuando clickesa
     */
    public SeccionFoundation(int cantidad, IntConsumer alHacerClick) {
        super(33);
        setAlignment(Pos.TOP_CENTER);

        foundations = new PanelFoundation[cantidad];
        for (int i = 0; i < foundations.length; i++) {
            foundations[i] = new PanelFoundation(i, alHacerClick);
            getChildren().add(foundations[i]);
        }
    }

    /**
     * Redibuja la sección
     * @param juego Juego actual
     */
    public void redibujar(Solitario juego) {
        for (int i = 0; i < foundations.length; i++) {
            foundations[i].redibujar(juego.getFoundation(i));
        }
    }

    /**
     * Quita el contorno de todas las foundations
     */
    public void quitarContorno() {
        for (PanelFoundation f : foundations) f.setPista(false);
    }

    /**
     * Getter del foundation
     * @param indice indice del foundation a regresar
     * @return representación grafica del foundation
     */
    public PanelFoundation getFoundation(int indice) {
        return foundations[indice];
    }
}
