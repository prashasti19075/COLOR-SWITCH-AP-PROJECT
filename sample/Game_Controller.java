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
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Game_Controller<e> implements Initializable {
    boolean not_enable=false;
    @FXML
    MediaPlayer music;
    @FXML
    MediaPlayer star_collide;
    @FXML
    Group scroll_element;

    @FXML
    Group scroll_element2;
    @FXML
    ImageView Obstacle2;
    @FXML
    ImageView color_switcher2;
    @FXML
    ImageView star2;
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
        Main.colorswitch.mygame.set_gui(this);
        Main.colorswitch.mygame.DisplayScore();
        Main.colorswitch.mygame.display();
        Ball.setCenterY(0.0);
        music.setOnEndOfMedia(() -> {
            music.seek(Duration.ZERO);
            music.play();
        });

        timeline= new Timeline(new KeyFrame(Duration.seconds(0.025), ev->
        {
//            System.out.println(" Y (layout): "+Ball.getLayoutY()+"Star Y (getY) "+(star.getLayoutY()+scroll_element.getTranslateY()));
            Main.colorswitch.mygame.playgame();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        if(not_enable==true)
        {
            timeline.play();
        }
        Main.colorswitch.mygame.DisplayObstacles();
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
        timeline.pause();
        if(not_enable==false) {
            not_enable = true;
            Hand.setVisible(false);
        }
        if(scrollcounter==0){
            Timeline move_ball = new Timeline(new KeyFrame(Duration.seconds(0.025), ev2 ->
            {
                Ball.setLayoutY(Ball.getLayoutY() - 20);
            }));
            move_ball.setCycleCount(1);
            move_ball.play();
        }
            System.out.println("Reached");
            TranslateTransition translate = new TranslateTransition(Duration.millis(10),scroll_element);
            translate.setByY(30);
            translate.setCycleCount(1);
            translate.play();

            TranslateTransition translate2 = new TranslateTransition(Duration.millis(10),scroll_element2);
           translate2.setByY(30);
          translate2.setCycleCount(1);
          translate2.play();
          timeline.play();

    }
    @FXML
    public void check2(){

    }

}
