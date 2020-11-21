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
    int total_stars;
    @FXML
    Button Revive;
    @FXML
    Button Back_to_Menu;
    @FXML
    Button Star_Label;
    @FXML
    public void Back() throws IOException
    {
        Scene homepage = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.window.setScene(homepage);
    }
    @FXML
    public void Revive()
    {
        //load
       int reduce= Main.colorswitch.mygame.DisplayRevive(Revive,total_stars);
       Main.colorswitch.updatestars(-reduce);
        int appstars=Main.colorswitch.get_stars();
        int currstars=Main.colorswitch.mygame.retStars();
        total_stars=appstars+currstars;
        Star_Label.setText("SCORE: "+total_stars);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int appstars=Main.colorswitch.get_stars();
        int currstars=Main.colorswitch.mygame.retStars();
        total_stars=appstars+currstars;
        Star_Label.setText("SCORE: "+total_stars);

    }
}
