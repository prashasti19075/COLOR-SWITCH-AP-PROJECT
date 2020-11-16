package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.media.MediaPlayer;
import static javafx.fxml.FXMLLoader.load;

public class Main extends Application {
    //    @Override
   private static Scene sc;
   static MediaPlayer mediaPlayer;
   static Stage window=new Stage();
    public void start(Stage primaryStage) throws Exception{
        sc= load(getClass().getResource("sample.fxml"));
        window.setTitle("Colour Switch Game");
        String path = "src/Ipsi.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);


        window.setMaximized(true);
        window.setScene(sc);
        window.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}

