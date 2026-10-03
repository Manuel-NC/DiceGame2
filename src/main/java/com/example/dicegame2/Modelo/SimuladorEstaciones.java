package com.example.dicegame2.Modelo;

import java.util.ArrayList;

/**
 * Gestor principal de la simulacion del juego de dados (Game 2).
 * Administra las 10 estaciones, el flujo de turnos y el registro de metricas.
 */
public class SimuladorEstaciones {
    private ArrayList<EstacionTrabajo> estaciones;
    private ArrayList<RegistroTurno> historialMetrics;
    private int turnoActual;
    private int totalUnidadesCompletadas;

    public SimuladorEstaciones() {
        this.estaciones = new ArrayList<>();
        this.historialMetrics = new ArrayList<>();
        this.turnoActual = 0;
        this.totalUnidadesCompletadas = 0;

        inicializarEstaciones();
    }

    /**
     * Crea las 10 estaciones de trabajo.
     */
    private void inicializarEstaciones() {
        for (int i = 1; i <= 10; i++) {
            // Capacidad de 100 elementos por cola circular
            estaciones.add(new EstacionTrabajo(i, 100));
        }
    }

    /**
     * Lanza los dados de todas las estaciones.
     */
    public void lanzarDados() {
        for (EstacionTrabajo estacion : estaciones) {
            estacion.lanzarDados();
        }
    }

    /**
     * Mueve un dado de la estacion origen a la estacion destino (Mecanica de Game 2).
     */
    public boolean moverDado(int indiceOrigen, int indiceDestino) {
        if (indiceOrigen >= 0 && indiceOrigen < estaciones.size() &&
                indiceDestino >= 0 && indiceDestino < estaciones.size() &&
                indiceOrigen != indiceDestino) {

            EstacionTrabajo origen = estaciones.get(indiceOrigen);
            EstacionTrabajo destino = estaciones.get(indiceDestino);

            if (origen.tieneDados()) {
                Dado dadoAMover = origen.removerDado();
                destino.agregarDado(dadoAMover);
                return true;
            }
        }
        return false;
    }

    /**
     * Procesa la atencion de un turno completo en toda la linea de produccion.
     * La estacion 1 trae personas segun el valor de sus dados.
     * Las demas estaciones procesan segun las personas disponibles en su cola.
     */
    public void avanzarTurno() {
        if (turnoActual >= 20) return;

        // Procesar de atras hacia adelante (de la estacion 10 a la 1)
        for (int i = estaciones.size() - 1; i >= 0; i--) {
            EstacionTrabajo actual = estaciones.get(i);
            int capacidadDado = actual.getCapacidadTotal();

            if (i == 0) {
                // La estacion 1 es la entrada del sistema (no tiene cola previa)
                // Genera tantas personas como indique el dado y las manda a la Estacion 2.
                int movidas = capacidadDado;
                EstacionTrabajo siguiente = estaciones.get(1);

                for (int k = 0; k < movidas; k++) {
                    siguiente.agregarPersona(new Persona(turnoActual));
                }

                historialMetrics.add(new RegistroTurno(
                        turnoActual, actual.getIdEstacion(), capacidadDado, movidas, 0, 0
                ));

            } else if (i < estaciones.size() - 1) {
                // Las estaciones 2 a 9 procesan lo que hay en su cola circular
                ArrayList<Persona> personasProcesadas = actual.procesarTurno();
                int movidas = personasProcesadas.size();

                EstacionTrabajo siguiente = estaciones.get(i + 1);
                for (Persona p : personasProcesadas) {
                    siguiente.agregarPersona(p);
                }

                historialMetrics.add(new RegistroTurno(
                        turnoActual, actual.getIdEstacion(), capacidadDado, movidas, actual.getCantidadEnCola(), 0
                ));

            } else {
                // La estacion 10 es la salida final de la linea
                ArrayList<Persona> personasProcesadas = actual.procesarTurno();
                int movidas = personasProcesadas.size();

                totalUnidadesCompletadas += movidas;

                historialMetrics.add(new RegistroTurno(
                        turnoActual, actual.getIdEstacion(), capacidadDado, movidas, actual.getCantidadEnCola(), movidas
                ));
            }
        }

        turnoActual++;
    }


    public ArrayList<EstacionTrabajo> getEstaciones() {
        return estaciones;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public ArrayList<RegistroTurno> getHistorialMetrics() {
        return historialMetrics;
    }

    public int getTotalUnidadesCompletadas() {
        return totalUnidadesCompletadas;
    }
}