package com.example.dicegame2.Modelo;

/**
 * Representa una unidad/persona (punto azul) en la linea de produccion.
 * Almacena el turno en que ingresa a la cola para calcular el tiempo en el sistema.
 */
public class Persona {
    private int turnoLlegada;

    /**
     * Constructor que registra el turno de entrada.
     * @param turnoLlegada Numero del turno actual (1 a 20).
     */
    public Persona(int turnoLlegada) {
        this.turnoLlegada = turnoLlegada;
    }

    /**
     * Obtiene el turno en el que llego la persona.
     * @return Numero de turno.
     */
    public int getTurnoLlegada() {
        return turnoLlegada;
    }

    /**
     * Calcula los turnos transcurridos dentro del sistema.
     * @param turnoActual Turno en el que sale del sistema.
     * @return Total de turnos permanecidos.
     */
    public int calcularTiempoEnSistema(int turnoActual) {
        return (turnoActual - turnoLlegada) + 1;
    }
}
