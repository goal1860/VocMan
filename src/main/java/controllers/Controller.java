package controllers;

import db.DBManager;
import entities.Word;
import javafx.animation.FadeTransition;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import windows.AddWordWindow;
import windows.DetailWindow;
import windows.LearnWindown;
import windows.SearchWindow;
import windows.SentenceListWindow;
import windows.TestSentenceWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class  Controller implements Initializable
{
    public ChoiceBox<String> choiceBox;
    public TableView<Word> table;

    @FXML
    private Group root;

    @Override public void initialize(URL location, ResourceBundle resources)
    {
//        loadSplashScreen();
        DBManager.init();
        DBManager.loadTopWords();
        choiceBox.getItems().addAll("First Name", "Last Name", "Email");
        choiceBox.setValue("First Name");

        TableColumn textCol = new TableColumn("Text");
        textCol.setMinWidth(100);
        textCol.setCellValueFactory(
                        new PropertyValueFactory<Word, String>("text"));

        TableColumn expCol = new TableColumn("Explanation");
        expCol.setMinWidth(100);
        expCol.setCellValueFactory(
                        new PropertyValueFactory<Word, String>("shortExplanation"));

        TableColumn catCol = new TableColumn("Category");
        catCol.setMinWidth(100);
        catCol.setCellValueFactory(
                        new PropertyValueFactory<Word, String>("category"));
        TableColumn exampleCol = new TableColumn("Example");
        exampleCol.setMinWidth(100 );
        exampleCol.setCellValueFactory(
                        new PropertyValueFactory<Word, String>("example"));
        FilteredList<Word> flPerson = new FilteredList(DBManager.getData(), p -> true);//Pass the data to a filtered list
        table.setItems(flPerson);//Set the table's items using the filtered list
        table.getColumns().addAll(textCol, expCol, catCol, exampleCol);
        handleDetail();
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

    public void handleAdd(){
//        System.out.println("handled");
        AddWordWindow.display();
        FilteredList<Word> flPerson = new FilteredList(DBManager.getData(), p -> true);//Pass the data to a filtered list
        table.setItems(flPerson);
        table.refresh();
    }

    private void loadSplashScreen() {
        try {
            StackPane pane = FXMLLoader.load(getClass().getResource("/fxml/splash.fxml"));
            root.getChildren().setAll(pane);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pane);
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            fadeIn.play();
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);
            fadeIn.setOnFinished(e->fadeOut.play());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRefresh(ActionEvent actionEvent)
    {
        DBManager.loadTopWords();
        FilteredList<Word> flPerson = new FilteredList(DBManager.getData(), p -> true);//Pass the data to a filtered list
        table.setItems(flPerson);
        table.refresh();
    }

    public void handleSearch(ActionEvent actionEvent)
    {
        SearchWindow.display();
    }

    public void handleLearn(ActionEvent actionEvent)
    {
        LearnWindown.display();
    }

    public void openManageSetencesWindow(ActionEvent actionEvent)
    {
        SentenceListWindow.display();
    }

    public void openTestSentencesWindow(ActionEvent actionEvent)
    {
        TestSentenceWindow.display();
    }
}
