package com.example.dicegame2.Vista;

import com.example.dicegame2.Modelo.RegistroTurno;
import com.example.dicegame2.Modelo.SimuladorEstaciones;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Grafica de Throughput: muestra la produccion acumulada por turno.
 */
public class GraficaThroughput extends Stage {

    /**
     * Constructor que configura el diagrama BarChart de rendimiento acumulado.
     * @param modelo Instancia del simulador.
     */
    public GraficaThroughput(SimuladorEstaciones modelo) {
        setTitle("Rendimiento (Throughput)");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Turno");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Unidades Completadas Acumuladas");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Throughput del Sistema");
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        ArrayList<RegistroTurno> historial = modelo.getMetricasHistorial();

        int acumulado = 0;
        for (int t = 1; t <= 20; t++) {
            for (RegistroTurno r : historial) {
                if (r.getTurno() == t && r.getIdEstacion() == 10) {
                    acumulado += r.getPersonasCompletadas();
                }
            }
            series.getData().add(new XYChart.Data<>(String.valueOf(t), acumulado));
        }

        barChart.getData().add(series);

        VBox root = new VBox(barChart);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 720, 480);
        setScene(scene);
    }
}
