package com.example.demo.Modelo.Listas;

public class ListaSimple<T> {
    private Nodo<T> inicio;
    private Nodo<T> fin;



    public void insertaInicio(T dato) {
        Nodo<T> n = new Nodo(dato);
        n.setInfo(dato);
        n.setSiguiente(inicio);
        inicio = n;
    }

    public T eliminaInicio() {
        if (inicio == null) return null;
        else {
            T dato = inicio.getInfo();
            inicio = inicio.getSiguiente();
            return dato;
        }
    }

    public void insertaFin(T dato) {
        Nodo<T> n = new Nodo<>(dato);
        if (inicio == null) {
            n.setSiguiente(inicio);
            inicio = n;
        } else {
            Nodo<T> r = inicio;
            while (r.getSiguiente() != null) {
                r = r.getSiguiente();
            }
            r.setSiguiente(n);
            n.setSiguiente(null);
        }
    }

    public T eliminaFin() {
        if (inicio == null) return null;
        else {
            T dato;
            if (inicio.getSiguiente() == null) {
                dato = inicio.getInfo();
                inicio = null;
                return dato;
            } else {
                Nodo<T> r = inicio;
                Nodo<T> a = r;
                while(r.getSiguiente() != null) {
                    a = r;
                    r = r.getSiguiente();
                }
                dato = r.getInfo();
                a.setSiguiente(null);
                return dato;
            }
        }
    }


    public String mostrarLista() {
        if (inicio == null) {
            return "La lista esta vacía";
        } else {
            Nodo<T> r = inicio;
            String cadena = "";
            while(r != null) {
                cadena += r.getInfo() + ", ";
                r = r.getSiguiente();
            }

            return cadena;
            // Debe ser recursivo y otro iterativo
        }
    }

    // 1. Buscar recursivo. Recibe x dato y
    // regresa el indice o -1 en caso de que no se encuentre

    public int buscarRecursivo(Nodo<T> nodo, T dato, int indice) {
        if (nodo == null) return -1;
        if (nodo.getInfo().equals(dato)) {
            return indice;
        } else {
            return buscarRecursivo(nodo.getSiguiente(), dato, indice+1);
        }
    }

    public int buscarDato(T dato) {
        return buscarRecursivo(inicio, dato, 0);
    }

    // 2. Eliminar posicion. Recibe un nmero entero; esta es la posición que vamos a eliminar.
    // Regresa el dato eliminado o null en caso de no encontrarlo
    public T eliminarPosicion(int posicion) {
        if (inicio == null || posicion < 0) return null;
        if (posicion == 0) return eliminaInicio();
        Nodo<T> a = inicio;
        Nodo<T> r = inicio.getSiguiente();
        int contador = 1;
        while (r != null && contador < posicion) {
            a = r; r = r.getSiguiente(); contador++;
        }
        if (r == null) return null;
        a.setSiguiente(r.getSiguiente());
        return r.getInfo();
    }

    public T obtenerX(int indice) {
        if (indice < 0) return null;
        Nodo<T> r = inicio;
        for (int i = 0; i < indice && r != null; i++) {
            r = r.getSiguiente();
        }
        return r == null ? null : r.getInfo();
    }

    public boolean eliminarDato(T dato) {
        if (inicio == null) return false;
        else {
            int posicion = buscarDato(dato);
            if (posicion == -1) return false;

            if (posicion == 0) {
                inicio = inicio.getSiguiente();
                return true;
            }

            Nodo<T> r = inicio;
            Nodo<T> a = r;
            int contador = 0;
            while(contador != posicion) {
                a = r;
                r = r.getSiguiente();
                contador++;
            }
            a.setSiguiente(r.getSiguiente());
            return true;
        }
    }

    public T peekInicio() {
        if (inicio == null) return null;
        else return inicio.getInfo();
    }

    public int size() {
        int tamano = 0;
        Nodo<T> r = inicio;
        while (r.getSiguiente() != null) {
            tamano++;
        }
        return tamano;
    }



    public String mostrarLista(Nodo<T> nodo) {
        if (nodo == null) {
            return "";
        } else {
            return nodo.getInfo() + ", " + mostrarLista(nodo.getSiguiente());
        }
    }

    public String mostrarListaRecursiva() {
        String cadena = mostrarLista(inicio);
        return cadena.isEmpty() ? "La lista esta vacia" : cadena;
    }

    public boolean isEmpty() {
        return (inicio == null);
    }






}

