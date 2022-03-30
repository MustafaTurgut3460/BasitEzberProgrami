package com.example.ezberyap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class WordPage implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private ImageView backButton;

    @FXML
    private TextField anlamTextField;

    @FXML
    private TextField kelimeTextField;

    @FXML
    private ListView<String> wordListView;

    @FXML
    private Button removeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("kelimeler.txt");

        setListItems(file, wordListView);

        backButton.setOnMouseClicked(mouseEvent -> {
            openPage("main_page.fxml");
        });

        addButton.setOnAction(actionEvent -> {
            // kelime ekle
            String kelime = kelimeTextField.getText();
            String anlam = anlamTextField.getText();

            addWordToListView(kelime, anlam, file);
            openPage("word_page.fxml");

        });

        removeButton.setOnAction(actionEvent -> {
            // secilen kelimeyi dosyadan sil
            String deletedWord = wordListView.getSelectionModel().getSelectedItem();
            removeItemFromList(deletedWord, file);
            openPage("word_page.fxml");
        });
    }

    private void setListItems(File file, ListView<String> listView)
    {
        try {
            Scanner scanner = new Scanner(file);
            String line;

            while (scanner.hasNext())
            {
                // dosyayi okumaya devam
                line = scanner.nextLine();

                listView.getItems().add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addWordToListView(String kelime, String anlam, File file)
    {
        String word = kelime + "-" + anlam + "\n";
        try {
            // dosyaya yaz
            Files.write(Paths.get(file.getPath()), word.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openPage(String fxml)
    {
        FXMLLoader FXML = new FXMLLoader(getClass().getResource(fxml));
        try
        {
            Stage stage = (Stage) anlamTextField.getScene().getWindow();
            Scene scene = new Scene(FXML.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void removeItemFromList(String word, File file)
    {
        ArrayList<String> yedekList = new ArrayList<>();
        // oku ve silinecek deger haric hepsini listeye at
        try {

            Scanner scanner = new Scanner(file);
            String line;

            while(scanner.hasNext())
            {
                line = scanner.nextLine();
                if(line.equals(word))
                    continue;

                yedekList.add(line);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        // dosyayi temizle
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // yeniden kelimeleri ekle dosyaya
        for(int i=0 ; i<yedekList.size() ; i++)
        {
            String kelime = yedekList.get(i) + "\n";
            try {
                Files.write(Paths.get(file.getPath()), kelime.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}



















