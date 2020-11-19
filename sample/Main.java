package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import java.io.Serializable;
import static javafx.fxml.FXMLLoader.load;

public class Main extends Application {
    static App colorswitch =new App();
   static Stage window=new Stage();
   static Scene Mainpage;
    public void start(Stage primaryStage) throws Exception{

        Mainpage = load(getClass().getResource("sample.fxml"));
        window.setTitle("Colour Switch Game");
        window.setMaximized(true);
        window.setScene(Mainpage);
        window.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
class App implements Serializable {
    ImageView[] images;
    Button[] buttons;
    Media music;
    private static int totalstars;
    Classes.Game mygame;
    static int video=0;
    Controller cont;
    App() {
        images = new ImageView[5];
        buttons = new Button[4];
        images = new ImageView[5];
        buttons = new Button[4];
//        buttons[0]=cont.videobutton;
//        buttons[1]=cont.New_Game;
//        buttons[2]=cont.Resume_Game;
//        buttons[3]=cont.Exit_Game;
//
//        images[0]=cont.Background_img;
//        images[1]=cont.New_Game_img;
//        images[2]=cont.Resume_Game_img;
//        images[3]=cont.Exit_Game_img;
//        images[4]=cont.Title_img;
    }
    public void setVideo() {
        video = 1;
    }
    public boolean is_video() {
        if (video == 0)
            return true;
        else
            return false;
    }
    public int get_stars()
    {
        return totalstars;
    }
    public void bonusstars()
    {
        totalstars+=30;
    }
    public void updatestars(int stars)
    {
        totalstars+=stars;
    }

}
