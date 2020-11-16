package sample;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Group;
import javafx.scene.media.MediaView;
import static javafx.fxml.FXMLLoader.load;

public class Main extends Application {
    //    @Override
    private static Scene sc;
    static Stage window;
    private static MediaPlayer mediaPlayer;
    public void start(Stage primaryStage) throws Exception{
        sc= load(getClass().getResource("sample.fxml"));
        window=primaryStage;
        window.setTitle("Colour Switch Game");


        String path = "src/Ipsi.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer= new MediaPlayer(media);
        //mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();

        window.setMaximized(true);
        window.setScene(sc);
        window.show();


        //String pathv = "src/video.mp4";
        /*Media video = new Media(new File(path).toURI().toString());
        MediaPlayer videoPlayer = new MediaPlayer(video);
        MediaView mediaView = new MediaView (videoPlayer);
        Group root = new Group();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root,600,400);
        primaryStage.setTitle("Playing Video");
        primaryStage.show();*/

    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
