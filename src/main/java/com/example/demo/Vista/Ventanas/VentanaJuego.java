package com.example.demo.Vista.Ventanas;

import com.example.demo.Modelo.Juego.Solitario;
import com.example.demo.Modelo.ZonaJuego;
import com.example.demo.Vista.Secciones.SeccionColumnas;
import com.example.demo.Vista.Secciones.SeccionFoundation;
import com.example.demo.Vista.Secciones.SeccionOpciones;
import com.example.demo.Vista.Secciones.SeccionReserva;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.function.IntConsumer;

public class VentanaJuego {
    private final BorderPane raiz = new BorderPane();
    private SeccionReserva seccionReserva;
    private SeccionColumnas seccionColumnas;
    private SeccionFoundation seccionFoundation;
    private SeccionOpciones seccionOpciones;
    private boolean confirmacionCerrarPartida = false;

    /**
     * Crea la ventana de juego
     * @param juego Juego a mostrar
     * @param clickColumna Evento al clickear la columna
     * @param clickReserva Evento al clickear la reserva
     * @param clickFoundation Evento al clickear la foundation
     */
    public VentanaJuego(Solitario juego,
                        IntConsumer clickColumna,
                        IntConsumer clickReserva,
                        IntConsumer clickFoundation) {
        seccionColumnas = new SeccionColumnas(juego.NUM_COLUMNAS, clickColumna);
        seccionReserva = new SeccionReserva(juego.NUM_RESERVAS, clickReserva);
        seccionFoundation = new SeccionFoundation(juego.NUM_FOUNDATIONS, clickFoundation);
        seccionOpciones = new SeccionOpciones();

        VBox centro = new VBox(24, seccionReserva, seccionColumnas);

        BorderPane tablero = new BorderPane();
        tablero.setPadding(new Insets(20));
        tablero.setLeft(seccionFoundation);
        tablero.setCenter(centro);
        BorderPane.setMargin(seccionFoundation, new Insets(0, 20, 0, 0));


        raiz.setTop(seccionOpciones);
        raiz.setCenter(tablero);
        raiz.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());
        redibujar(juego);
    }

    /**
     * Metodo que redibuja el juego
     * @param juego Juego a mostrar
     */
    public void redibujar(Solitario juego) {
        seccionColumnas.redibujar(juego);
        seccionReserva.actualizar(juego);
        seccionFoundation.redibujar(juego);
    }

    /**
     * Metodo que quiat las marcas/contornos de todas las secciones
     */
    public void quitarMarcas() {
        seccionFoundation.quitarContorno();
        seccionReserva.quitarContorno();
        seccionColumnas.quitarContorno();
    }

    /**
     * Metodo que marca una carta al ser seleccionada
     * @param tipo Tipo de zona que se va a marcar
     * @param indice Indice de la zona que contenga a la carta
     * @param cantidad cantidad de cartas a marcar
     */
    public void marcarSeleccion(ZonaJuego tipo, int indice, int cantidad) {
        switch (tipo) {
            case COLUMNA -> seccionColumnas.getColumna(indice).setSeleccionada(true, cantidad);
            case RESERVA -> seccionReserva.getCelda(indice).setSeleccionada(true);
            default -> {}
        }
    }

    /**
     * Muestra un mensaje en la seccion de opciones
     * @param texto Mensaje a mostrar
     */
    public void mostrarMensaje(String texto) {
        seccionOpciones.mostrarMensaje(texto);
    }

    /**
     * Muestra un aviso de confirmación al acabar el juego
     * @param titulo Titulo del aviso
     * @param texto Texto del cuerpo del aviso
     */
    public void mostrarAviso(String titulo, String texto) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(texto);
        Button botonConfirmar = (Button) alerta.getDialogPane().lookupButton(ButtonType.OK);
        botonConfirmar.setText("Salir del juego");
        botonConfirmar.setOnAction(e-> confirmacionCerrarPartida = true);
        Button botonNegar = (Button) alerta.getDialogPane().lookupButton(ButtonType.CANCEL);
        botonNegar.setText("Quedarse en la partida actual");

        alerta.showAndWait();
    }

    /**
     * Getter qie indica si se cerrará la partida
     * @return Confirma que la partida se acabe al cerrarse la ventana
     */
    public boolean getConfirmacionCerrarPartida() {
        return confirmacionCerrarPartida;
    }

    /**
     * Metodo que marca una carta con el color de una pista
     * @param zona Zona a marcar
     * @param indice Indice de donde se ubica la carta
     */
    public void marcarPista(ZonaJuego zona, int indice) {
        switch (zona) {
            case COLUMNA -> seccionColumnas.getColumna(indice).setPista(true);
            case RESERVA -> seccionReserva.getCelda(indice).setPista(true);
            case FOUNDATION -> seccionFoundation.getFoundation(indice).setPista(true);
        }
    }

    /**
     * Metodo que bloquea la mayoria de cosas del tablero, con excepción de las cartas
     * @param habilitado Habilitador
     */
    public void bloquearTablero(boolean habilitado) {
        seccionReserva.setDisable(habilitado);
        seccionFoundation.setDisable(habilitado);
    }

    /**
     * Getter de la sección de opciones
     * @return La seccion de opciones
     */
    public SeccionOpciones getSeccionOpciones() {
        return seccionOpciones;
    }

    /**
     * Getter de la ventana de juego
     * @return Ventana de juego
     */
    public BorderPane getRaiz() {
        return raiz;
    }
}
