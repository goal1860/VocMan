package windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TestSentenceWindow
{
    private static Stage window;

    public static void display(){
        window = new Stage();
        try {
            Parent editRoot = FXMLLoader.load(AddWordWindow.class.getResource("/fxml/sentence_test.fxml"));
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Test Sentences");
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
