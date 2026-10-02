package com.example.dicegame2.Modelo;

/**
 * Almacena el estado de una estacion en un turno especifico.
 * Se utiliza para generar las graficas al finalizar los 20 turnos.
 */
public class RegistroTurno {
    private int numeroTurno;
    private int idEstacion;
    private int valorDado;
    private int unidadesMovidas;
    private int unidadesEnCola;
    private int unidadesSalidasSistema; // Si falta algun dato para un grafica, lo agrego luego

    public RegistroTurno(int numeroTurno, int idEstacion, int valorDado,
                         int unidadesMovidas, int unidadesEnCola, int unidadesSalidasSistema) {
        this.numeroTurno = numeroTurno;
        this.idEstacion = idEstacion;
        this.valorDado = valorDado;
        this.unidadesMovidas = unidadesMovidas;
        this.unidadesEnCola = unidadesEnCola;
        this.unidadesSalidasSistema = unidadesSalidasSistema;
    }

    public int getNumeroTurno() {
        return numeroTurno;
    }

    public int getIdEstacion() {
        return idEstacion;
    }

    public int getValorDado() {
        return valorDado;
    }

    public int getUnidadesMovidas() {
        return unidadesMovidas;
    }

    public int getUnidadesEnCola() {
        return unidadesEnCola;
    }

    public int getUnidadesSalidasSistema() {
        return unidadesSalidasSistema;
    }
}
