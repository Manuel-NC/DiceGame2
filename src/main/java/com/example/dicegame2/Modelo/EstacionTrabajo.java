package com.example.dicegame2.Modelo;

import com.example.dicegame2.Estructuras.ColaCircular;
import java.util.ArrayList;

/**
 * Representa una estacion de trabajo con su cola FIFO y su lista de dados asignados.
 */
public class EstacionTrabajo {
    private int idEstacion;
    private ColaCircular<Persona> cola;
    private ArrayList<Dado> dados;
    private int cantidadEnCola;

    public EstacionTrabajo(int idEstacion, int capacidadCola) {
        this.idEstacion = idEstacion;
        this.cola = new ColaCircular<>(capacidadCola);
        this.dados = new ArrayList<>();
        this.dados.add(new Dado()); // Inicia con 1 dado asignado por defecto
        this.cantidadEnCola = 0;
    }

    public boolean agregarPersona(Persona persona) {
        boolean exito = cola.insertarCircular(persona);
        if (exito) {
            cantidadEnCola++;
        }
        return exito;
    }

    /**
     * Lanza todos los dados asignados a esta estacion.
     */
    public void lanzarDados() {
        for (Dado d : dados) {
            d.lanzar();
        }
    }

    /**
     * Suma el valor de todos los dados asignados a la estacion.
     */
    public int getCapacidadTotal() {
        int suma = 0;
        for (Dado d : dados) {
            suma += d.getValor();
        }
        return suma;
    }

    /**
     * Procesa y desencola personas segun la capacidad total acumulada de sus dados.
     */
    public ArrayList<Persona> procesarTurno() {
        ArrayList<Persona> procesadas = new ArrayList<>();
        int capacidadProcesamiento = getCapacidadTotal();

        for (int i = 0; i < capacidadProcesamiento; i++) {
            if (cantidadEnCola > 0) {
                Persona p = cola.eliminarCircular();
                if (p != null) {
                    procesadas.add(p);
                    cantidadEnCola--;
                }
            } else {
                break;
            }
        }
        return procesadas;
    }

    // Gestion de Dados

    public void agregarDado(Dado dado) {
        this.dados.add(dado);
    }

    public Dado removerDado() {
        if (!dados.isEmpty()) {
            return dados.remove(dados.size() - 1);
        }
        return null;
    }

    public boolean tieneDados() {
        return !dados.isEmpty();
    }

    public ArrayList<Dado> getDados() {
        return dados;
    }

    // Getters

    public int getIdEstacion() {
        return idEstacion;
    }

    public ColaCircular<Persona> getCola() {
        return cola;
    }

    public int getCantidadEnCola() {
        return cantidadEnCola;
    }
}