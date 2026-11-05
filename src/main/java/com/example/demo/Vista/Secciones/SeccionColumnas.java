package com.example.demo.Vista.Secciones;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Vista.ObjetosGUI.ColumnaGUI;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.function.IntConsumer;

public class SeccionColumnas extends HBox {
    private ColumnaGUI[] columnas;

    /**
     * Crea la seccion del tableau
     * @param cantidad cantidad de tableaus a crear
     * @param alHacerClick Evento que ocurre al hacer click en uno de los tableaus
     */
    public SeccionColumnas(int cantidad, IntConsumer alHacerClick) {
        super(12);
        setAlignment(Pos.TOP_LEFT);

        columnas = new ColumnaGUI[cantidad];
        for (int i = 0; i < cantidad; i++) {
            columnas[i] = new ColumnaGUI(i, alHacerClick);
            getChildren().add(columnas[i]);
        }
    }

    /**
     * Redibuja la propia seccion de tableaus
     * @param juego Juego actual
     */
    public void redibujar(Solitario juego) {
        for (int i = 0; i < columnas.length; i++) {
            columnas[i].actualizar(juego.getColumna(i));
        }
    }

    /**
     * Getter de la forma grafica de las columnas
     * @param indice Indice de la columna a obtener
     * @return Columna grafica pedida
     */
    public ColumnaGUI getColumna(int indice) {
        return columnas[indice];
    }

    /**
     * Metodo que les quita el contorno a cada columna
     */
    public void quitarContorno() {
        for (ColumnaGUI col : columnas) {
            col.setSeleccionada(false);
            col.setPista(false);
        }
    }
}
