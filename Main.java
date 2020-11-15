package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import javafx.scene.media.MediaPlayer;

public class Main extends Application {
    //    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Colour Switch Game");

        Image image = new Image(new FileInputStream("src\\colorswitch2.jpg"));
        BackgroundImage myBI= new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        String path = "Ipsi.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        String videopath = "video.mp3";
        Media video = new Media(new File(path).toURI().toString());
        MediaPlayer videoPlayer = new MediaPlayer(video);
        MediaView videoView = new MediaView(videoPlayer);
        videoPlayer.play();

        Image titleimage = new Image(new FileInputStream("src\\title.png"));
        ImageView titleimg = new ImageView(titleimage);
        titleimg.setX(0);
        titleimg.setY(0);
        titleimg.setFitHeight(300);
        titleimg.setFitWidth(300);
        titleimg.setPreserveRatio(true);
        Group root = new Group(titleimg);

        Image newg=new Image(new FileInputStream("src\\new_game.png"));
        ImageView iv1=new ImageView(newg);
        iv1.setFitHeight(67);
        iv1.setFitWidth(250);
        Button newGame = new Button("",iv1);
        newGame.setStyle("-fx-background-color: #000000;");

        Image resumeg=new Image(new FileInputStream("src\\resume_game.png"));
        ImageView iv2=new ImageView(resumeg);
        iv2.setFitHeight(67);
        iv2.setFitWidth(250);
        Button resumeGame = new Button("",iv2);
        resumeGame.setStyle("-fx-background-color: #000000;");

        Image quitg=new Image(new FileInputStream("src\\exit_game.png"));
        ImageView iv3=new ImageView(quitg);
        iv3.setFitHeight(67);
        iv3.setFitWidth(250);
        Button quitGame = new Button("",iv3);
        quitGame.setStyle("-fx-background-color: #000000;");

       // String musicFile = "Ipsi.mp3";
        VBox vBox = new VBox();
        vBox.setBackground(new Background(myBI));
        vBox.setSpacing(10);


        vBox.getChildren().add(root);
        vBox.getChildren().add(newGame);
        vBox.getChildren().add(resumeGame);
        vBox.getChildren().add(quitGame);

        vBox.setMargin(root, new Insets(0, 150, -250, 500));
        vBox.setMargin(newGame, new Insets(250, 150, 20, 500));
        vBox.setMargin(resumeGame, new Insets(50, 20, 20, 500));
        vBox.setMargin(quitGame, new Insets(50, 20, 20, 500));

        // create a scene
        Scene sc = new Scene(vBox, 1200, 500);
        primaryStage.setScene(sc);
        primaryStage.setMaximized(true);

        primaryStage.show();
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
