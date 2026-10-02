package com.example.dicegame2.Estructuras;

public class ColaCircular<T> {
    private T[] colaCircular;
    private int inicio;
    private int fin;

    public ColaCircular() {
        colaCircular = (T[]) new Object[10];
        inicio = -1;
        fin = -1;
    }

    public ColaCircular(int capacidad) {
        colaCircular = (T[]) new Object[capacidad];
        inicio = -1;
        fin = -1;
    }

    public boolean insertarCircular(T dato) {

        if (((fin == colaCircular.length - 1) && (inicio == 0)) || ((fin+1) == inicio)) {
            System.out.println("Desbordamiento");
            return false;
        }

        if (fin == colaCircular.length - 1) {
            fin = 0;
        } else {
            fin++;
        }

        colaCircular[fin] = dato;
        if (inicio == -1) {
            inicio = 0;
        }

        return true;
    }

    public T eliminarCircular() {
        if (inicio == -1) {
            System.out.println("Subdesboardamiento");
            return null;
        } else {
            T dato = colaCircular[inicio];

            if (inicio == fin) {
                inicio = -1;
                fin = -1;
            } else {
                if (inicio == colaCircular.length - 1) {
                    inicio = 0;
                } else {
                    inicio++;
                }
            }

            return dato;
        }
    }
}
