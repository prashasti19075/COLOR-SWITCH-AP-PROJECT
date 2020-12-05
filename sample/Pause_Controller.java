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
        Game_Controller.timeline.play();
        Main.window.setScene(App.gamepage);
    }
    @FXML
    public void Restart() throws IOException {
        Main.colorswitch.mygame=new Game();
        Scene gamepage = FXMLLoader.load(getClass().getResource("Game_Page.fxml"));
        Main.window.setScene(gamepage);
    }
    @FXML
    public void SaveandExit(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        fileChooser.showOpenDialog(Main.window);
    }
}
