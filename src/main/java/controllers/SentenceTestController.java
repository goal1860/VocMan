package controllers;

import db.DBManager;
import entities.Sentence;
import helpers.DiffHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SentenceTestController implements Initializable
{
    public static final int QUERY_SIZE = 5;
    List<Label> labelList = new ArrayList<>();
    List<WebView> webViewList = new ArrayList<>();
    List<TextField> textFieldList = new ArrayList<>();
    List<Sentence> sentenceList;
    @FXML VBox vb;

    public void handleSubmit(ActionEvent actionEvent)
    {
//        wv1.getEngine().loadContent(DiffHelper.diff("This is a test senctence.", "This is a test for diffutils."), "text/html");
        for (int i = 0; i < QUERY_SIZE; i++) {
            String answer = textFieldList.get(i).getText();
            String result = DiffHelper.diff(answer, sentenceList.get(i).getText());
            webViewList.get(i).getEngine().loadContent(result, "text/html");
        }
    }

    @Override public void initialize(URL location, ResourceBundle resources)
    {
        sentenceList = DBManager.getSentenceQuizList(QUERY_SIZE);
        for (int i = 0; i < QUERY_SIZE ; i++) {
            Label label = new Label();
            label.setText(sentenceList.get(i).getTranslation());
            TextField tf = new TextField();
            textFieldList.add(tf);
            WebView wbv = new WebView();
            wbv.setPrefHeight(30D);
            wbv.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/sentencetest.css").toString());
            webViewList.add(wbv);
            vb.getChildren().addAll(label, tf, wbv);
        }

    }
}
