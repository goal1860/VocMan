package controllers;

import db.DBManager;
import entities.Word;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import windows.DetailWindow;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable
{
    @FXML TextField keyword;
    @FXML TableView<Word> table;

    public void handleSearch(ActionEvent actionEvent)
    {
        String searchText = keyword.getText().trim();
        if(!searchText.isEmpty()) {
            FilteredList<Word> flPerson = new FilteredList(DBManager.searchWord(searchText));//Pass the data to a filtered list
            table.setItems(flPerson);//Set the table's items using the filtered list
            handleDetail();
        }
    }

    private void handleDetail() {
        table.setRowFactory(tv -> {
            TableRow<Word> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Word rowData = row.getItem();
                    DetailWindow.display(rowData);
                    table.refresh();
                }
            });
            return row ;
        });
    }

    @Override public void initialize(URL location, ResourceBundle resources)
    {
        TableColumn textCol = new TableColumn("Text");
        textCol.setMinWidth(100);
        textCol.setCellValueFactory(
                        new PropertyValueFactory<Word, String>("text"));

        TableColumn expCol = new TableColumn("Explanation");
        expCol.setMinWidth(100);
        expCol.setCellValueFactory(
                        new PropertyValueFactory<Word, String>("shortExplanation"));

        table.getColumns().addAll(textCol, expCol);
    }
}
