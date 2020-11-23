package controllers;

import db.DBManager;
import entities.Category;
import entities.Type;
import entities.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import windows.AddWordWindow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddController implements Initializable
{
    @FXML
    private TextField text;

    @FXML
    private TextArea explanation;

    @FXML
    private TextArea example;

    @FXML
    private ChoiceBox<Category> catChoice;

    public void handleSubmit() {
        Word word = new Word();
//        Category category = DBManager.getCategory(0);
        word.setCategory(catChoice.getValue());
        word.setText(text.getText());
        word.setExplanation(explanation.getText());
        word.setStatus(DBManager.getDefaultStatus());
        word.setExample(example.getText());
        DBManager.saveWord(word);
        AddWordWindow.close();
    }

    @Override public void initialize(URL location, ResourceBundle resources)
    {
        List<Category> categoryList = DBManager.loadAllCategories();
        catChoice.getItems().addAll(categoryList);
        catChoice.setValue(categoryList.get(0));
    }
}
