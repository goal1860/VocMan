package windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddWordWindow
{
    private static Stage editWindow;

    public static void display(){
        editWindow = new Stage();
        try {
            Parent editRoot = FXMLLoader.load(AddWordWindow.class.getResource("/fxml/add.fxml"));
            editWindow.initModality(Modality.APPLICATION_MODAL);
            editWindow.setTitle("Add a new word");
            editWindow.setScene(new Scene(editRoot, 400, 500));
            editWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(){
        editWindow.close();
    }
}
