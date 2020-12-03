package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;
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
    Game mygame;
    static int video=0;
    App()
        {
        images = new ImageView[5];
        buttons = new Button[4];
        images = new ImageView[5];
        buttons = new Button[4];
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
    private MediaPlayer[] sounds;
    private TextArea[] labels;
    private ImageView[] images;
    private Button[] buttons;

    private int starcount;
    private Ball b;
    private int level;
    private double ballspeed;
    ArrayList<Obstacle> obstacles;

    private double obstaclespeed;
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
        obstacles=new ArrayList<>();
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
        images[3]=run.Hand;

        Obstacle o1=new Obstacle1(run.Obstacle,run.star, run.color_switcher,run.scroll_element);
        obstacles.add(o1);
        //Just for the very first time.
//        obstacles.get(0).set_claimed(true);
//        obstacles.get(0).getMystar().set_claimed(true);

        Obstacle o2=new Obstacle1(run.Obstacle2,run.star2, run.color_switcher2,run.scroll_element2);
        obstacles.add(o2);

//        System.out.println("Gui set finish");
    }
    public void updateStars(int x)
    {
        starcount+=x;
    }
    public int retStars()
    {
        return starcount;
    }
    public void IncreaseLevel()
    {
        this.level++;
    }
    public int getLevel()
    {
        return level;
    }
    public void display_level()
    {
        labels[0].setText("LEVEL "+getLevel());
    }
    public void display_score()
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
        //ISKO MAKE IN TERMS OF GENERAL BALL SPEED
        b.setY(b.getY()+ballspeed-1);
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
        TranslateTransition[] translate_a=new TranslateTransition[obstacles.size()];
        for(int i=0;i<obstacles.size();i++)
        {
            obstacles.get(i).Translate(translate_a[i]);
        }
        for(int i=0;i<obstacles.size();i++)
        {
            obstacles.get(i).star_collision(this) ;
            Obstacle test=obstacles.get(i);
        }
        for(int i=0;i<obstacles.size();i++)
        {
            obstacles.get(i).colorswitcher_collision(this);
        }
    }
    public boolean CollisionStar(Star star, Group patterns)
    {
//        System.out.println(""+Math.abs(patterns.getLayoutY()+patterns.getTranslateY()-b.getY()));
        if(Math.abs(patterns.getLayoutY()+patterns.getTranslateY()-b.getY())<600 && star.is_notclaimed() && b.check_collison(star.getimage(),patterns))
        {
            System.out.println(star.getimage()+" is touching");
            updateStars(1);
            sounds[1].play();
            display_score();
            star.getimage().setVisible(false);
            star.set_claimed(false);
            return true;
        }
        return false;
    }
    public boolean CollisionColorSwitcher(ImageView ColorSwitcher, Group patterns,boolean is_enable)
    {   //in deadline 3
//
        if(Math.abs(patterns.getLayoutY()+patterns.getTranslateY()-b.getY())<100 && is_enable && b.check_collison_cs(ColorSwitcher,patterns))
        {
            System.out.println(ColorSwitcher+" is touching");
            b.ChooseRandomColor();
            ColorSwitcher.setVisible(false);
            IncreaseLevel();
            display_level();
            return true;
        }
        return false;
    }
    public boolean isCollisionObstacle()
    {
        //in deadline 3
        return true;
    }
    private void ChooseRandomObstacle() {
        //for levels in deadline 3
        double color_switch_layoutx = obstacles.get(0).colorswitcher.getLayoutX();
        double color_switch_layouty = obstacles.get(0).colorswitcher.getLayoutY();
        double star_layoutx = obstacles.get(0).Star.getLayoutX();
        double star_layouty = obstacles.get(0).Star.getLayoutY();
        double obstacle_layoutx = obstacles.get(0).obstacle.getLayoutX();
        double obstacle_layouty = obstacles.get(0).obstacle.getLayoutY();
//        ImageView star=new ImageView();
//        star.setImage(new Image("../star.png"));
//        ImageView color_switcher=new ImageView();
//        color_switcher.setImage(new Image("../"));
//        Group n_grp=new Group();
//        n_grp.getChildren().add()


//        Random rand = new Random();
//        int randomNum = rand.nextInt(4) +1;
//        switch(randomNum)
//        {
//            case 1:
//                break;
//            case 2:
//                break;
//            case 3:
//                break;
//            case 4:
//                break;
//            case 5:
//                break;
//        }
//        Group ptrns=new Group();
//        ptrns.getChildren().add(run.Obstacle2);
//        ptrns.getChildren().add(run.star2);
//        ptrns.getChildren().add(run.color_switcher2);
    }
    public void DisplayObstacles()
    {
        //change images[0] to something in terms of current obstacle

        RotateTransition rt_array[]=new RotateTransition[obstacles.size()];
        for(int i=0;i<obstacles.size();i++) {
            obstacles.get(i).rotate(rt_array[i]);
        }
    }
    //+Serialize(): void
}
class Ball
{
    private Circle ball;
    //store ball ka x
    //store ball ka y
    private int shape;
    private String colorname;
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
        color[0]="#0000ff";
        color[1]="#ffff00";
        color[2]="#ff0000";
        color[3]="#660066";
        Random rand = new Random();
        int randomNum = rand.nextInt(4) ;
        System.out.println("random no:"+randomNum+" String "+ color[randomNum]);
        colorname=color[randomNum];
        display_color();
    }
    private void display_color()
    {
        ball.setFill(Paint.valueOf(colorname));
    }
    public String getColorname()
    {
        return colorname;
    }
    public boolean check_collison(ImageView a,Group scroll_element) {
//        System.out.println(" Image is: "+a+" Star Position: "+
//                a.getLayoutY()+" "+scroll_element.getTranslateY()+80+" Ball Position:"+ball.getLayoutY());
        if (Math.abs(Math.abs(a.getLayoutY()+scroll_element.getTranslateY()+80)- ball.getLayoutY())<2)
        {
            System.out.println("Touched");
            return true;
        }
        return false;
    }
    public boolean check_collison_cs(ImageView a,Group scroll_element) {

        if (Math.abs(Math.abs(a.getLayoutY()+scroll_element.getTranslateY()+40)- ball.getLayoutY())<2)
        {
            System.out.println("Touched");
            return true;
        }
        System.out.println("Called but not collision"+Math.abs((a.getLayoutY()+scroll_element.getTranslateY()+40)- ball.getLayoutY()));
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
abstract class Obstacle
{
    ImageView obstacle;
    ImageView Star;
    Star mystar;
    ImageView colorswitcher;
    boolean enable_colorswitch;
    Group patterns;
    Obstacle(ImageView Star,ImageView colorswitcher)
    {
        this.Star=Star;
        this.colorswitcher=colorswitcher;
        this.enable_colorswitch=true;
        mystar=new Star(Star,true);
    }
    public void Translate(TranslateTransition translate)
    {
        translate= new TranslateTransition(Duration.millis(25),patterns);
        translate.setByY(-3);
        translate.setCycleCount(1);
        translate.play();
    }
    public boolean star_collision(Game g)
    {
       return g.CollisionStar(mystar,patterns);
    }
    public boolean colorswitcher_collision(Game g)
    {
        boolean result=g.CollisionColorSwitcher(colorswitcher,patterns,enable_colorswitch);
      if(result) set_claimed(false);
      return result;
    }
    public void set_claimed(boolean b)
    {
        enable_colorswitch=b;
    }
    public boolean get_claimed()
    {
        return enable_colorswitch;
    }
    public Star getMystar()
    {
        return mystar;
    }
    abstract void rotate(RotateTransition rt);
    //1. Rotate- abstract method different for everyone
    //2. Translate

}
class Obstacle1 extends Obstacle{
    Obstacle1(ImageView obstacle,ImageView Star,ImageView colorswitcher,Group patterns)
    {
        super(Star,colorswitcher);
        this.obstacle=obstacle;
        this.patterns=patterns;
    }
    @Override
    public void rotate(RotateTransition rt)
    {
        rt= new RotateTransition(Duration.millis(5000),obstacle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
}
class Obstacle2 extends Obstacle{
    Obstacle2(ImageView Star,ImageView colorswitcher,Group patterns)
    {
        super(Star,colorswitcher);
        this.obstacle=obstacle;
         this.patterns=patterns;
    }
    @Override
    public void rotate(RotateTransition rt)
    {
        rt= new RotateTransition(Duration.millis(5000),obstacle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
}
class Obstacle3 extends Obstacle{
    Obstacle3(ImageView Star,ImageView colorswitcher,Group patterns)
    {
        super(Star,colorswitcher);
        this.obstacle=obstacle;
        this.patterns=patterns;
    }
    @Override
    public void rotate(RotateTransition rt)
    {
        rt= new RotateTransition(Duration.millis(5000),obstacle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
}
class Obstacle4 extends Obstacle{
    Obstacle4(ImageView Star,ImageView colorswitcher,Group patterns)
    {
        super(Star,colorswitcher);
        this.obstacle=obstacle;
        this.patterns=patterns;
    }
    @Override
    public void rotate(RotateTransition rt)
    {
        rt= new RotateTransition(Duration.millis(5000),obstacle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
}
class Obstacle5 extends Obstacle{
    Obstacle5(ImageView Star,ImageView colorswitcher,Group patterns)
    {
        super(Star,colorswitcher);
        this.obstacle=obstacle;
        this.patterns=patterns;
    }
    @Override
    public void rotate(RotateTransition rt)
    {
        rt= new RotateTransition(Duration.millis(5000),obstacle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
}
class Star
{
    private ImageView starimage;
    private boolean enable_star;
    Star(ImageView a,Boolean e) {
        this.starimage = a;
        this.enable_star = e;
    }
    public ImageView getimage()
    {
        return starimage;
    }
    public boolean is_notclaimed()
    {
        return enable_star;
    }
    public void set_claimed(boolean b)
    {
        enable_star=b;
    }
}