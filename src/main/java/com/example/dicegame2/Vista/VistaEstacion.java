package com.example.dicegame2.Vista;

import com.example.dicegame2.Modelo.Dado;
import com.example.dicegame2.Modelo.EstacionTrabajo;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Representacion visual de una estacion de trabajo.
 * Muestra el trabajador, dados rojos cuadrados y la cola de puntos azules.
 */
public class VistaEstacion extends VBox {
    private int idEstacion;
    private Label lblTitulo;
    private HBox contenedorDados;
    private HBox contenedorPuntosAzules;
    private boolean seleccionada;

    public VistaEstacion(int idEstacion) {
        this.idEstacion = idEstacion;
        this.seleccionada = false;

        this.lblTitulo = new Label("Estación " + idEstacion);
        this.lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        this.contenedorDados = new HBox(5);
        this.contenedorDados.setAlignment(Pos.CENTER);
        this.contenedorDados.setMinHeight(35);

        this.contenedorPuntosAzules = new HBox(3);
        this.contenedorPuntosAzules.setAlignment(Pos.CENTER);
        this.contenedorPuntosAzules.setMinHeight(15);

        this.setAlignment(Pos.CENTER);
        this.setSpacing(6);
        this.setStyle("-fx-border-color: #a0a0a0; -fx-border-width: 1.5; -fx-padding: 8; -fx-background-color: #ffffff; -fx-border-radius: 8; -fx-background-radius: 8;");
        this.setPrefWidth(125);
        this.setPrefHeight(135);

        this.getChildren().addAll(lblTitulo, contenedorDados, contenedorPuntosAzules);
    }

    /**
     * Redibuja la estacion creando los dados rojos y la fila de puntos azules.
     */
    public void actualizar(EstacionTrabajo estacion) {
        // Dibujar Dados Rojos
        this.contenedorDados.getChildren().clear();
        ArrayList<Dado> dados = estacion.getDados();

        if (dados.isEmpty()) {
            Label lblSinDado = new Label("Sin Dado");
            lblSinDado.setStyle("-fx-font-size: 10px; -fx-text-fill: #888888;");
            this.contenedorDados.getChildren().add(lblSinDado);
        } else {
            for (Dado d : dados) {
                this.contenedorDados.getChildren().add(crearFiguraDado(d.getValor()));
            }
        }

        // Dibujar Puntos Azules
        this.contenedorPuntosAzules.getChildren().clear();
        int cantidad = estacion.getCantidadEnCola();
        int puntosADibujar = Math.min(cantidad, 10);

        for (int i = 0; i < puntosADibujar; i++) {
            Circle puntoAzul = new Circle(4, Color.web("#1E90FF"));
            this.contenedorPuntosAzules.getChildren().add(puntoAzul);
        }

        if (cantidad > 10) {
            Label lblExtra = new Label("+" + (cantidad - 10));
            lblExtra.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #1E90FF;");
            this.contenedorPuntosAzules.getChildren().add(lblExtra);
        }
    }

    /**
     * Genera un cuadrado rojo con esquinas redondeadas y el numero blanco dentro.
     */
    private StackPane crearFiguraDado(int valor) {
        Rectangle cuadradoRojo = new Rectangle(28, 28);
        cuadradoRojo.setArcWidth(8);
        cuadradoRojo.setArcHeight(8);
        cuadradoRojo.setFill(Color.web("#e60000"));

        Label lblValor = new Label(String.valueOf(valor));
        lblValor.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        StackPane dadoPane = new StackPane();
        dadoPane.getChildren().addAll(cuadradoRojo, lblValor);
        return dadoPane;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
        if (seleccionada) {
            this.setStyle("-fx-border-color: #e60000; -fx-border-width: 3; -fx-padding: 8; -fx-background-color: #ffe6e6; -fx-border-radius: 8; -fx-background-radius: 8;");
        } else {
            this.setStyle("-fx-border-color: #a0a0a0; -fx-border-width: 1.5; -fx-padding: 8; -fx-background-color: #ffffff; -fx-border-radius: 8; -fx-background-radius: 8;");
        }
    }

    public int getIdEstacion() {
        return idEstacion;
    }
}