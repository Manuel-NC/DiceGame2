package com.example.dicegame2.Vista;

import com.example.dicegame2.Modelo.RegistroTurno;
import com.example.dicegame2.Modelo.SimuladorEstaciones;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Grafica de Actividad: muestra las unidades movidas (Moved) o dados tirados (Rolled)
 * por turno para una estacion individual o para todas.
 */
public class GraficaActividad extends Stage {
    private SimuladorEstaciones modelo;
    private BarChart<String, Number> barChart;
    private int estacionSeleccionada; // 0 = Todas, 1..10 = Estaciones
    private boolean mostrarMoved;      // true = Moved, false = Rolled

    public GraficaActividad(SimuladorEstaciones modelo) {
        this.modelo = modelo;
        this.estacionSeleccionada = 0;
        this.mostrarMoved = true;

        setTitle("Actividad (Activity)");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Turno");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Cantidad");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Actividad por Turno");
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);

        // Botones de modo: Moved vs Rolled
        Button btnMoved = new Button("Moved");
        Button btnRolled = new Button("Rolled");

        btnMoved.setOnAction(e -> { mostrarMoved = true; actualizarGrafica(); });
        btnRolled.setOnAction(e -> { mostrarMoved = false; actualizarGrafica(); });

        HBox panelModo = new HBox(10, btnMoved, btnRolled);
        panelModo.setAlignment(Pos.CENTER);

        // Botones para filtrar por Jugador/Estacion (1 a 10 y Todos)
        HBox panelJugadores = new HBox(5);
        panelJugadores.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 10; i++) {
            final int id = i;
            Button btnEstacion = new Button(String.valueOf(id));
            btnEstacion.setOnAction(e -> { estacionSeleccionada = id; actualizarGrafica(); });
            panelJugadores.getChildren().add(btnEstacion);
        }

        Button btnTodos = new Button("Todos");
        btnTodos.setOnAction(e -> { estacionSeleccionada = 0; actualizarGrafica(); });
        panelJugadores.getChildren().add(btnTodos);

        VBox root = new VBox(10, panelModo, barChart, panelJugadores);
        root.setPadding(new Insets(15));

        actualizarGrafica();

        Scene scene = new Scene(root, 780, 500);
        setScene(scene);
    }

    private void actualizarGrafica() {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(mostrarMoved ? "Moved" : "Rolled");

        ArrayList<RegistroTurno> historial = modelo.getHistorialMetrics();

        for (int t = 1; t <= 20; t++) {
            int suma = 0;
            for (RegistroTurno r : historial) {
                if (r.getTurno() == t) {
                    if (estacionSeleccionada == 0 || r.getIdEstacion() == estacionSeleccionada) {
                        suma += mostrarMoved ? r.getPersonasMovidas() : r.getValorDado();
                    }
                }
            }
            series.getData().add(new XYChart.Data<>(String.valueOf(t), suma));
        }

        barChart.getData().add(series);
    }
}