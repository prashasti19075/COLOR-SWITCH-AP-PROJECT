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

public class Controller implements Initializable {
    Apps colorswitch = new Apps();
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
        int curr_stars = colorswitch.get_stars();
        Star_Label.setText(Integer.toString(curr_stars));
        musicPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                musicPlayer.seek(Duration.ZERO);
                musicPlayer.play();
            }
        });
        System.out.println(Classes.App.is_video());
        if (Classes.App.is_video())
        {

            videobutton.setDisable(false);
        }
        else
        {
            colorswitch.addBonus();
            videobutton.setDisable(true);
        }

    }

    @FXML
    void bonusVideo() throws IOException {
        System.out.println("Bonus Availed");
        Classes.App.setVideo();
        musicPlayer.pause();
        Scene videoPage = FXMLLoader.load(getClass().getResource("video.fxml"));
        Main.window.setScene(videoPage);
        // ********* ADD THIS Star_Label.setText(Integer.toString(curr_stars+30));
    }

    class Apps implements Serializable {
        ImageView[] images;
        Button[] buttons;

        Media music;
        private int totalstars;
        Classes.Game mygame;
        TextArea Display_Stars;

        Apps() {
            images = new ImageView[5];
            buttons = new Button[4];
            buttons[0]=videobutton;
            buttons[1]=New_Game;
            buttons[2]=Resume_Game;
            buttons[3]=Exit_Game;

            images[0]=Background_img;
            images[1]=New_Game_img;
            images[2]=Resume_Game_img;
            images[3]=Exit_Game_img;
            images[4]=Title_img;
            totalstars=0;
            Display_Stars=Star_Label;

        }

        private void display() {
            Display_Stars.setText(Integer.toString(totalstars));
        }
        public void addBonus()
        {
            totalstars+=30;
        }
        public int get_stars() {
            return totalstars;
        }

    }
}
