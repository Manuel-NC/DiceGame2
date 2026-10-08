package com.example.dicegame2.Vista;

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
 * Grafica de Tiempo en Sistema: tiempo de permanencia de cada elemento completado.
 */
public class GraficaTiempoEnSistema extends Stage {
    public GraficaTiempoEnSistema(SimuladorEstaciones modelo) {
        setTitle("Tiempo en el Sistema (Time in System)");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Orden de Llegada (Order of Arrival)");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Tiempo en Sistema (Turnos)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Tiempo en Sistema por Unidad Completada");
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        ArrayList<Integer> tiempos = modelo.getTiemposEnSistema();

        for (int i = 0; i < tiempos.size(); i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i + 1), tiempos.get(i)));
        }

        barChart.getData().add(series);

        VBox root = new VBox(barChart);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 750, 480);
        setScene(scene);
    }
}
