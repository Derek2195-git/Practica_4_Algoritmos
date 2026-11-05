package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import com.example.demo.Modelo.Listas.ListaSimple;

public class ContenedorMovimiento {
    public ListaSimple<Movimiento> movimientos;

    /**
     * Crea un contenedor de movimientos
     */
    public ContenedorMovimiento() {
        movimientos = new ListaSimple<>();
    }

    /**
     * Agrega un movimiento al contenedor
     * @param movimiento Movimiento realizado en el juego a agregar
     */
    public void agregarMovimiento(Movimiento movimiento) {
        movimientos.insertaInicio(movimiento);
    }

    /**
     * Elimina un movimiento del contenedor
     * @return Ultimo movimiento realizado
     */
    public Movimiento sacarMovimiento() {
        return !isEmpty() ? movimientos.eliminaInicio() : null;
    }

    /**
     * Revisa si el contenedor de movimientos está vacio
     * @return Booleano que indica si el contenedor está vacio o aun tiene movimientos
     */
    public boolean isEmpty() {
        return movimientos.isEmpty();
    }

    /**
     * Metodo que obtiene el ultimo movimiento en el contenedor, sin eliminarlo de la lista
     * @return Ultimo movimiento realizado en el juego
     */
    public Movimiento verUltimo() {
        return movimientos.peekInicio();
    }


}
