package sample;

import javafx.application.Platform;
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
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import static javafx.fxml.FXMLLoader.load;

public class Controller implements Initializable {
    @FXML
    MediaPlayer musicPlayer;
    @FXML
    TextArea Star_Label;
    @FXML
    Button videobutton;
    @FXML
    Button New_Game;
    @FXML
    Button Resume_Game;
    @FXML
    Button Exit_Game;
    //IMAGES
    @FXML
    ImageView Background_img;
    @FXML
    ImageView Title_img;
    @FXML
    ImageView New_Game_img;
    @FXML
    ImageView Resume_Game_img;
    @FXML
    ImageView Exit_Game_img;

    public void initialize(URL location, ResourceBundle resources) {
        musicPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                musicPlayer.seek(Duration.ZERO);
                musicPlayer.play();
            }
        });
        System.out.println(Main.colorswitch.is_video());
        if (Main.colorswitch.is_video()) {
            videobutton.setDisable(false);
        } else {
            videobutton.setDisable(true);
        }
        Star_Label.setText("" + Main.colorswitch.get_stars());
    }

    @FXML
    void bonusVideo() throws IOException {
        System.out.println("Bonus Availed");
        Main.colorswitch.setVideo();
        musicPlayer.pause();
        Main.colorswitch.bonusstars();
        Scene videoPage = FXMLLoader.load(getClass().getResource("video.fxml"));
        Main.window.setScene(videoPage);
        // ********* ADD THIS Star_Label.setText(Integer.toString(curr_stars+30));
    }

    @FXML
    void Quit() {
        Platform.exit();
    }
    @FXML
    void Begin_New() throws IOException {
        musicPlayer.pause();
       Scene gamepage = FXMLLoader.load(getClass().getResource("Game_Page.fxml"));
        Main.window.setScene(gamepage);
    }
    @FXML
    void Resume_old() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(Main.window);
    }
}