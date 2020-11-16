package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main_Controller implements Initializable {
        private int totalstars=0;
        private final static String videopath =new File("src/video.mp4").getAbsolutePath();
        private MediaPlayer videoPlayer;
        private  Media video;
        @FXML
        private MediaView Videop;
        @Override
        public void initialize(URL arg0, ResourceBundle arg1)
        {
            video=new Media(new File(videopath).toURI().toString());
            videoPlayer= new MediaPlayer(video);
            Videop=new MediaView(videoPlayer);
           // Videop.setMediaPlayer(videoPlayer);
            videoPlayer.setAutoPlay(true);
        }
        @FXML
        void bonusVideo() throws IOException {
            System.out.println("Bonus Availed");
            Scene videoPage = FXMLLoader.load(getClass().getResource("Video_Bonus.fxml"));
            Main.window.setScene(videoPage);
        }
        @FXML
        void back() throws IOException{
            System.out.println("Back to Main");
            Scene MainPage = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Main.window.setScene(MainPage);
        }
    }

