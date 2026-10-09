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
 * Grafica de Numero en el Sistema: muestra la cantidad total en colas por turno.
 */
public class GraficaNumeroEnSistema extends Stage {

    /**
     * Constructor que configura la grafica de inventario en proceso (WIP).
     * @param modelo Instancia del simulador.
     */
    public GraficaNumeroEnSistema(SimuladorEstaciones modelo) {
        setTitle("Número en el Sistema (Number in System)");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Turno");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Unidades en Sistema");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Total de Unidades Retenidas por Turno");
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        ArrayList<RegistroTurno> historial = modelo.getMetricasHistorial();

        for (int t = 0; t <= 20; t++) {
            int totalEnSistema = 0;
            if (t > 0) {
                for (RegistroTurno r : historial) {
                    if (r.getTurno() == t) {
                        totalEnSistema += r.getCantidadEnCola();
                    }
                }
            }
            series.getData().add(new XYChart.Data<>(String.valueOf(t), totalEnSistema));
        }

        barChart.getData().add(series);

        VBox root = new VBox(barChart);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 720, 480);
        setScene(scene);
    }
}
