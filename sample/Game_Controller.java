package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Game_Controller implements Initializable {
    @FXML
    Circle Ball;
    @FXML
    Scene Game_Scene;
    @FXML
    ImageView a;
    @FXML
    Canvas canvas;
    Timeline timeline;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Bounds bounds = canvas.getBoundsInLocal();
            timeline= new Timeline(new KeyFrame(Duration.seconds(0.025),ev->
            {
                Ball.setCenterY(Ball.getCenterY()+3);
                if (Ball.getCenterY() >= bounds.getMaxY()) {
                    System.out.println("Out of bounds");
                    Scene gameover = null;
                    try {
                        timeline.pause();
                        System.out.println("Inside try");
                        gameover = FXMLLoader.load(getClass().getResource("Game_Over.fxml"));
                        Main.window.setScene(gameover);
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Outside try");

                }
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
    }
    @FXML
    public void check()
    {
       System.out.println("Key Pressed");
        Game_Scene.setOnKeyPressed(e ->
        {
            if (e.getCode() == KeyCode.UP) {
                System.out.println("UP key was pressed");
                timeline.pause();
                System.out.println("timeline - paused");
                Timeline moveup = new Timeline(new KeyFrame(Duration.seconds(0.025),ev->
                {
                    Ball.setCenterY(Ball.getCenterY()-50);
                }));
//                new KeyValue(Ball.layoutYProperty(), Ball.getLayoutY()-30)));
                moveup.setCycleCount(1);
                moveup.play();
                timeline.play();
                System.out.println("timeline-resumed");
            }
        });

    }
    public void collision(ImageView a, Circle b) {
        if (a.getBoundsInLocal().intersects(b.getBoundsInLocal())) {
            System.out.println("Touched");
        }
    }
}
