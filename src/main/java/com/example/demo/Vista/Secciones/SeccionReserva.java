package com.example.demo.Vista.Secciones;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Vista.Paneles.PanelReserva;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.function.IntConsumer;

public class SeccionReserva extends HBox {
    private final PanelReserva[] celdas;

    /**
     * Crea la sección de las reservas
     * @param cantidad Cantidad de reservas a mostrar
     * @param alHacerClic Evento que ocurre cuando clickeas
     */
    public SeccionReserva(int cantidad, IntConsumer alHacerClic) {
        super(12);
        setAlignment(Pos.TOP_LEFT);

        celdas = new PanelReserva[cantidad];
        for (int i = 0; i < cantidad; i++) {
            celdas[i] = new PanelReserva(i, alHacerClic);
            getChildren().add(celdas[i]);
        }
    }

    /**
     * Metodo que redibuja la seccion
     * @param juego Juego actual
     */
    public void actualizar(Solitario juego) {
        for (int i = 0; i < celdas.length; i++) {
            celdas[i].actualizar(juego.getReserva(i));
        }
    }

    /**
     * Metodo que devuelve la celda actual de la reserva
     * @param indice Reserva a buscar
     * @return Representación grafica de la reserva buscada
     */
    public PanelReserva getCelda(int indice) {
        return celdas[indice];
    }

    /**
     * Quita el contorno de las reservas
     */
    public void quitarContorno() {
        for (PanelReserva c : celdas) {
            c.setSeleccionada(false);
            c.setPista(false);
        }
    }
}
