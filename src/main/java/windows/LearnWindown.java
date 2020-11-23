package windows;

import controllers.DetailController;
import entities.Word;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LearnWindown
{
    private static Stage window;

    public static void display(){
        window = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DetailWindow.class.getResource("/fxml/card.fxml"));
            Parent cardRoot = fxmlLoader.load();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Learning words");
            window.setScene(new Scene(cardRoot));
            window.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(){
        window.close();
    }
}
