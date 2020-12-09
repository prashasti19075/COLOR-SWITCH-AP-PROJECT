package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import static javafx.fxml.FXMLLoader.load;

public class Video_Controller implements Initializable
{
    @FXML
    MediaPlayer videoPlayer;
    @FXML
	Button Back_Button;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Back_Button.setDisable(true);
        videoPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    Back_Button.setDisable(false);
                }
            });
}
    @FXML
    void back() throws IOException 
    {
//        System.out.println("Back to Main");
        Scene MainPage = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.window.setScene(MainPage); 
    }
}