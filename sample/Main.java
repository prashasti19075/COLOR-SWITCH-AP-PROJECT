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
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    AnchorPane main_pane;
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
        main_pane=run.Main_Pane;
        Obstacle o1=new Obstacle1(run.Obstacle,run.star, run.color_switcher,run.scroll_element);
        obstacles.add(o1);

        Obstacle gen = null;
        for(int i=0;i<10;i++) {
            try {
                gen = ChooseRandomObstacle(obstacles.get(i).get_position());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            obstacles.add(gen);
        }
//        System.out.println("Size of obstacle Array: "+obstacles.size());
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
    public void Translate_UP()
    {
        for(int i=0;i<obstacles.size();i++) {
//            System.out.println(" Pattern moves down");
            obstacles.get(i).Translate(10, 40, 1);
        }
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
        for(int i=0;i<obstacles.size();i++)
            obstacles.get(i).Translate(25,-3,1);
        for(int i=0;i<obstacles.size();i++)
            obstacles.get(i).star_collision(this);
        for(int i=0;i<obstacles.size();i++)
            obstacles.get(i).colorswitcher_collision(this);
    }
    public boolean CollisionStar(Star star, Group patterns)
    {
        if(star.is_notclaimed() && b.check_collison(star.getimage(),patterns))
        {
//            System.out.println(star.getimage()+" is touching");
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
        if(is_enable && b.check_collison_cs(ColorSwitcher,patterns))
        {
//            System.out.println(ColorSwitcher+" is touching");
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
    private Obstacle ChooseRandomObstacle( double group_layout_y) throws FileNotFoundException {
        //for levels in deadline 3
         ImageView star=new ImageView();
        star.setImage(new Image(new FileInputStream("C:\\Users\\rachn\\Desktop\\java\\GUI\\src\\star.png")));

        ImageView color_switcher=new ImageView();
        color_switcher.setImage(new Image(new FileInputStream("C:\\Users\\rachn\\Desktop\\java\\GUI\\src\\switcher.png")));

        ImageView obstacle=new ImageView();

        Group pattern=new Group();
        main_pane.getChildren().add(pattern);

        pattern.setLayoutY(group_layout_y-450.00);
        pattern.setLayoutX(420.0);

        pattern.getChildren().add(star);
        star.setFitHeight(81.0);
        star.setFitWidth(74.0);
        star.setLayoutX(160.0);
        star.setLayoutY(250.0);
        star.setPickOnBounds(true);
        star.setPreserveRatio(true);

        pattern.getChildren().add(color_switcher);
        color_switcher.setFitHeight(66.0);
        color_switcher.setFitWidth(59.0);
        color_switcher.setLayoutX(168.0);
        color_switcher.setLayoutY(69.0);
        color_switcher.setPickOnBounds(true);
        color_switcher.setPreserveRatio(true);

        Obstacle new_obstacle=null;
        Random rand = new Random();
        int randomNum = rand.nextInt(5) +1;
//        System.out.println(" Random Number is: "+ randomNum);
        switch(randomNum)
        {
            case 1:
                obstacle.setImage(new Image(new FileInputStream("C:\\Users\\rachn\\Desktop\\java\\GUI\\src\\obs1.png")));
                new_obstacle=new Obstacle1(obstacle,star,color_switcher,pattern);
                break;
            case 2:
                obstacle.setImage(new Image(new FileInputStream("C:\\Users\\rachn\\Desktop\\java\\GUI\\src\\obs2.png")));
                new_obstacle=new Obstacle2(obstacle,star,color_switcher,pattern);
                break;
            case 3:
                obstacle.setImage(new Image(new FileInputStream("C:\\Users\\rachn\\Desktop\\java\\GUI\\src\\obs3.png")));
                new_obstacle=new Obstacle3(obstacle,star,color_switcher,pattern);
                break;
            case 4:
                obstacle.setImage(new Image(new FileInputStream("C:\\Users\\rachn\\Desktop\\java\\GUI\\src\\obs4.png")));
                new_obstacle=new Obstacle4(obstacle,star,color_switcher,pattern);
                break;
            case 5:
                obstacle.setImage(new Image(new FileInputStream("C:\\Users\\rachn\\Desktop\\java\\GUI\\src\\obs5.png")));
                new_obstacle=new Obstacle5(obstacle,star,color_switcher,pattern);
                break;
        }

        pattern.getChildren().add(obstacle);
        obstacle.setFitHeight(284.0);
        obstacle.setFitWidth(294.0);
        obstacle.setLayoutX(53.00);
        obstacle.setLayoutY(148.00);
        obstacle.setPickOnBounds(true);
        obstacle.setPreserveRatio(true);


        return new_obstacle;
    }
    public void DisplayObstacles()
    {
        RotateTransition rt_array[]=new RotateTransition[obstacles.size()];
        for(int i=0;i<obstacles.size();i++) obstacles.get(i).rotate(rt_array[i]);
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
//        System.out.println("random no:"+randomNum+" String "+ color[randomNum]);
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
        if (Math.abs(a.getLayoutY()+scroll_element.getTranslateY()+scroll_element.getLayoutY()- ball.getLayoutY())<2)
        {
//            System.out.println("Touched");
            return true;
        }
        return false;
    }
    public boolean check_collison_cs(ImageView a,Group scroll_element) {

        if (Math.abs(a.getLayoutY()+scroll_element.getTranslateY()+scroll_element.getLayoutY()- ball.getLayoutY())<2)
        {
//            System.out.println("Color Switcher Touched");
            return true;
        }
//        System.out.println("Called but not collision"+Math.abs((a.getLayoutY()+scroll_element.getTranslateY()+40)- ball.getLayoutY()));
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
    protected ImageView obstacle;
    protected ImageView Star;
    protected Star mystar;
    protected ImageView colorswitcher;
    protected boolean enable_colorswitch;
    protected Group patterns;
    Obstacle(ImageView Star,ImageView colorswitcher)
    {
        this.Star=Star;
        this.colorswitcher=colorswitcher;
        this.enable_colorswitch=true;
        mystar=new Star(Star,true);
    }
    public void Translate(int duration,int translate_by,int cycle_count)
    {
        TranslateTransition translate= new TranslateTransition(Duration.millis(duration),patterns);
        translate.setByY(translate_by);
        translate.setCycleCount(cycle_count);
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
    public double get_position()
    {
        return this.patterns.getLayoutY();
    }
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
    Obstacle2(ImageView obstacle,ImageView Star,ImageView colorswitcher,Group patterns)
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
    Obstacle3(ImageView obstacle,ImageView Star,ImageView colorswitcher,Group patterns)
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
    Obstacle4(ImageView obstacle,ImageView Star,ImageView colorswitcher,Group patterns)
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
    Obstacle5(ImageView obstacle,ImageView Star,ImageView colorswitcher,Group patterns)
    {
        super(Star,colorswitcher);
        this.obstacle=obstacle;
        this.patterns=patterns;
    }
    @Override
    public void rotate(RotateTransition rt)
    {
        rt= new RotateTransition(Duration.millis(5000),obstacle);
        rt.setByAngle(-360);
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