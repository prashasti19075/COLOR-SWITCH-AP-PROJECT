package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Game_Over_Controller implements Initializable {
    @FXML
    Button Revive;
    @FXML
    Button Back_to_Menu;
    @FXML
    public void Back() throws IOException {
        Scene homepage = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.window.setScene(homepage);
    }
    @FXML
    public void Revive()
    {
        //load
        Main.colorswitch.mygame.DisplayRevive(Revive);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
