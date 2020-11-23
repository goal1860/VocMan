package windows;

import controllers.DetailController;
import controllers.SentenceDetailController;
import entities.Sentence;
import entities.Word;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SentenceDetailWindow
{
    private static Stage window;


    public static void display(Sentence sentence){
        window = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SentenceDetailWindow.class.getResource("/fxml/sentence_detail.fxml"));
            fxmlLoader.setController(new SentenceDetailController(sentence));
            Parent editRoot = fxmlLoader.load();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Edit sentence");
            window.setScene(new Scene(editRoot, 400, 500));
            window.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(){
        window.close();
    }

}
