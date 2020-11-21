package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

import java.io.IOException;

public class Pause_Controller{

    @FXML
    public void Menu() throws IOException {
        Scene homepage = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.window.setScene(homepage);
    }
    @FXML
    public void resume()
    {

    }
    @FXML
    public void SaveandExit(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        fileChooser.showOpenDialog(Main.window);
    }
}
