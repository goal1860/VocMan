package controllers;

import db.DBManager;
import entities.StudyResult;
import entities.Word;
import helpers.AlertHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import windows.LearnWindown;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CardController implements Initializable
{
    private ObservableList<Word> wordList;
    private int index = 0;
    private Word activeWord;
    private static final int DECKSIZE = 30;
    private int total;
    private int studied = 0;
    private int remaining;
    private int passed = 0;
    private int failed = 0;

    @FXML Label lbl_numb_cards;
    @FXML Label lbl_front;
    @FXML Label lbl_back;
    @FXML ImageView star_white;
    @FXML ImageView star_filled;
    @FXML TextField tf_answer;
    @FXML Button btn_check_answer;
    @FXML List<Circle> circleList ;
    @FXML Label lbl_numb_answered;
    @FXML Label lbl_numb_remaining;
    @FXML Label lbl_numb_pass;
    @FXML Label lbl_numb_fail;
    @FXML Button btn_next;


    public void cancelAction(MouseEvent mouseEvent)
    {
        LearnWindown.close();
    }

    public void flipAllAction(MouseEvent mouseEvent)
    {
    }

    public void wrongAction(MouseEvent mouseEvent)
    {
    }

    public void rightAction(MouseEvent mouseEvent)
    {
    }

    public void flipAction(MouseEvent mouseEvent)
    {
        if (lbl_front.isVisible()) {
            lbl_front.setVisible(false);
            lbl_back.setVisible(true);
        } else {
            lbl_front.setVisible(true);
            lbl_back.setVisible(false);
        }
    }

    public void nextNewAction(MouseEvent mouseEvent)
    {
        Word found = wordList.stream().filter(w->w.getStudied() == StudyResult.NEW && !w.equals(activeWord)).findFirst().orElse(null);
        if(found == null) {
            AlertHelper.alertInfo("All words studied.", "All words have been studied");
        } else {
            activeWord = found;
            index = wordList.indexOf(activeWord);
            resetUI();
        }
    }

    public void nextAction(MouseEvent mouseEvent)
    {
        moveToNext();
    }

    private void moveToNext() {
        if (index < wordList.size() - 1) {
            index ++;
        } else {
            index = 0;
        }
        activeWord = wordList.get(index);
        resetUI();
    }

    public void previousAction(MouseEvent mouseEvent)
    {
    }

    public void enableTypeAction(MouseEvent mouseEvent)
    {
    }

    public void markStarredAction(MouseEvent mouseEvent)
    {
    }

    public void unmarkStarred(MouseEvent mouseEvent)
    {
    }

    @Override public void initialize(URL location, ResourceBundle resources)
    {
        wordList = FXCollections.observableArrayList(DBManager.getDeck(DECKSIZE));
        total = wordList.size();
        remaining = wordList.size();
        if(!wordList.isEmpty()) {
            index = 0;
            activeWord = wordList.get(0);

        }
        btn_next.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER)){
                moveToNext();
            }
        });
        resetUI();
    }

    public void handlePass(MouseEvent mouseEvent)
    {
        activeWord.increaseFamiliarity();
        updateStats(true);
        activeWord.setStudied(StudyResult.PASS);
        DBManager.saveWord(activeWord, false);
        resetUI(StudyResult.PASS);
    }

    public void handleFail(MouseEvent mouseEvent)
    {
        activeWord.decreaseFamiliarity();
        updateStats(false);
        activeWord.setStudied(StudyResult.FAIL);
        DBManager.saveWord(activeWord, false);
        resetUI(StudyResult.FAIL);
    }

    public void handleCheckAnswer(MouseEvent mouseEvent)
    {
        StudyResult result;
        if (tf_answer.getText().trim().toLowerCase().equals(activeWord.getText().toLowerCase())) {
            result = StudyResult.PASS;
            activeWord.increaseFamiliarity();
            updateStats(true);
        } else {
            result = StudyResult.FAIL;
            activeWord.decreaseFamiliarity();
            updateStats(false);
        }
        activeWord.setStudied(result);
        DBManager.saveWord(activeWord, false);
        resetUI(result);
    }

    private void resetUI(){
        resetUI(StudyResult.NEW);
    }

    private void resetUI(StudyResult answerResult) {
        if(answerResult.equals( StudyResult.NEW)) { // Not answered
            star_filled.setVisible(false);
            star_white.setVisible(false);
        }else if (answerResult .equals(StudyResult.PASS)){ // Correct
            star_white.setVisible(true);
            star_filled.setVisible(false);
        }else { // Wrong
            star_white.setVisible(false);
            star_filled.setVisible(true);
        }
        lbl_front.setText(activeWord.getExplanation());
        lbl_back.setText(activeWord.getText());
        if(activeWord.getExplanation().isEmpty() && activeWord.getExample().isEmpty()) {
            lbl_back.setVisible(true);
            lbl_front.setVisible(false);
            turnOffAnswerInput();
        }else {
            lbl_back.setVisible(false);
            lbl_front.setVisible(true);
            if(activeWord.getStudied() != StudyResult.NEW){
                turnOffAnswerInput();
            }else {
                turnOnAnswerInput();
            }
        }
        markCircles(activeWord.getFamiliarity());

        lbl_numb_cards.textProperty().set(String.valueOf(total));
        lbl_numb_answered.textProperty().set(String.valueOf(studied));
        lbl_numb_remaining.textProperty().set(String.valueOf(remaining));
        lbl_numb_pass.textProperty().set(String.valueOf(passed));
        lbl_numb_fail.textProperty().set(String.valueOf(failed));
        if(remaining == 0) {
            if(failed == 0) {
                AlertHelper.alertInfo("All words studied.", "Congratulations you have mastered all the words");
            } else {
                Optional<ButtonType> result = AlertHelper.alertConfirmation("All words studied.", "All words studied. Do you want to retry failed ones?");
                if(result.get() == ButtonType.OK) {
                    handleRetry();
                }
            }
        }
    }

    private void turnOnAnswerInput() {
        tf_answer.setEditable(true);
        btn_check_answer.setDisable(false);
        tf_answer.clear();
        tf_answer.requestFocus();
    }

    private void turnOffAnswerInput() {
        tf_answer.setEditable(false);
        btn_check_answer.setDisable(true);
        btn_next.requestFocus();
    }

    private void markCircles(int circles) {
        for(int i=0; i<5; i++) {
            if(i >= circles){
                circleList.get(i).setFill(Color.web("#ffffff"));
            }else {
                circleList.get(i).setFill(Color.web("#69115e"));
            }
        }
    }

    private void updateStats(boolean correct) {
        studied++;
        remaining--;
        if (correct) {
            passed++;
        } else {
            failed++;
        }
    }

    private void handleRetry() {
        wordList = FXCollections.observableArrayList(wordList.stream().filter(w->w.getStudied().equals(StudyResult.FAIL)).collect(Collectors.toList()));
        wordList.stream().forEach(word -> word.setStudied(StudyResult.NEW));
        total = wordList.size();
        remaining = total;
        passed = 0;
        failed = 0;
        studied = 0;
        activeWord = wordList.get(0);
        resetUI();
    }

    @FXML
    public void onEnter(ActionEvent actionEvent)
    {
        handleCheckAnswer(null);
    }
}
