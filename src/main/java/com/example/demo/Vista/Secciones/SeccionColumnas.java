package com.example.demo.Vista.Secciones;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Vista.ObjetosGUI.ColumnaGUI;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.function.IntConsumer;

public class SeccionColumnas extends HBox {
    private ColumnaGUI[] columnas;

    public SeccionColumnas(int cantidad, IntConsumer alHacerClick) {
        super(12);
        setAlignment(Pos.TOP_LEFT);

        columnas = new ColumnaGUI[cantidad];
        for (int i = 0; i < cantidad; i++) {
            columnas[i] = new ColumnaGUI(i, alHacerClick);
            getChildren().add(columnas[i]);
        }
    }

    public void redibujar(Solitario juego) {
        for (int i = 0; i < columnas.length; i++) {
            columnas[i].actualizar(juego.getColumna(i));
        }
    }

    public ColumnaGUI getColumna(int indice) {
        return columnas[indice];
    }

    public void quitarContorno() {
        for (ColumnaGUI col : columnas) {
            col.setSeleccionada(false);
            col.setPista(false);
        }
    }
}
