package com.example.dicegame2.Modelo;

import java.util.Random;

/**
 * Clase que representa un dado de 6 caras.
 * Modela la capacidad aleatoria de procesamiento en cada turno.
 */
public class Dado {
    private int valor;
    private Random random;

    /**
     * Constructor que inicializa el dado en 1.
     */
    public Dado() {
        this.random = new Random();
        this.valor = 1;
    }

    /**
     * Lanza el dado y genera un valor aleatorio entre 1 y 6.
     * @return El resultado del lanzamiento.
     */
    public int lanzar() {
        this.valor = random.nextInt(6) + 1;
        return this.valor;
    }

    /**
     * Obtiene el valor actual del dado.
     * @return Valor del dado (1 a 6).
     */
    public int getValor() {
        return valor;
    }

    /**
     * Asigna un valor especifico al dado.
     * @param valor Valor entre 1 y 6.
     */
    public void setValor(int valor) {
        if (valor >= 1 && valor <= 6) {
            this.valor = valor;
        }
    }
}