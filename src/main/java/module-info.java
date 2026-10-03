module com.example.dicegame2 {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;


    opens com.example.dicegame2 to javafx.graphics;
    exports com.example.dicegame2;
    exports com.example.dicegame2.Vista;
    exports com.example.dicegame2.Controlador;
    exports com.example.dicegame2.Modelo;
}