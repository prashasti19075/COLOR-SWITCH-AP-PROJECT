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

public class Controller {
    App colorswitch=new App();
    private int totalstars = 0;
    private final static String videopath = new File("src/video.mp4").getAbsolutePath();
    private Media video;
    @FXML
    private MediaPlayer videoPlayer;
    @FXML
    TextArea Star_Label;
    @FXML
    private Button videobutton;
    @FXML
    private Button New_Game;
    @FXML
    private Button Resume_Game;
    @FXML
    private Button Exit_Game;
    @FXML
    private Button Back_Button;
//IMAGES
    @FXML
    private ImageView Background_img;
    @FXML
    private ImageView Title_img;
    @FXML
    private ImageView New_Game_img;
    @FXML
    private ImageView Resume_Game_img;
    @FXML
    private ImageView Exit_Game_img;
    @FXML
    void bonusVideo() throws IOException {
        //videoPlayer.setAutoPlay(true);
        Main.mediaPlayer.pause();
        System.out.println("Bonus Availed");
        Scene videoPage = FXMLLoader.load(getClass().getResource("video.fxml"));
//        videoPlayer.setOnEndOfMedia(new Runnable() {
//            public void run() {
//                Back_Button.setDisable(false);
//            }
//        });
        Main.window.setScene(videoPage);
    }
    @FXML
    void back() throws IOException {
        int curr_stars=Integer.parseInt(Star_Label.getText());
        Star_Label.setText(Integer.toString(curr_stars+30));
        Main.mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                Main.mediaPlayer.seek(Duration.ZERO);
                Main.mediaPlayer.play();
            }
        });
        Main.mediaPlayer.play();
        System.out.println("Back to Main");
        Scene MainPage = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.window.setScene(MainPage);
        videobutton.setDisable(true);
    }

    class App implements Serializable {
        ImageView[] images;
        Button[] buttons;
        Media music;
        private int totalstars;
        Classes.Game mygame;
        Media video;
        TextArea Display_Stars;

        App() {
            images = new ImageView[5];
            buttons = new Button[4];
            buttons[0] = videobutton;
            buttons[1] = New_Game;
            buttons[2] = Resume_Game;
            buttons[3] = Exit_Game;

            images[0] = Background_img;
            images[1] = New_Game_img;
            images[2] = Resume_Game_img;
            images[3] = Exit_Game_img;
            images[4]=Title_img;
            totalstars=0;
            Display_Stars=Star_Label;

        }
        private void display()
        {
            Star_Label.setText(Integer.toString(totalstars));
        }


    }
}

