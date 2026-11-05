package com.example.demo.Modelo.Juego;

import com.example.demo.Modelo.Baraja.CartaInglesa;
import com.example.demo.Modelo.Listas.ListaSimple;

public class ColumnaCartas {
    private ListaSimple<CartaInglesa> cartasContenidas;
    private boolean soloReyes = false;

    /**
     * Crea una columna de cartas
     * @param soloReyes Booleano que indica si solo puede empezar con reyes
     */
    public ColumnaCartas(boolean soloReyes) {
        cartasContenidas = new ListaSimple<>();
        this.soloReyes = soloReyes;
    }

    /**
     * Metodo que revisa si la columna puede recibir una carta
     * @param carta Carta a ingresar
     * @return Booleano que indica si la columna puede recibir la carta
     */
    public boolean puedeRecibir(CartaInglesa carta) {
        CartaInglesa tope = cartasContenidas.peekInicio();
        if (tope == null) {
            return !soloReyes || carta.getValor() == 13;
        }
        return carta.tieneElMismoPalo(tope) && carta.esLaSiguiente(tope);
    }

    /**
     * Metodo que agrega una carta al tableau
     * @param carta Carta a ingresar
     * @return Booleano que indica si la inserción pudo realizarse
     */
    public boolean agregarCarta(CartaInglesa carta) {
        if (!puedeRecibir(carta)) return false;
        cartasContenidas.insertaInicio(carta);
        return true;
    }

    /**
     * Metodo que coloca un carta en la lista, sin importar si la puede recibir o no
     * @param carta
     */
    public void colocarCarta(CartaInglesa carta) {
        cartasContenidas.insertaInicio(carta);
    }

    /**
     * Metodo que revisa la longitud de una secuencia de cartas de la columna
     * @return Tamaño de la secuencia de cartas
     */
    public int longitudSecuencia() {
        // Revisamos el tamaño de la columna
        int n = getTamano();
        if (n == 0) return 0;
        int largo = 1;
        // Revisamos si la carta actual es la siguiente de la anterior, estamos en una secuencia
        // y aumentamos el valor de largo, en caso de que no, salimos del ciclo
        while (largo < n) {
            CartaInglesa arriba = getCarta(largo -1);
            CartaInglesa abajo = getCarta(largo);
            if (arriba.tieneElMismoPalo(abajo) &&
            arriba.esLaSiguiente(abajo)) largo++;
            else break;
        }
        return largo;
    }

    /**
     * Metodo que revisa la ultima carta contenida en el tableau, sin eliminarlo
     * @return Ultima carta ubicada en el tableau
     */
    public CartaInglesa verTope() { return cartasContenidas.peekInicio(); }

    /**
     * Metodo que quita una carta de la columna
     * @return Ultima carta ubicada en el tableau
     */
    public CartaInglesa quitarCarta() { return cartasContenidas.eliminaInicio(); }

    /**
     * Metodo que revisa si la columna está vacia
     * @return Booleano que indica si el tableau no contiene ninguna carta
     */
    public boolean estaVacia() { return cartasContenidas.isEmpty(); }

    /**
     * Metodo que devuelve el tamaño de la columna
     * @return Tamaño de la lista que contiene las cartas en la columna
     */
    public int getTamano() { return cartasContenidas.size(); }

    /**
     * Metodo que obtiene una carta en especifico dentro de la columna
     * @param indice Indice de la carta a remover
     * @return Carta obtenida en el tableau
     */
    public CartaInglesa getCarta(int indice) {
        return cartasContenidas.obtenerX(indice);
    }
}
