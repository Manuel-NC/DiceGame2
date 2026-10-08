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

/**
 * Punto de entrada principal de la aplicacion en JavaFX.
 * Coloca el tablero en U, el panel de control lateral con boton para lanzar dado, mover las personas
 * y gestiona el flujo principal.
 */
public class SimuladorGUI extends Application {
    private SimuladorEstaciones modelo;
    private SimuladorControlador controlador;
    private ArrayList<VistaEstacion> vistasEstaciones;
    private Label lblTurno;
    private Label lblTotal;
    private Button btnAccion;
    private boolean esFaseTirar;

    @Override
    public void start(Stage primaryStage) {
        this.modelo = new SimuladorEstaciones();
        this.controlador = new SimuladorControlador(modelo);
        this.vistasEstaciones = new ArrayList<>();
        this.esFaseTirar = true;

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // TABLERO EN FORMA DE U (10 Estaciones)
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

        // Fila Superior (Estaciones 1 a 4)
        gridEstaciones.add(vistasEstaciones.get(0), 0, 0);
        gridEstaciones.add(vistasEstaciones.get(1), 1, 0);
        gridEstaciones.add(vistasEstaciones.get(2), 2, 0);
        gridEstaciones.add(vistasEstaciones.get(3), 3, 0);

        // Lateral Derecho (Estaciones 5 y 6)
        gridEstaciones.add(vistasEstaciones.get(4), 3, 1);
        gridEstaciones.add(vistasEstaciones.get(5), 3, 2);

        // Fila Inferior (Estaciones 7 a 10 de derecha a izquierda)
        gridEstaciones.add(vistasEstaciones.get(6), 3, 3);
        gridEstaciones.add(vistasEstaciones.get(7), 2, 3);
        gridEstaciones.add(vistasEstaciones.get(8), 1, 3);
        gridEstaciones.add(vistasEstaciones.get(9), 0, 3);

        root.setCenter(gridEstaciones);

        // PANEL DE CONTROL LATERAL
        VBox panelControl = new VBox(15);
        panelControl.setPadding(new Insets(20));
        panelControl.setAlignment(Pos.CENTER);
        panelControl.setPrefWidth(200);
        panelControl.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-background-color: #f8f9fa; -fx-border-radius: 8;");

        lblTurno = new Label("Turnos\n0");
        lblTurno.setAlignment(Pos.CENTER);
        lblTurno.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-alignment: center;");

        lblTotal = new Label("Unidades completadas: 0");
        lblTotal.setStyle("-fx-font-size: 12px;");

        // Boton unico para conmutar entre "Tirar" y "Mover"
        btnAccion = new Button("Tirar");
        btnAccion.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10 25; -fx-background-color: #d1c7bd; -fx-text-fill: #3b2219;");
        btnAccion.setMaxWidth(Double.MAX_VALUE);

        btnAccion.setOnAction(e -> {
            if (esFaseTirar) {
                // Clic 1: Tirar dados (bloquea el movimiento de dados mientras diga "Mover")
                controlador.lanzarDados(vistasEstaciones);
                btnAccion.setText("Mover");
                esFaseTirar = false;
            } else {
                // Clic 2: Procesar avance y liberar movimiento de dados para el siguiente turno
                controlador.procesarMovimiento(vistasEstaciones);

                int turnoActual = modelo.getTurnoActual();
                lblTotal.setText("Unidades completadas: " + modelo.getTotalUnidadesCompletadas());

                if (turnoActual >= 20) {
                    btnAccion.setDisable(true);
                    btnAccion.setText("Finalizado");
                    lblTurno.setText("Turnos\n20 (Fin)");
                } else {
                    lblTurno.setText("Turnos\n" + turnoActual);
                    btnAccion.setText("Tirar");
                    esFaseTirar = true;
                }
            }
        });

        panelControl.getChildren().addAll(lblTurno, lblTotal, btnAccion);
        root.setRight(panelControl);

        controlador.actualizarVistas(vistasEstaciones);

        Scene scene = new Scene(root, 920, 620);
        primaryStage.setTitle("The Dice Game 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
