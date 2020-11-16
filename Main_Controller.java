package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main_Controller{
    private int totalstars=0;
    private String videopath = "src/video.mp4";
    @FXML
    MediaView videoP;
    @FXML
    void bonusVideo() throws IOException {
        System.out.println("Came here");
        Scene videoPage = FXMLLoader.load(getClass().getResource("videoscene.fxml"));
        Media video = new Media(new File(videopath).toURI().toString());
        MediaPlayer videoPlayer = new MediaPlayer(video);
        videoP=new MediaView(videoPlayer);
        videoP.setMediaPlayer(videoPlayer);
        Main.window.setScene(videoPage);
    }
}