package windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SentenceListWindow
{
    private static Stage manageSentenceWindow;

    public static void display(){
        manageSentenceWindow = new Stage();
        try {
            Parent editRoot = FXMLLoader.load(AddWordWindow.class.getResource("/fxml/manage_sentence.fxml"));
            manageSentenceWindow.initModality(Modality.APPLICATION_MODAL);
            manageSentenceWindow.setTitle("Manage Sentences");
            manageSentenceWindow.setScene(new Scene(editRoot, 500, 500));
            manageSentenceWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(){
        manageSentenceWindow.close();
    }
}
