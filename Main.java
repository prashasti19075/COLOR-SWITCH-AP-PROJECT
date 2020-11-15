package sample;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.File;


import javafx.scene.media.MediaPlayer;

import static javafx.fxml.FXMLLoader.load;

public class Main extends Application {
    //    @Override
    Scene sc;
    Scene videoscene;
    public void start(Stage primaryStage) throws Exception{
        sc= load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Colour Switch Game");
        String path = "src/Ipsi.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        String videopath = "src/video.mp4";
        Media video = new Media(new File(videopath).toURI().toString());
        MediaPlayer videoPlayer = new MediaPlayer(video);
        MediaView videoView = new MediaView(videoPlayer);

        primaryStage.setMaximized(true);
        primaryStage.setScene(sc);
        primaryStage.show();
        System.out.println(primaryStage.getMaxWidth());
        System.out.println(primaryStage.getMaxHeight());

//        back.setOnAction(new EventHandler<>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                mediaPlayer.play();
//                primaryStage.setScene(sc);
//                primaryStage.show();
//            }
//        });
//
//        earnStars.setOnAction(new EventHandler<ActionEvent>()
//        {
//            @Override
//            public void handle(ActionEvent e) {
//                mediaPlayer.pause();
//                videoPlayer.play();
//                primaryStage.setScene(videoscene);
//                videoPlayer.setOnEndOfMedia(new Runnable() {
//                    @Override
//                    public void run() {
//                        back.setDisable(false);
//                        return;
//                    }
//                });
//                return;
//            }
//        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
class App{
    private Image[] images;
    private Button[] buttons;
    private Media music;
    private int totalstars=0;
    //private Game mygame;
    private Media video;
    App()
    {
        images=new Image[10];
        buttons=new Button[10];
    }
}
