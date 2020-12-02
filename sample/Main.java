package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;
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
    ArrayList<Game> savedgames;
    Game mygame;
    static int video=0;
    Controller cont;
    App()
        {
        images = new ImageView[5];
        buttons = new Button[4];
        images = new ImageView[5];
        buttons = new Button[4];
        savedgames=new ArrayList<Game>();
        mygame=new Game();
        }
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
            mygame=new Game();
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
    String name;
    private MediaPlayer[] sounds;
    private TextArea[] labels;
    private ImageView[] images;
    private Button[] buttons;
    private Group patterns;
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
        images=new ImageView[4];
        buttons=new Button[3];
        labels=new TextArea[2];
        starcount=0;
        level=1;
        ballspeed=3;
        starcount=0;
        //********This need to be initialised obstaclespeed=
        game=true;
        name="default";
    }
    public void set_gui(Game_Controller run)
    {
        sounds[0]=run.music;
        sounds[1]=run.star_collide;
//        sounds[2]=run.gameover;
        buttons[0]=run.Pause_Button;
        labels[0]=run.Level_Label;
        labels[1]=run.Star_Label;
        b=new Ball(run.Ball,ballspeed);
        images[0]=run.Obstacle;
        images[1]=run.color_switcher;
        images[2]=run.star;
        images[3]=run.Hand;
        patterns=run.scroll_element;
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
        if (b.getY()>700)
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
        TranslateTransition translate = new TranslateTransition(Duration.millis(25),patterns);
        translate.setByY(-1);
        translate.setCycleCount(1);
        translate.play();
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
        if(CollisionStar())
        {
            images[2].setVisible(false);

        }
        if(CollisionColorswitcher())
        {
            b.ChooseRandomColor();
        }
    }
    private boolean CollisionStar()
    {
        //in deadline 3
        if(b.check_collison(images[2],patterns)) {
            updateStars(1);
            sounds[1].play();
            display_stars();
            return true;
        }
        return false;
    }
    private boolean CollisionColorswitcher()
    {
        //in deadline 3
        if(b.check_collison_cs(images[1],patterns)) {
            return true;
        }
        return false;
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
    public void display_stars()
    {
        labels[1].setText(""+retStars());
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
    public void ChooseRandomColor()
    {
        //for levels in deadline 3
        String[] color=new String[4];
        color[0]="#ffff00";
        color[1]="#66ffff";
        color[2]="#ff3399";
        color[3]="#9966ff";
        Random rand = new Random();
        int randomNum = rand.nextInt(4) ;
        System.out.println("random no:"+randomNum+" String "+ color[randomNum]);
        ball.setFill(Paint.valueOf(color[randomNum]));
    }
    public boolean check_collison(ImageView a,Group scroll_element) {

        if (Math.abs((a.getLayoutY()+scroll_element.getTranslateY()+80)- ball.getLayoutY())<2)
        {
            System.out.println("Touched");
            return true;
        }
        return false;
    }
    public boolean check_collison_cs(ImageView a,Group scroll_element) {

        if (Math.abs((a.getLayoutY()+scroll_element.getTranslateY()+40)- ball.getLayoutY())<2)
        {
            System.out.println("Touched");
            return true;
        }
        return false;
    }
    public void setY(double x)
    {
        ball.setLayoutY(x);
    }
    public double getY()
    {
        return ball.getLayoutY();
    }
}
class Obstacle
{

}
