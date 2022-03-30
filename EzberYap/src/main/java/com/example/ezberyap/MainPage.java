package com.example.ezberyap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPage implements Initializable {

    @FXML
    private Pane examPane;

    @FXML
    private Pane wordPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        examPane.setOnMouseClicked(mouseEvent -> {
            // sinav sayfasina gonder
            openPage("exam_page.fxml");
        });

        wordPane.setOnMouseClicked(mouseEvent -> {
            // kelimeler sayfasina gonder
            openPage("word_page.fxml");
        });
    }

    public void openPage(String fxml)
    {
        FXMLLoader FXML = new FXMLLoader(getClass().getResource(fxml));
        try
        {
            Stage stage = (Stage) wordPane.getScene().getWindow();
            Scene scene = new Scene(FXML.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}



















