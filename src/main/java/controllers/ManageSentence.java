package controllers;

import db.DBManager;
import entities.Sentence;
import entities.Word;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import windows.DetailWindow;
import windows.SentenceDetailWindow;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageSentence implements Initializable
{
    @FXML private TableView<Sentence> table;
    @Override public void initialize(URL location, ResourceBundle resources)
    {
        DBManager.loadTopSentences(100);
        TableColumn textCol = new TableColumn("Text");
        textCol.setCellValueFactory(
                        new PropertyValueFactory<Sentence, String>("text"));
        textCol.setMinWidth(200);
        TableColumn translationCol = new TableColumn("Translation");
        translationCol.setCellValueFactory(
                        new PropertyValueFactory<Sentence, String>("translation"));
        translationCol.setMinWidth(200);
        FilteredList<Sentence> flSentence = new FilteredList(DBManager.getSentenceData(), p -> true);//Pass the data to a filtered list
        table.setItems(flSentence);//Set the table's items using the filtered list
        table.getColumns().addAll(textCol, translationCol);
        handleDetail();
    }

    private void handleDetail() {
        table.setRowFactory(tv -> {
            TableRow<Sentence> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Sentence rowData = row.getItem();
                    SentenceDetailWindow.display(rowData);
                    table.refresh();
                }
            });
            return row ;
        });
    }

    public void handleAdd() {
        SentenceDetailWindow.display(null);
    }

    public void handleRefresh(ActionEvent actionEvent)
    {
        DBManager.loadTopSentences(100);
        FilteredList<Sentence> flPerson = new FilteredList(DBManager.getSentenceData(), p -> true);//Pass the data to a filtered list
        table.setItems(flPerson);
        table.refresh();
    }
}
