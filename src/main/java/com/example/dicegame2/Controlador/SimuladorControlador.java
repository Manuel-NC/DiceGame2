package com.example.dicegame2.Controlador;

import com.example.dicegame2.Modelo.SimuladorEstaciones;
import com.example.dicegame2.Vista.VistaEstacion;
import java.util.ArrayList;

/**
 * Controlador que gestiona la interaccion entre la interfaz grafica (Vista)
 * y la logica del simulador (Modelo).
 */
public class SimuladorControlador {
    private SimuladorEstaciones modelo;
    private int indiceOrigenSeleccionado;
    private boolean puedeMoverDados;

    /**
     * Constructor del controlador.
     * @param modelo Instancia principal del simulador de estaciones.
     */
    public SimuladorControlador(SimuladorEstaciones modelo) {
        this.modelo = modelo;
        this.indiceOrigenSeleccionado = -1;
        this.puedeMoverDados = true; // Al inicio (fase Tirar) se permite reasignar dados
    }

    /**
     * Maneja el clic sobre una estacion para transferir dados de lugar.
     * Bloquea la seleccion si el juego esta en la fase "Mover" (post-lanzamiento).
     * @param indiceEstacion Indice de la estacion clickeada.
     * @param vistasEstaciones Lista de vistas de las estaciones.
     */
    public void seleccionarEstacion(int indiceEstacion, ArrayList<VistaEstacion> vistasEstaciones) {
        // Si el boton dice "Mover" (post-lanzamiento), se bloquea el cambio de dados
        if (!puedeMoverDados) {
            return;
        }

        if (indiceOrigenSeleccionado == -1) {
            if (modelo.getEstaciones().get(indiceEstacion).tieneDados()) {
                indiceOrigenSeleccionado = indiceEstacion;
                vistasEstaciones.get(indiceEstacion).setSeleccionada(true);
            }
        } else if (indiceOrigenSeleccionado == indiceEstacion) {
            vistasEstaciones.get(indiceOrigenSeleccionado).setSeleccionada(false);
            indiceOrigenSeleccionado = -1;
        } else {
            modelo.moverDado(indiceOrigenSeleccionado, indiceEstacion);
            vistasEstaciones.get(indiceOrigenSeleccionado).setSeleccionada(false);
            indiceOrigenSeleccionado = -1;
            actualizarVistas(vistasEstaciones);
        }
    }

    /**
     * Paso 1 del turno: Lanza los dados y bloquea el movimiento de dados.
     * @param vistasEstaciones Lista de vistas a actualizar.
     */
    public void lanzarDados(ArrayList<VistaEstacion> vistasEstaciones) {
        modelo.lanzarDados();
        this.puedeMoverDados = false; // Desactiva movimiento de dados cuando el boton cambia a "Mover"

        // Cancela cualquier seleccion previa si la habia
        if (indiceOrigenSeleccionado != -1) {
            vistasEstaciones.get(indiceOrigenSeleccionado).setSeleccionada(false);
            indiceOrigenSeleccionado = -1;
        }
        actualizarVistas(vistasEstaciones);
    }

    /**
     * Paso 2 del turno: Procesa las personas en la linea y reactiva el movimiento de dados para el siguiente turno.
     * @param vistasEstaciones Lista de vistas a actualizar.
     */
    public void procesarMovimiento(ArrayList<VistaEstacion> vistasEstaciones) {
        modelo.avanzarTurno();
        this.puedeMoverDados = true; // Reactiva la posibilidad de mover dados para el nuevo turno
        actualizarVistas(vistasEstaciones);
    }

    /**
     * Sincroniza la representacion visual de todas las estaciones con los datos actuales del modelo.
     * @param vistasEstaciones Lista de las vistas a redibujar.
     */
    public void actualizarVistas(ArrayList<VistaEstacion> vistasEstaciones) {
        for (int i = 0; i < vistasEstaciones.size(); i++) {
            vistasEstaciones.get(i).actualizar(modelo.getEstaciones().get(i));
        }
    }

    public SimuladorEstaciones getModelo() {
        return modelo;
    }
}
