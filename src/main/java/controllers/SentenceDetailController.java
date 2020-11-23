package controllers;

import db.DBManager;
import entities.Sentence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import windows.SentenceDetailWindow;

import java.net.URL;
import java.util.ResourceBundle;

public class SentenceDetailController implements Initializable
{
    private Sentence sentence;

    @FXML TextArea text;
    @FXML TextArea translation;

    public SentenceDetailController(Sentence sentence) {
        this.sentence = sentence;
    }

    public void handleSubmit(ActionEvent actionEvent)
    {
        sentence.setText(text.getText());
        sentence.setTranslation(translation.getText());
        DBManager.saveSentence(sentence);
        SentenceDetailWindow.close();
    }

    @Override public void initialize(URL location, ResourceBundle resources)
    {
        if(sentence == null) {
            sentence = new Sentence();
        }
        text.setText(sentence.getText());
        translation.setText(sentence.getTranslation());
    }
}
