package com.example.ezberyap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class ExamPage implements Initializable {

    @FXML
    private TextField anlamTextField;

    @FXML
    private Label dogruLabel;

    @FXML
    private Label yanlisLabel;

    @FXML
    private Label sonucLabel;

    @FXML
    private Button tahminButton;

    @FXML
    private Label wordLabel;

    private int gosterilenKelime = 1;
    private int dogruSayisi = 0;
    private int randomIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("kelimeler.txt");

        ArrayList<String> kelimelerList = new ArrayList<>();
        ArrayList<String> anlamlarList = new ArrayList<>();

        addWordsToLists(kelimelerList, anlamlarList, file);

        int[] oldIndexArray = new int[kelimelerList.size()];

        showRandomWord(oldIndexArray, kelimelerList, wordLabel);

        tahminButton.setOnAction(actionEvent -> {
            String anlam = anlamTextField.getText();
            if(anlam.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Lütfen anlamı giriniz!");
                alert.show();
            }
            else
            {
                if(anlamlarList.get(randomIndex).equals(anlam))
                {
                    // dogru cevap
                    dogruSayisi++;
                    dogruLabel.setText("Doğru Cevap Sayisi: " + dogruSayisi);

                    // cevap sonucunu ayarla
                    sonucLabel.setTextFill(Color.GREEN);
                    sonucLabel.setText("Doğru Cevap!");
                }
                else
                {
                    // yanlis cevap
                    yanlisLabel.setText("Yanlış Cevap Sayisi: " + (gosterilenKelime-dogruSayisi-1));
                    sonucLabel.setTextFill(Color.RED);
                    sonucLabel.setText("Yanlış Cevap!");
                }
                anlamTextField.setText(""); // kelime bilindikten sonra textfield temizle
                showRandomWord(oldIndexArray, kelimelerList, wordLabel); // yeni kelime goster
            }
        });
    }

    private void addWordsToLists(ArrayList<String> kelimeler, ArrayList<String> anlamlar, File file)
    {
        try {
            Scanner scanner = new Scanner(file);
            String line;
            String[] word;

            while (scanner.hasNext())
            {
                line = scanner.nextLine();
                word = line.split("-");

                kelimeler.add(word[0]);
                anlamlar.add(word[1]);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showRandomWord(int[] oldIndexArray, ArrayList<String> kelimelerList, Label wordLabel)
    {
        Random random = new Random();
        boolean kontrol = true;

        if(!(gosterilenKelime >= kelimelerList.size()))
        {
            // eger butun kelimeler gosterilmediyse gostermeye devam et
            while(kontrol)
            {
                kontrol = false;
                randomIndex = random.nextInt(kelimelerList.size());

                for(int i=0 ; i<gosterilenKelime ; i++)
                {
                    if(randomIndex == oldIndexArray[i])
                    {
                        kontrol = true;
                        break;
                    }
                }
            }

            oldIndexArray[gosterilenKelime-1] = randomIndex;
            gosterilenKelime++;

            wordLabel.setText(kelimelerList.get(randomIndex));
        }
        else
        {
            int puan = (int) (((float)dogruSayisi/kelimelerList.size())*100);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Sınav Tamamlandı");
            alert.setContentText("Puan: " + puan);
            alert.show();
        }

    }
}




















