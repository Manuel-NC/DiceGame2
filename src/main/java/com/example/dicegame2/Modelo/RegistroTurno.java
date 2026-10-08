package com.example.dicegame2.Modelo;

/**
 * Almacena el estado de una estacion en un turno especifico.
 * Se utiliza para generar las graficas al finalizar los 20 turnos.
 */
public class RegistroTurno {
    private int turno;
    private int idEstacion;
    private int valorDado;
    private int personasMovidas;
    private int cantidadEnCola;
    private int personasCompletadas;

    public RegistroTurno(int turno, int idEstacion, int valorDado, int personasMovidas, int cantidadEnCola, int personasCompletadas) {
        this.turno = turno;
        this.idEstacion = idEstacion;
        this.valorDado = valorDado;
        this.personasMovidas = personasMovidas;
        this.cantidadEnCola = cantidadEnCola;
        this.personasCompletadas = personasCompletadas;
    }

    public int getTurno() {
        return turno;
    }

    public int getIdEstacion() {
        return idEstacion;
    }

    public int getValorDado() {
        return valorDado;
    }

    public int getPersonasMovidas() {
        return personasMovidas;
    }

    public int getCantidadEnCola() {
        return cantidadEnCola;
    }

    public int getPersonasCompletadas() {
        return personasCompletadas;
    }
}
