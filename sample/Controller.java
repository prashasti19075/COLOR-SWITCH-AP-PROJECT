//package sample;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.event.ActionEvent;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.media.MediaView;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.media.Media;
//import javafx.stage.Stage;
//import java.io.File;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.Group;
//import javafx.scene.media.MediaView;
//import static javafx.fxml.FXMLLoader.load;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//public class Controller{
//    private int totalstars=0;
//    private static Scene s2;
//    private String videopath = "src/video.mp4";
//    @FXML
//    MediaView videoP;
//    //@FXML
//    //MediaPlayer  videoPlayer;
//    /*@FXML
//    void bonusVideo() throws IOException {
//        System.out.println("Came here");
//        Scene videoPage = FXMLLoader.load(getClass().getResource("video.fxml"));
//        Media video = new Media(new File(videopath).toURI().toString());
//        MediaPlayer videoPlayer = new MediaPlayer(video);
//        videoP=new MediaView(videoPlayer);
//        videoP.setMediaPlayer(videoPlayer);
//        Main.window.setScene(videoPage);
//        Main.window.show();
//    }*/
//    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
//        System.out.println("Came here");
//
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("video.fxml"));
//        Media video = new Media(new File(videopath).toURI().toString());
//        MediaPlayer videoPlayer = new MediaPlayer(video);
//        videoP=new MediaView(videoPlayer);
//        videoP.setMediaPlayer(videoPlayer);
//        //Main.window.setScene(videoPage);
//
//        Parent root1 = fxmlLoader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root1));
//        stage.show();
//
//        /*Media video = new Media(new File(videopath).toURI().toString());
//        MediaPlayer videoPlayer = new MediaPlayer(video);
//        videoP=new MediaView(videoPlayer);
//        videoP.setMediaPlayer(videoPlayer);*/
//        //Scene videoPage = FXMLLoader.load(getClass().getClassLoader().getResource("video.fxml"));
//        //Parent root = FXMLLoader.load(getClass().getResource("/video.fxml"));
//        /*s2= load(getClass().getResource("sample.fxml"));
//        Main.window.setScene(videoPage);
//        Main.window.show();*/
//    }
//    /*void onButtonClick(){
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("video.fxml"));
//            Media video = new Media(new File(videopath).toURI().toString());
//            MediaPlayer videoPlayer = new MediaPlayer(video);
//            videoP=new MediaView(videoPlayer);
//            videoP.setMediaPlayer(videoPlayer);
//            //Main.window.setScene(videoPage);
//
//            Parent root1 = (Parent) fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root1));
//            stage.show();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }*/
//}

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

public class Controller implements Initializable {
    private int totalstars=0;
    Scene videoscene;
    private static MediaPlayer mediaPlayer;
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
        Scene videoPage = FXMLLoader.load(getClass().getResource("video.fxml"));
        Main.window.setScene(videoPage);
    }
    @FXML
    void back() throws IOException{
        System.out.println("Back to Main");
        Scene MainPage = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.window.setScene(MainPage);
    }
}

