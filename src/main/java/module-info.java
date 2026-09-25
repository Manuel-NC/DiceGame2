module com.example.dicegame2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dicegame2 to javafx.fxml;
    exports com.example.dicegame2;
}