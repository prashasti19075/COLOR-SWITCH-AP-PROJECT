package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;
import static javafx.fxml.FXMLLoader.load;

public class Main extends Application {
    static App colorswitch =new App();
    static Stage window;
    static Scene Mainpage;
    public void start(Stage primaryStage) throws Exception{
        window=new Stage();
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
    MediaPlayer music;
    private static int totalstars;
    Game mygame;
    static int video=0;
    Controller cont;
    App() {
        images = new ImageView[5];
        buttons = new Button[4];
        images = new ImageView[5];
        buttons = new Button[4];
        mygame=new Game();}
        public void set_gui(Controller cont)
        {
        buttons[0]=cont.videobutton;
        buttons[1]=cont.New_Game;
        buttons[2]=cont.Resume_Game;
        buttons[3]=cont.Exit_Game;

        images[0]=cont.Background_img;
        images[1]=cont.New_Game_img;
        images[2]=cont.Resume_Game_img;
        images[3]=cont.Exit_Game_img;
        images[4]=cont.Title_img;

        music=cont.musicPlayer;
        }
        public void exit_game()
        {
            Platform.exit();
        }
        public void new_game() throws IOException
        {
            music.pause();
            Scene gamepage = FXMLLoader.load(getClass().getResource("Game_Page.fxml"));
            Main.window.setScene(gamepage);
        }
    public void setVideo()
    {
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
    public void resume_game()
    {
        //deserialise the game and loads of functions
    }
}

class Game
{
    private MediaPlayer[] sounds;
    private TextArea[] labels;
    private ImageView[] images;
    private Button[] buttons;;
    private int starcount;
    private Ball b;
    private int level;
    private double ballspeed;
    private double obstaclespeed;
    Obstacle currentobstacle;
    Boolean game;
    Game()
    {
        sounds=new MediaPlayer[3];
        images=new ImageView[3];
        buttons=new Button[3];
        labels=new TextArea[2];
        starcount=0;
        level=1;
        ballspeed=3;
        starcount=0;
        //********This need to be initialised obstaclespeed=
        game=true;
    }
    public void set_gui(Game_Controller run)
    {
        sounds[0]=run.music;
//        sounds[1]=run.ping;
//        sounds[2]=run.gameover;
        buttons[0]=run.Pause_Button;
        labels[0]=run.Level_Label;
        labels[1]=run.Star_Label;
        b=new Ball(run.Ball,ballspeed);
        images[0]=run.Obstacle;
        images[1]=run.color_switcher;
        images[2]=run.star;
//        System.out.println("Gui set finish");
    }
    public void IncreaseLevel()
    {
        this.level++;
    }
    public int getLevel()
    {
        return level;
    }
    public void DisplayScore()
    {
        labels[1].setText(""+starcount);
    }
    public void DisplayPause(MediaPlayer music) throws IOException {
        music.pause();
        Scene pausepage = FXMLLoader.load(getClass().getResource("Pause_Page.fxml"));
        Main.window.setScene(pausepage);
    }
    public int  DisplayRevive(Button revive,int total_stars)
    {
        if(total_stars>5)
        {
            // revive button is eligible
            //decrease the stars
            //resume the game from the level we left on
            if(starcount>5)
            {
                starcount-=5;
                //return is basically how many stars need to be deducted from app ke total stars
                return 0;
            }
            else
            {
                int app_stars=(5-starcount);
                starcount=0;
                return app_stars;
            }
        }
        else
        {
            revive.setText("Not enough Stars");
            return 0;
        }
    }
    public void updateStars(int x)
    {
        starcount+=x;
    }
    public int retStars()
    {
        return starcount;
    }
    private boolean drop()
    {
        if (b.getY() >= 100)
        {
            return true;
        }
        return false;
    }
    private void game_over() throws IOException {
        Scene gameover = FXMLLoader.load(getClass().getResource("Game_Over.fxml"));
        Main.window.setScene(gameover);
    }
    public void playgame()
    {
        b.setY(b.getY()+ballspeed);
        //THIS IS GAME_OVER
        if(drop())
        {
            try
            {
                Game_Controller.timeline.pause();
                sounds[0].pause();
                game_over();
                return;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return;
        }
    }
    private boolean CollisionStar()
    {
        //in deadline 3
        return true;
    }
    public boolean isCollisionObstacle()
    {
        //in deadline 3
        return true;
    }
    private void ChooseRandomObstacle()
    {
        //for levels in deadline 3
    }
    public void DisplayObstacles()
    {
        //change images[0] to something in terms of current obstacle
        RotateTransition rt = new RotateTransition(Duration.millis(3000), images[0]);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
    public void display()
    {
        labels[0].setText("LEVEL "+getLevel());
    }
    //+Serialize(): void 
}
class Ball
{
    private Circle ball;
    //store ball ka x
    //store ball ka y
    private int shape;
    private int color;
    private double X_coordinate;
    private double Y_coordinate;
    private double speed;
    Ball(Circle b,double y)
    {
        ball=b;
        speed=y;
    }
    public void setY(double x)
    {
        ball.setCenterY(x);
    }
    public double getY()
    {
        return ball.getCenterY();
    }
    public void setX(double x)
    {
        ball.setCenterX(x);
    }
    public double getX()
    {
        return ball.getCenterX();
    }
}
class Obstacle
{

}
