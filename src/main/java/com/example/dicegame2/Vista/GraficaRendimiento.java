package com.example.dicegame2.Vista;

import com.example.dicegame2.Modelo.SimuladorEstaciones;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Grafica de Rendimiento del Jugador: resumen final al concluir la simulacion.
 */
public class GraficaRendimiento extends Stage {

    /**
     * Constructor que presenta la métrica final de la partida.
     * @param modelo Instancia del simulador.
     */
    public GraficaRendimiento(SimuladorEstaciones modelo) {
        setTitle("Tu Rendimiento (Your Performance)");

        Label lblTitulo = new Label("Resultado Final del Juego!");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lblResultado = new Label("Registraste un total de " + modelo.getTotalUnidadesCompletadas() + " personas en el sistema.");
        lblResultado.setStyle("-fx-font-size: 15px; -fx-text-fill: #2e7d32; -fx-font-weight: bold;");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Métrica");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Unidades");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Unidades Completadas", modelo.getTotalUnidadesCompletadas()));

        barChart.getData().add(series);

        VBox root = new VBox(15, lblTitulo, lblResultado, barChart);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 580, 430);
        setScene(scene);
    }
}
