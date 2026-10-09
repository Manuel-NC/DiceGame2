package com.example.dicegame2.Modelo;

import java.util.ArrayList;

/**
 * Gestor principal de la simulacion del juego de dados (Game 2).
 * Administra las 10 estaciones, el flujo de turnos y el registro de metricas.
 */
public class SimuladorEstaciones {
    private ArrayList<EstacionTrabajo> estaciones;
    private ArrayList<RegistroTurno> metricasHistorial;
    private ArrayList<Integer> tiemposEnSistema;
    private int turnoActual;
    private int totalUnidadesCompletadas;

    /**
     * Constructor que inicializa el motor numérico de la simulacion.
     */
    public SimuladorEstaciones() {
        this.estaciones = new ArrayList<>();
        this.metricasHistorial = new ArrayList<>();
        this.tiemposEnSistema = new ArrayList<>();
        this.turnoActual = 0;
        this.totalUnidadesCompletadas = 0;

        inicializarEstaciones();
    }

    /**
     * Crea las 10 estaciones de trabajo.
     * Configura el estado inicial del juego: la Estacion 1 inicia vacia y las estaciones 2 a 10
     * cargan 4 personas en sus colas con turno de llegada 0.
     * Carga inicial de las Colas
     */
    private void inicializarEstaciones() {
        for (int i = 1; i <= 10; i++) {
            estaciones.add(new EstacionTrabajo(i, 100));

            if (i > 1) {
                for (int k = 0; k < 4; k++) {
                    estaciones.get(i - 1).agregarPersona(new Persona(0));
                }
            }
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
     * @param indiceOrigen Indice de la estacion de origen (0 a 9).
     * @param indiceDestino Indice de la estacion de destino (0 a 9).
     * @return true si se realizo el movimiento correctamente, false en caso contrario.
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
                // Estacion 1: Genera personas segun sus dados y las ENCOLA en la Estacion 2
                int movidas = capacidadDado;
                EstacionTrabajo siguiente = estaciones.get(1);

                for (int k = 0; k < movidas; k++) {
                    siguiente.agregarPersona(new Persona(turnoActual + 1));
                }

                metricasHistorial.add(new RegistroTurno(
                        turnoActual + 1, actual.getIdEstacion(), capacidadDado, movidas, 0, 0
                ));

            } else if (i < estaciones.size() - 1) {
                // Estaciones 2 a 9: DESENCOLAN de su propia cola y ENCOLAN en la siguiente
                ArrayList<Persona> personasProcesadas = actual.procesarTurno();
                int movidas = personasProcesadas.size();

                EstacionTrabajo siguiente = estaciones.get(i + 1);
                for (Persona p : personasProcesadas) {
                    siguiente.agregarPersona(p);
                }

                metricasHistorial.add(new RegistroTurno(
                        turnoActual + 1, actual.getIdEstacion(), capacidadDado, movidas, actual.getCantidadEnCola(), 0
                ));

            } else {
                // Estacion 10: DESENCOLA personas para sacarlas del sistema y calcula tiempos de ciclo
                ArrayList<Persona> personasProcesadas = actual.procesarTurno();
                int movidas = personasProcesadas.size();

                totalUnidadesCompletadas += movidas;

                for (Persona p : personasProcesadas) {
                    tiemposEnSistema.add(p.calcularTiempoEnSistema(turnoActual + 1));
                }

                metricasHistorial.add(new RegistroTurno(
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

    public ArrayList<RegistroTurno> getMetricasHistorial() {
        return metricasHistorial;
    }

    public ArrayList<Integer> getTiemposEnSistema() {
        return tiemposEnSistema;
    }

    public int getTotalUnidadesCompletadas() {
        return totalUnidadesCompletadas;
    }
}