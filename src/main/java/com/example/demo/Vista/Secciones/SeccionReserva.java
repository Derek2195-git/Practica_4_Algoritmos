package com.example.demo.Vista.Secciones;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Vista.Paneles.PanelReserva;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.function.IntConsumer;

public class SeccionReserva extends HBox {
    private final PanelReserva[] celdas;

    public SeccionReserva(int cantidad, IntConsumer alHacerClic) {
        super(12);                       // misma separación que SeccionColumnas
        setAlignment(Pos.TOP_LEFT);

        celdas = new PanelReserva[cantidad];
        for (int i = 0; i < cantidad; i++) {
            celdas[i] = new PanelReserva(i, alHacerClic);
            getChildren().add(celdas[i]);
        }
    }

    public void actualizar(Solitario juego) {
        for (int i = 0; i < celdas.length; i++) {
            celdas[i].actualizar(juego.getReserva(i));
        }
    }

    public PanelReserva getCelda(int indice) {
        return celdas[indice];
    }

    public void quitarMarcas() {
        for (PanelReserva c : celdas) {
            c.setSeleccionada(false);
            c.setPista(false);
        }
    }
}
