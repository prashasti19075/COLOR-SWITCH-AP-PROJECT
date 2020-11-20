package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;
public class Game_Over_Controller {
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

    }

}
