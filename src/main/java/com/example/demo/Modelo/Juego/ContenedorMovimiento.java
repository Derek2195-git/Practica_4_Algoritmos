package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import com.example.demo.Modelo.Listas.ListaSimple;

public class ContenedorMovimiento {
    public ListaSimple<Movimiento> movimientos;

    public ContenedorMovimiento() {
        movimientos = new ListaSimple<>();
    }

    public void agregarMovimiento(Movimiento movimiento) {
        movimientos.insertaInicio(movimiento);
    }

    public Movimiento sacarMovimiento() {
        return !isEmpty() ? movimientos.eliminaInicio() : null;
    }

    public boolean isEmpty() {
        return movimientos.isEmpty();
    }


}
