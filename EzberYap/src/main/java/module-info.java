module com.example.ezberyap {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ezberyap to javafx.fxml;
    exports com.example.ezberyap;
}