package com.example.dicegame2;

import com.example.dicegame2.Controlador.SimuladorControlador;
import com.example.dicegame2.Modelo.SimuladorEstaciones;
import com.example.dicegame2.Vista.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SimuladorGUI extends Application {
    private SimuladorEstaciones modelo;
    private SimuladorControlador controlador;
    private ArrayList<VistaEstacion> vistasEstaciones;
    private Label lblTurno;
    private Label lblTotal;
    private Button btnAccion;
    private Button btnPerformance;
    private boolean esFaseTirar;

    @Override
    public void start(Stage primaryStage) {
        this.modelo = new SimuladorEstaciones();
        this.controlador = new SimuladorControlador(modelo);
        this.vistasEstaciones = new ArrayList<>();
        this.esFaseTirar = true;

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // --- TABLERO EN FORMA DE U ---
        GridPane gridEstaciones = new GridPane();
        gridEstaciones.setHgap(15);
        gridEstaciones.setVgap(15);
        gridEstaciones.setAlignment(Pos.CENTER);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            VistaEstacion vista = new VistaEstacion(i + 1);
            vista.setOnMouseClicked(e -> controlador.seleccionarEstacion(index, vistasEstaciones));
            vistasEstaciones.add(vista);
        }

        gridEstaciones.add(vistasEstaciones.get(0), 0, 0);
        gridEstaciones.add(vistasEstaciones.get(1), 1, 0);
        gridEstaciones.add(vistasEstaciones.get(2), 2, 0);
        gridEstaciones.add(vistasEstaciones.get(3), 3, 0);

        gridEstaciones.add(vistasEstaciones.get(4), 3, 1);
        gridEstaciones.add(vistasEstaciones.get(5), 3, 2);

        gridEstaciones.add(vistasEstaciones.get(6), 3, 3);
        gridEstaciones.add(vistasEstaciones.get(7), 2, 3);
        gridEstaciones.add(vistasEstaciones.get(8), 1, 3);
        gridEstaciones.add(vistasEstaciones.get(9), 0, 3);

        root.setCenter(gridEstaciones);

        // --- PANEL DE CONTROL Y GRÁFICAS ---
        VBox panelControl = new VBox(10);
        panelControl.setPadding(new Insets(15));
        panelControl.setAlignment(Pos.CENTER);
        panelControl.setPrefWidth(210);
        panelControl.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-background-color: #f8f9fa; -fx-border-radius: 8;");

        lblTurno = new Label("Turnos\n0");
        lblTurno.setAlignment(Pos.CENTER);
        lblTurno.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-alignment: center;");

        lblTotal = new Label("Personas registradas: 0");
        lblTotal.setStyle("-fx-font-size: 11px;");

        btnAccion = new Button("Tirar");
        btnAccion.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-color: #d1c7bd; -fx-text-fill: #3b2219;");
        btnAccion.setMaxWidth(Double.MAX_VALUE);

        // Botones de las Gráficas
        Button btnActivity = new Button("Activity");
        Button btnThroughput = new Button("Throughput");
        Button btnNumSystem = new Button("Number in system");
        Button btnTimeSystem = new Button("Time in system");
        btnPerformance = new Button("Your performance");

        btnPerformance.setDisable(true); // Se habilita solo al terminar el juego

        btnActivity.setMaxWidth(Double.MAX_VALUE);
        btnThroughput.setMaxWidth(Double.MAX_VALUE);
        btnNumSystem.setMaxWidth(Double.MAX_VALUE);
        btnTimeSystem.setMaxWidth(Double.MAX_VALUE);
        btnPerformance.setMaxWidth(Double.MAX_VALUE);

        btnActivity.setOnAction(e -> new GraficaActividad(modelo).show());
        btnThroughput.setOnAction(e -> new GraficaThroughput(modelo).show());
        btnNumSystem.setOnAction(e -> new GraficaNumeroEnSistema(modelo).show());
        btnTimeSystem.setOnAction(e -> new GraficaTiempoEnSistema(modelo).show());
        btnPerformance.setOnAction(e -> new GraficaRendimiento(modelo).show());

        btnAccion.setOnAction(e -> {
            if (esFaseTirar) {
                controlador.lanzarDados(vistasEstaciones);
                btnAccion.setText("Mover");
                esFaseTirar = false;
            } else {
                controlador.procesarMovimiento(vistasEstaciones);

                int turnoActual = modelo.getTurnoActual();
                lblTotal.setText("Personas registradas: " + modelo.getTotalUnidadesCompletadas());

                if (turnoActual >= 20) {
                    btnAccion.setDisable(true);
                    btnAccion.setText("Finalizado");
                    lblTurno.setText("Turnos\n20 (Fin)");
                    btnPerformance.setDisable(false); // Habilitar grafica de rendimiento
                } else {
                    lblTurno.setText("Turnos\n" + turnoActual);
                    btnAccion.setText("Tirar");
                    esFaseTirar = true;
                }
            }
        });

        panelControl.getChildren().addAll(btnActivity, btnThroughput, btnNumSystem, btnTimeSystem, btnPerformance, lblTurno, lblTotal, btnAccion);
        root.setRight(panelControl);

        controlador.actualizarVistas(vistasEstaciones);

        Scene scene = new Scene(root, 950, 640);
        primaryStage.setTitle("The Dice-Game 2 - Simulación de Línea de Producción");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
