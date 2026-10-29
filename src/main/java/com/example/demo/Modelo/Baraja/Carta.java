package com.example.demo.Modelo.Baraja;
/**
 * Write a description of class Carta here.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-2)
 */
// Clases extraidas del mismo repositorio de la práctica 1
public abstract class Carta implements Comparable<Carta> {
    private int valor;
    private Palo palo;
    private String color;

    /**
     * Crea una carta
     * @param valor
     * @param palo
     * @param color
     */
    public Carta(int valor, Palo palo, String color) {
        this.valor = valor;
        this.palo = palo;
        this.color = color;
    }


    public String toString() {
        return switch (valor) {
            case 1 -> "A" + palo.getFigura();
            case 11 -> "J"+ palo.getFigura();
            case 12 -> "Q" + palo.getFigura();
            case 13 -> "K" + palo.getFigura();
            case -1 -> "Joker1";
            default -> ""+ valor + palo.getFigura();
        };
    }

    public boolean tieneElMismoValor(Carta carta) {
        return valor == carta.valor;
    }
    public boolean tieneElMismoPalo(Carta carta) {
        return this.palo.equals(carta.getPalo());
    }
    public boolean esLaSiguiente(Carta carta) {
        if (valor+1 == carta.valor) return true;
        else return false;
    }

    public int getValor() {
        return valor;
    }
    public Palo getPalo() {
        return palo;
    }
    public String getColor() {
        return color;
    }

    /**
     * Compara dos cartas entre si y revisa si es igual o diferente a la otra
     * @param obj Objeto al que compararemos nuestra carta
     * @return booleano que indica si es diferente o igual a nuestra carta
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Carta)) return false;
        Carta otra = (Carta) obj;
        return valor == otra.getValor() &&
                palo == otra.getPalo();
    }

    @Override
    public int hashCode() {
        return 31 * valor + palo.hashCode();
    }
}
