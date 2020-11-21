package sample;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Game_Controller<e> implements Initializable {
    boolean not_enable=true;
    @FXML
    MediaPlayer music;
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
    Canvas canvas;
    public static Timeline timeline;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Main.colorswitch.mygame.set_gui(this);
        Main.colorswitch.mygame.DisplayScore();
        Main.colorswitch.mygame.display();
        music.setOnEndOfMedia(new Runnable() {
            public void run() {
                music.seek(Duration.ZERO);
                music.play();
            }
        });
        timeline= new Timeline(new KeyFrame(Duration.seconds(0.025), ev->
        {
            Main.colorswitch.mygame.playgame();
            System.out.println("Here2");

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


        RotateTransition rt = new RotateTransition(Duration.millis(3000), Obstacle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
    @FXML
    public void pause() throws IOException
    {
        System.out.println("Pause Pressed");
        timeline.pause();
        Main.colorswitch.mygame.DisplayPause(music);
    }
    @FXML
    public void check()
    {
        System.out.println("Ab call honge maharaj");
        System.out.println("Here1");
        timeline.pause();
        Timeline moveup = new Timeline(new KeyFrame(Duration.seconds(0.025),ev2->
        {
            Ball.setCenterY(Ball.getCenterY()-30);
        }));
        moveup.setCycleCount(1);
        moveup.play();
        timeline.play();

    }
    @FXML
    public void check2(){

    }
    public void collision(ImageView a, Circle b) {
        if (a.getBoundsInLocal().intersects(b.getBoundsInLocal())) {
            System.out.println("Touched");
        }
    }
}
