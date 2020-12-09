package sample;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Game_Controller<e> implements Initializable {
    boolean not_enable=false;
    @FXML
    AnchorPane Main_Pane;
    @FXML
    MediaPlayer music;
    @FXML
    MediaPlayer star_collide;
    @FXML
    Group scroll_element;
    @FXML
    TextArea Star_Label;
    @FXML
    TextArea Level_Label;
    @FXML
    Button Pause_Button;
    @FXML
    Circle Ball;
    @FXML
    Scene Game_Scene;
    @FXML
    ImageView Obstacle;
    @FXML
    ImageView color_switcher;
    @FXML
    ImageView star;
    @FXML
    ImageView Hand;
    int scrollcounter=0;
    public static Timeline timeline;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
//        System.out.println("Initialise Started");
        Main.colorswitch.mygame.set_gui(this);
        Main.colorswitch.mygame.display_score();
        Main.colorswitch.mygame.display_level();

         music.setOnEndOfMedia(() -> {
            music.seek(Duration.ZERO);
            music.play();
        });
        Ball_Fall();
        Main.colorswitch.mygame.DisplayObstacles();
//        System.out.println("Initialise Finished");
    }
    @FXML
    public void pause() throws IOException
    {
//        System.out.println("Pause Pressed");
        timeline.pause();
        Main.colorswitch.mygame.DisplayPause(music);
    }
    @FXML
    public void check()
    {
        timeline.pause();
        if(not_enable==false)
        {
            not_enable = true;
            Hand.setVisible(false);
        }
//        System.out.println("not_enable: "+not_enable);
        Timeline move_ball = new Timeline(new KeyFrame(Duration.seconds(0.025), ev2 ->
        {
//            System.out.println(" Ball Move UP");
            Ball.setLayoutY(Ball.getLayoutY() - 10);
        }));
        move_ball.setCycleCount(1);
        move_ball.play();
        Main.colorswitch.mygame.Translate_UP();
        timeline.play();
    }
    public void Ball_Fall()
    {
        timeline= new Timeline(new KeyFrame(Duration.seconds(0.025), ev->
        {
//            System.out.println("Timeline is running");
            Main.colorswitch.mygame.playgame();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        if(not_enable==true) {
            timeline.play();
        }
//        System.out.println();
    }

}
