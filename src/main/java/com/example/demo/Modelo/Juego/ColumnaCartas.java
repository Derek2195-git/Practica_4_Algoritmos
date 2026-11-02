package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import com.example.demo.Modelo.Listas.ListaSimple;

public class ColumnaCartas {
    private ListaSimple<CartaInglesa> cartasContenidas;
    private boolean soloReyes = false;

    public ColumnaCartas(boolean soloReyes) {
        cartasContenidas = new ListaSimple<>();
        this.soloReyes = soloReyes;
    }

    public boolean puedeRecibir(CartaInglesa carta) {
        CartaInglesa tope = cartasContenidas.peekInicio();
        if (tope == null) {
            return !soloReyes || carta.getValor() == 13;
        }
        return carta.tieneElMismoPalo(tope) && carta.esLaSiguiente(tope);
    }

    // El que usaremos para el juego, donde validamos las cosas
    public boolean agregarCarta(CartaInglesa carta) {
        if (!puedeRecibir(carta)) return false;
        cartasContenidas.insertaInicio(carta);
        return true;
    }

    // Para el undo
    public void colocarCarta(CartaInglesa carta) {
        cartasContenidas.insertaInicio(carta);
    }

    public int longitudSecuencia() {
        int n = getTamano();
        if (n == 0) return 0;
        int largo = 1;
        while (largo < n) {
            CartaInglesa arriba = getCarta(largo -1);
            CartaInglesa abajo = getCarta(largo);
            if (arriba.tieneElMismoPalo(abajo) &&
            arriba.esLaSiguiente(abajo)) largo++;
            else break;
        }
        return largo;
    }

    public CartaInglesa verTope() { return cartasContenidas.peekInicio(); }
    public CartaInglesa quitarCarta() { return cartasContenidas.eliminaInicio(); }
    public boolean estaVacia() { return cartasContenidas.isEmpty(); }
    public int getTamano() { return cartasContenidas.size(); }

    public CartaInglesa getCarta(int indice) {
        return cartasContenidas.obtenerX(indice);
    }



}
