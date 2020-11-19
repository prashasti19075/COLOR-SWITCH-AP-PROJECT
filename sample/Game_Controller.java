package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Game_Controller implements Initializable {
    @FXML
    Circle Ball;
    @FXML
    ImageView a;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        while(true)
        {
            collision(a,Ball);
        }
    }


    @FXML
    public void check()
    {
       System.out.println("Key Pressed");
       Ball.setCenterY(Ball.getCenterY()-10);
    }

    public void collision(ImageView a, Circle b) {
        if (a.getBoundsInLocal().intersects(b.getBoundsInLocal())) {
            System.out.println("Touched");
        }
    }
}
