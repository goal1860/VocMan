package windows;

import controllers.DetailController;
import entities.Word;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailWindow
{
    private static Stage window;

    public static void display(Word word){
        window = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DetailWindow.class.getResource("/fxml/detail.fxml"));
            fxmlLoader.setController(new DetailController(word));
            Parent editRoot = fxmlLoader.load();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Edit word");
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
