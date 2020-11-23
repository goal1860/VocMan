package controllers;

import db.DBManager;
import entities.Category;
import entities.Status;
import entities.Word;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import windows.DetailWindow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DetailController implements Initializable
{
    private Word word;

    @FXML private TextField text;
    @FXML private TextArea explanation;
    @FXML private TextArea example;
    @FXML private ChoiceBox<Category> catChoice;
    @FXML private Button learned;

    public DetailController(Word word) {
        this.word = word;
    }

    public StringProperty textProperty() {
        return new SimpleStringProperty(word==null?"":word.getText());
    }

    public void handleSubmit(ActionEvent actionEvent)
    {
        System.out.println(catChoice.getValue().getName());
        System.out.println(catChoice.valueProperty().get().getName());
        DBManager.saveWord(word);
        DetailWindow.close();
    }

    public void handleClose(ActionEvent actionEvent)
    {
    }

    public void handleDelete(ActionEvent actionEvent)
    {
        DBManager.deleteWord(word);
        DetailWindow.close();
    }

    public void handleMarkLearned(ActionEvent actionEvent)
    {
        word.setStatus(DBManager.getStatusById(word.getStatus().getId() == Status.LEARNED? Status.NEW : Status.LEARNED));
        DBManager.saveWord(word);
        setLearnedLabel();
    }

    @Override public void initialize(URL location, ResourceBundle resources)
    {
        List<Category> categoryList = DBManager.loadAllCategories();
        catChoice.getItems().addAll(categoryList);
        catChoice.setValue(categoryList.get(0));
        text.textProperty().bindBidirectional(word.textPropertyProperty());
        explanation.textProperty().bindBidirectional(word.explanationProperty());
        example.textProperty().bindBidirectional(word.getExampleProperty());
//        catChoice.valueProperty().bindBidirectional(word.categoryProperty());
        catChoice.setValue(categoryList.get(word.getCategory().getId()));
        catChoice.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent event)
            {
                word.setCategory(catChoice.getValue());
            }
        });
        setLearnedLabel();

//        System.out.println(catChoice.getValue().getName());
//        System.out.println(catChoice.valueProperty().get().getName());

//        catChoice.setValue(categoryList.get(catChoice.getValue().getId()));
//        int a = 0;
    }

    private void setLearnedLabel(){
        if (word.getStatus().getId().equals(Status.LEARNED)) {
            learned.setText("Unlearned");
        } else {
            learned.setText("Learned");
        }
    }
}
