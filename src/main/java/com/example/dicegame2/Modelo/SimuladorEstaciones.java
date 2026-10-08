package com.example.dicegame2.Modelo;

import java.util.ArrayList;

/**
 * Gestor principal de la simulacion del juego de dados (Game 2).
 * Administra las 10 estaciones, el flujo de turnos y el registro de metricas.
 */
public class SimuladorEstaciones {
    private ArrayList<EstacionTrabajo> estaciones;
    private ArrayList<RegistroTurno> historialMetrics;
    private ArrayList<Integer> tiemposEnSistema;
    private int turnoActual;
    private int totalUnidadesCompletadas;

    public SimuladorEstaciones() {
        this.estaciones = new ArrayList<>();
        this.historialMetrics = new ArrayList<>();
        this.tiemposEnSistema = new ArrayList<>();
        this.turnoActual = 0;
        this.totalUnidadesCompletadas = 0;

        inicializarEstaciones();
    }

    /**
     * Crea las 10 estaciones de trabajo.
     */
    private void inicializarEstaciones() {
        for (int i = 1; i <= 10; i++) {
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

        for (int i = estaciones.size() - 1; i >= 0; i--) {
            EstacionTrabajo actual = estaciones.get(i);
            int capacidadDado = actual.getCapacidadTotal();

            if (i == 0) {
                int movidas = capacidadDado;
                EstacionTrabajo siguiente = estaciones.get(1);

                for (int k = 0; k < movidas; k++) {
                    siguiente.agregarPersona(new Persona(turnoActual + 1));
                }

                historialMetrics.add(new RegistroTurno(
                        turnoActual + 1, actual.getIdEstacion(), capacidadDado, movidas, 0, 0
                ));

            } else if (i < estaciones.size() - 1) {
                ArrayList<Persona> personasProcesadas = actual.procesarTurno();
                int movidas = personasProcesadas.size();

                EstacionTrabajo siguiente = estaciones.get(i + 1);
                for (Persona p : personasProcesadas) {
                    siguiente.agregarPersona(p);
                }

                historialMetrics.add(new RegistroTurno(
                        turnoActual + 1, actual.getIdEstacion(), capacidadDado, movidas, actual.getCantidadEnCola(), 0
                ));

            } else {
                ArrayList<Persona> personasProcesadas = actual.procesarTurno();
                int movidas = personasProcesadas.size();

                totalUnidadesCompletadas += movidas;

                for (Persona p : personasProcesadas) {
                    tiemposEnSistema.add(p.calcularTiempoEnSistema(turnoActual + 1));
                }

                historialMetrics.add(new RegistroTurno(
                        turnoActual + 1, actual.getIdEstacion(), capacidadDado, movidas, actual.getCantidadEnCola(), movidas
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

    public ArrayList<Integer> getTiemposEnSistema() {
        return tiemposEnSistema;
    }

    public int getTotalUnidadesCompletadas() {
        return totalUnidadesCompletadas;
    }
}