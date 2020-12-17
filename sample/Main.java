package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
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

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.lang.*;

import static javafx.fxml.FXMLLoader.load;

public class Main extends Application {
    public static void Serialize(Game a, String filename) throws IOException
    {
        ObjectOutputStream out = null;
        try{
            for(int i=0;i<a.obstacles.size();i++)
            {
//                System.out.println(" Translate value of obstacle "+i+" is: "+a.obstacles.get(i).getTransateY());
            }
            out = new ObjectOutputStream(new FileOutputStream("src\\sample\\savedgames\\"+filename));
            out.writeObject(a);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            out.close();
        }
    }
    public static Game Deserialize(String filename) throws IOException {
        ObjectInputStream in = null;
        Game newgame = null;
        try{
            in = new ObjectInputStream(new FileInputStream("src\\sample\\savedgames\\"+filename));
            newgame = (Game) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        return newgame;
    }
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
    private transient ImageView[] images;
    private transient Button[] buttons;
    private transient MediaPlayer music;
    private static int totalstars;
    static Scene gamepage;
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
    public void set_gui(Controller cont){
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
        gamepage = FXMLLoader.load(getClass().getResource("Game_Page.fxml"));
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
    public void resume_game() throws IOException {
        music.pause();
        Scene displayoldgames = FXMLLoader.load(getClass().getResource("Display_Games.fxml"));
        Main.window.setScene(displayoldgames);

    }
}

class Game implements Serializable
{
    private transient MediaPlayer[] sounds;
    private transient TextArea[] labels;
    private transient ImageView[] images;
    private transient Button[] buttons;
    private transient AnchorPane main_pane;
    private int starcount;
    private Ball b;
    private int level;
    private double ballspeed;
    public ArrayList<Obstacle> obstacles;
    private ArrayList<Integer> obstacleno;
    private Boolean hand_enable;
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
        obstacleno= new ArrayList<>();
        obstacleno.add(2);
        hand_enable=true;
    }
    public void initialise()
    {
        sounds = new MediaPlayer[3];
        images = new ImageView[4];
        buttons = new Button[3];
        labels = new TextArea[2];
    }
    public void play_music()
    {
        sounds[0].play();
    }
    public void set_gui(Game_Controller run)
    {
        sounds[0]=run.music;
        sounds[1]=run.star_collide;
//        sounds[2]=run.gameover;
        buttons[0]=run.Pause_Button;
        labels[0]=run.Level_Label;
        labels[1]=run.Star_Label;
        images[3]=run.Hand;
        main_pane=run.Main_Pane;

        if(obstacleno.size()==1)
        {   //Only when new
            b=new Ball(run.Ball,ballspeed);
            Obstacle o1=new Obstacle2(run.Obstacle,run.star, run.color_switcher,run.scroll_element);
            obstacles.add(o1);
//            obstacleno.add(2);
            addObstacles(0,20);
        }
        else
        {
            Ball temp=b;
            b=new Ball(run.Ball, ballspeed);
            b.setY(temp.Y_coordinate);
            b.setColor(temp.getColorname());
            b.display_color();
//            System.out.println("1. Array Size: "+obstacles.size()+" No arraySize: "+obstacleno.size());

            Obstacle thisone=obstacles.get(0);
//            System.out.println(" Obstacle no. 0"+ " Star: "+thisone.getMystar().is_notclaimed()+
//                    " Color Switcher:"+ thisone.get_claimed());
            obstacles.remove(0);
            Obstacle newgen=new Obstacle2(run.Obstacle,run.star, run.color_switcher,run.scroll_element);
            obstacles.add(0,newgen);
            newgen.translate_value= thisone.translate_value;
            newgen.getMystar().set_claimed(thisone.getMystar().is_notclaimed());
            newgen.set_claimed(thisone.get_claimed());
//            System.out.println("2. Array Size: "+obstacles.size()+" No arraySize: "+obstacleno.size());
            addObstacles_serialize();
        }
//        System.out.println("Size of obstacle Array: "+obstacles.size());
    }
    private void addObstacles(int start,int end)
    {
        Obstacle gen = null;
        for (int i = start; i < end; i++)
        {
            try
            {
                Random rand = new Random();
                int randomNum = rand.nextInt(5) +1;
                obstacleno.add(randomNum);
                gen = ChooseRandomObstacle(obstacles.get(i).get_position(),randomNum);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            obstacles.add(gen);
        }
    }
    private void addObstacles_serialize() {
        for (int i = 1; i < obstacles.size(); i++) {
//            System.out.println(" i is : + "+i+ "\n"+"1. Array Size: "+obstacles.size()+" No arraySize: "+obstacleno.size());
            Obstacle thisone = obstacles.get(i);
//            System.out.println(" Obstacle no."+i+ " Star: "+thisone.getMystar().is_notclaimed()+
//                    " Color Switcher:"+ thisone.get_claimed());
            Obstacle newgen = null;
            obstacles.remove(i);
            try {
                try
                {
                    int b=obstacleno.get(i);
                }
                catch(IndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
                newgen = ChooseRandomObstacle(obstacles.get(i - 1).get_position(), obstacleno.get(i));
                newgen.translate_value = thisone.translate_value;
                newgen.getMystar().set_claimed(thisone.getMystar().is_notclaimed());
                newgen.set_claimed(thisone.get_claimed());
//                System.out.println(" Obstacle no."+i+ " Star: "+newgen.getMystar().is_notclaimed()+
//                        " Color Switcher:"+ newgen.get_claimed());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            obstacles.add(i, newgen);
//            System.out.println("2. Array Size: "+obstacles.size()+" No arraySize: "+obstacleno.size());
        }

        if (hand_enable == false)
            images[3].setVisible(false);
        for (int i = 0; i < obstacles.size(); i++)
            obstacles.get(i).update();
    }
    public void updateStars(int x)
    {
        starcount+=x;
    }
    public void hand_enable_set(boolean x)
    {
        hand_enable=x;
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
    public void DisplayPause() throws IOException {
        sounds[0].pause();
        Scene pausepage = FXMLLoader.load(getClass().getResource("Pause_Page.fxml"));
        Main.window.setScene(pausepage);
    }
    public int  DisplayRevive(Button revive,int total_stars)
    {
        if(total_stars>=5)
        {
            // revive button is eligible
            //decrease the stars
            //resume the game from the level we left on
            if(starcount>=5)
            {
                starcount-=5;
                display_score();
                Main.colorswitch.mygame.play_music();
                //Game_Controller.timeline.play();
                Main.window.setScene(App.gamepage);
                //return is basically how many stars need to be deducted from app ke total stars
                return 0;
            }
            else
            {
                int app_stars=(5-starcount);
                starcount=0;
                display_score();
                //Game_Controller.timeline.play();
                Main.colorswitch.mygame.play_music();
                Main.window.setScene(App.gamepage);
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
        if (b.getY()>690)
        {
            b.setY(670);
            return true;
        }
        return false;
    }
    private void game_over() throws IOException {
        Scene gameover = FXMLLoader.load(getClass().getResource("Game_Over.fxml"));
        //b.setY(b.getY()-200);
        Main.window.setScene(gameover);
    }
    public void Translate_UP()
    {
        for(int i=0;i<obstacles.size();i++) {
//            System.out.println(" Pattern moves down");
            obstacles.get(i).Translate(10, 40.00, 1);
        }
    }
    public void playgame()
    {
        //ISKO MAKE IN TERMS OF GENERAL BALL SPEED
        b.setY(b.getY()+ballspeed-1);
        //THIS IS GAME_OVER
        if(drop())
        {
            //System.out.println("Collided.");
            try     {
                Game_Controller.timeline.pause();
                sounds[0].pause();
                game_over();
                return;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return;
        }
        Iterator<Obstacle> iterator=obstacles.iterator();
        while(iterator.hasNext()) {
            Obstacle currobs=iterator.next();
            currobs.Translate(25,-3,1);
            currobs.star_collision(this);
            currobs.colorswitcher_collision(this);
            if(currobs.obs_collision(this, this.b))  {
//                System.out.println("Collided.");
                try     {
                    Game_Controller.timeline.pause();
                    sounds[0].pause();
                    game_over();
                    return;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                return;
            }
            //obstacles.get(i).obs_collision(this, this.b);
        }
        int n=obstacles.size();
        if(n-getLevel()==1)
        {
            addObstacles(n-1,n+9);
            DisplayObstacles();
            for(int i=n;i<n+10;i++)
            {
                obstacles.get(i).patterns.setTranslateY(obstacles.get(n-1).patterns.getTranslateY());
                System.out.println("Translate Value of: "+i+" is "+obstacles.get(i).patterns.getTranslateY());
            }
        }
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
        if(is_enable && b.check_collison_cs(ColorSwitcher,patterns)) {
//            System.out.println(ColorSwitcher+" is touching");
            b.ChooseRandomColor();
            ColorSwitcher.setVisible(false);
            IncreaseLevel();
            display_level();
            return true;
        }
        return false;
    }

    private Obstacle ChooseRandomObstacle( double group_layout_y, int randomNum) throws FileNotFoundException {
        //for levels in deadline 3
        ImageView star=new ImageView();
        star.setImage(new Image(new FileInputStream("src\\star.png")));

        ImageView color_switcher=new ImageView();
        color_switcher.setImage(new Image(new FileInputStream("src\\switcher.png")));

        ImageView obstacle=new ImageView();
        ImageView obs_2=new ImageView();
        //Only in case of obstacles 3 and 5

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
        color_switcher.setLayoutY(50.0);
        color_switcher.setPickOnBounds(true);
        color_switcher.setPreserveRatio(true);

        Obstacle new_obstacle=null;

        switch(randomNum)
        {
            case 1:
                obstacle.setImage(new Image(new FileInputStream("src\\obs1.png")));
                new_obstacle=new Obstacle1(obstacle,star,color_switcher,pattern);
                pattern.getChildren().add(obstacle);
                obstacle.setFitHeight(284.0);
                obstacle.setFitWidth(294.0);
                obstacle.setLayoutX(53.00);
                obstacle.setLayoutY(148.00);
                break;
            case 2:
                obstacle.setImage(new Image(new FileInputStream("src\\obs2.png")));
                new_obstacle=new Obstacle2(obstacle,star,color_switcher,pattern);
                pattern.getChildren().add(obstacle);
                obstacle.setFitHeight(284.0);
                obstacle.setFitWidth(294.0);
                obstacle.setLayoutX(53.00);
                obstacle.setLayoutY(148.00);
                break;
            case 3:
                obstacle.setImage(new Image(new FileInputStream("src\\obs3.png")));
                obs_2.setImage(new Image(new FileInputStream("src\\obs3.png")));
                new_obstacle=new Obstacle3(obstacle,obs_2,star,color_switcher,pattern);
                pattern.getChildren().add(obstacle);
                pattern.getChildren().add(obs_2);
                obstacle.setFitHeight(142.0);
                obstacle.setFitWidth(142.0);
                obs_2.setFitHeight(142.0);
                obs_2.setFitWidth(142.0);
                obstacle.setLayoutX(60.00);
                obstacle.setLayoutY(190.00);
                obs_2.setLayoutX(199.00);
                obs_2.setLayoutY(190.00);
                pattern.resize(325,313);
                obs_2.setPickOnBounds(true);
                obs_2.setPreserveRatio(true);
                obs_2.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                break;
            case 4:
                obstacle.setImage(new Image(new FileInputStream("src\\obs4.png")));
                new_obstacle=new Obstacle4(obstacle,star,color_switcher,pattern);
                pattern.getChildren().add(obstacle);
                obstacle.setFitHeight(284.0);
                obstacle.setFitWidth(294.0);
                obstacle.setLayoutX(53.00);
                obstacle.setLayoutY(148.00);
                break;
            case 5:
                obstacle.setImage(new Image(new FileInputStream("src\\obs5.png")));
                new_obstacle=new Obstacle5(obstacle,star,color_switcher,pattern);
                pattern.getChildren().add(obstacle);
                obstacle.setFitHeight(284.0);
                obstacle.setFitWidth(294.0);
                obstacle.setLayoutX(53.00);
                obstacle.setLayoutY(148.00);
                break;
        }

        obstacle.setPickOnBounds(true);
        obstacle.setPreserveRatio(true);


        return new_obstacle;
    }
    public void DisplayObstacles()
    {
//        System.out.println("DisplayObstacles() Called");
        int rotate_time=5000;
        RotateTransition rt_array[]=new RotateTransition[obstacles.size()];
        for(int i=0;i<obstacles.size();i++) {
            if(i!=0 && i%5==0)
                rotate_time-=500;
            obstacles.get(i).rotate(rt_array[i], rotate_time);
        }
    }
    //+Serialize(): void
}
class Ball implements Serializable
{
    private transient Circle ball;
    //store ball ka x
    //store ball ka y
    private int shape;
    private String colorname;
    private double X_coordinate;
    public double Y_coordinate;
    private double speed;
    Ball(Circle b,double y)
    {
        ball=b;
        speed=y;
        X_coordinate=b.getLayoutX();
        Y_coordinate=b.getLayoutY();
        colorname="#ffff00";
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
    public void display_color()
    {
        ball.setFill(Paint.valueOf(colorname));
    }
    public String getColorname()
    {
        return colorname;
    }
    public boolean check_collison(ImageView a,Group scroll_element) {
        if (Math.abs(a.getLayoutY()+scroll_element.getTranslateY()+scroll_element.getLayoutY()- ball.getLayoutY())<15)
        {
//            System.out.println("Touched");
            return true;
        }
        return false;
    }
    public boolean check_collision_up(ImageView a,Group scroll_element)
    {
        if (Math.abs(a.getLayoutY()+scroll_element.getTranslateY()+scroll_element.getLayoutY()+50-ball.getLayoutY())<10)
        {
//            System.out.println("Up collide");
            return true;
        }
        return false;
    }
    public boolean check_collision_down(ImageView a,Group scroll_element)
    {
        if (Math.abs(a.getLayoutY()+scroll_element.getTranslateY()+scroll_element.getLayoutY()+230-ball.getLayoutY())<10)
        {
//            System.out.println("Down collide");
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
    public void setColor(String name)
    {
        colorname=name;
    }
    public void setY(double x)
    {
        ball.setLayoutY(x);
        Y_coordinate=ball.getLayoutY();
    }
    public double getY()
    {
        Y_coordinate=ball.getLayoutY();
        return Y_coordinate;
    }
    public double getX()
    {
        return X_coordinate;
    }
}
abstract class Obstacle implements Serializable,Collidable
{
    protected  transient ImageView obstacle;
    protected transient ImageView Star;
    protected Star mystar;
    protected transient ImageView colorswitcher;
    protected boolean enable_colorswitch;
    protected transient Group patterns;
    protected double translate_value;
    Obstacle(ImageView Star,ImageView colorswitcher)
    {
        this.Star=Star;
        this.colorswitcher=colorswitcher;
        this.enable_colorswitch=true;
        mystar=new Star(Star,true);
        translate_value=0;
    }
    public void Translate(int duration,double translate_by,int cycle_count)
    {
        TranslateTransition translate= new TranslateTransition(Duration.millis(duration),patterns);
        translate.setByY(translate_by);
        translate.setCycleCount(cycle_count);
//        translate_value=patterns.getTranslateY();
        translate.play();
        update_translate();
    }
    public boolean star_collision(Game g)
    {
        return g.CollisionStar(mystar,patterns);
    }
    public boolean obs_collision(Game g, Ball b){
        return CollisionObs(this, patterns, b);
    }
    public boolean colorswitcher_collision(Game g)
    {
        boolean result=g.CollisionColorSwitcher(colorswitcher,patterns,enable_colorswitch);
        if(result) set_claimed(false);
        return result;
    }
    public void update()
    {
//        System.out.println( " Star: "+this.getMystar().is_notclaimed()+
//                " Color Switcher:"+ this.get_claimed());
        this.getMystar().getimage().setVisible(this.getMystar().is_notclaimed());
        this.colorswitcher.setVisible(this.get_claimed());
        this.Translate(100,this.getTransateY(),1);
//        System.out.println(" tanslate Value: "+this.getTransateY());
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
    /*public boolean getobs_claimed(){
        return false;
    }*/
    abstract void rotate(RotateTransition rt,int rotate_time);
    public double get_position()
    {
        return this.patterns.getLayoutY();
    }
    public void update_translate()
    {
        this.translate_value=patterns.getTranslateY();
    }
    public double getTransateY()
    {
        return this.translate_value;
    }
    public ImageView getimage()
    {
        return this.obstacle;
    }
    public abstract boolean CollisionObs(Obstacle star, Group patterns, Ball b);
}
class Obstacle1 extends Obstacle {
    Obstacle1(ImageView obstacle, ImageView Star, ImageView colorswitcher, Group patterns) {
        super(Star, colorswitcher);
        this.obstacle = obstacle;
        this.patterns = patterns;
    }

    @Override
    public void rotate(RotateTransition rt,int rotate_time) {
        rt = new RotateTransition(Duration.millis(rotate_time), obstacle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
    @Override
    public boolean CollisionObs(Obstacle star, Group patterns, Ball b) {
        if (b.check_collision_down(star.getimage(), patterns))
        {
            if(b.getColorname().equals("#ffff00")){//Yellow.
//                System.out.println("aaya yellow");
                if((((int)(this.obstacle.getRotate())%360)>225)&&((this.obstacle.getRotate()%360)<315)){
//                    System.out.println("aaya yellow");
                    return false;
                }
            }
            if(b.getColorname().equals("#0000ff")){//Blue
                if(((this.obstacle.getRotate())%360>45)&&((this.obstacle.getRotate())%360<135)){
                    return false;
                }
            }
            if(b.getColorname().equals("#660066")){//Purple
                if(((this.obstacle.getRotate()%360)>135)&&((this.obstacle.getRotate()%360)<225)){
                    return false;
                }
            }
            if(b.getColorname().equals("#ff0000")){//Red
                if(((int)(this.obstacle.getRotate())%360>315)||(((int)this.obstacle.getRotate())%360<45)){
                    return false;
                }
            }
            return true;
        }
        if (b.check_collision_up(star.getimage(), patterns)) {
            if(b.getColorname().equals("#ffff00")){//Yellow.
//                System.out.println("aaya yellow");
                if((((int)(this.obstacle.getRotate())%360)>45)&&((this.obstacle.getRotate()%360)<135)){
//                    System.out.println("aaya yellow");
                    return false;
                }
            }
            if(b.getColorname().equals("#0000ff")){//Blue
                if(((this.obstacle.getRotate())%360>225)&&((this.obstacle.getRotate())%360<315)){
                    return false;
                }
            }
            if(b.getColorname().equals("#660066")){//Purple
                if(((this.obstacle.getRotate()%360)>315)&&((this.obstacle.getRotate()%360)<45)){
                    return false;
                }
            }
            if(b.getColorname().equals("#ff0000")){//Red
                if((this.obstacle.getRotate()>135)&&(this.obstacle.getRotate()<225)){
                    return false;
                }
            }
            return true;
        }
        return false;
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
    public void rotate(RotateTransition rt,int rotate_time)
    {
        rt= new RotateTransition(Duration.millis(rotate_time),obstacle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
    @Override
    public boolean CollisionObs(Obstacle star, Group patterns, Ball b) {

        if (b.check_collision_down(star.getimage(), patterns))
        {
//            System.out.println("Down COllide");
            if(b.getColorname().equals("#ffff00")){//Yellow.
//                System.out.println("Ball aaya yellow");
                if((((int)(this.obstacle.getRotate())%360)>270)&&((this.obstacle.getRotate()%360)<360)){
//                    System.out.println("aaya yellow");
                    return false;
                }
            }
            if(b.getColorname().equals("#0000ff")){//Blue
//                System.out.println("Ball aaya Blue");
                if(((this.obstacle.getRotate())%360>90)&&((this.obstacle.getRotate())%360<180)){
//                    System.out.println("aaya Blue");
                    return false;
                }
            }
            if(b.getColorname().equals("#660066")){//Purple
//                System.out.println("Ball aaya purple");
                if(((this.obstacle.getRotate()%360)>180)&&((this.obstacle.getRotate()%360)<270)){
//                    System.out.println("aaya Purple");
                    return false;
                }
            }
            if(b.getColorname().equals("#ff0000")){//Red
//                System.out.println("Ball aaya Red");
                if((this.obstacle.getRotate()>0)&&(this.obstacle.getRotate()<90)){
//                    System.out.println("aaya Red");
                    return false;
                }
            }
            return true;
        }
        if (b.check_collision_up(star.getimage(), patterns)) {
            if(b.getColorname().equals("#ffff00")){//Yellow.
//                System.out.println("aaya yellow");
                if((((int)(this.obstacle.getRotate())%360)>90)&&((this.obstacle.getRotate()%360)<180)){
//                    System.out.println("aaya yellow");
                    return false;
                }
            }
            if(b.getColorname().equals("#0000ff")){//Blue
                if(((this.obstacle.getRotate())%360>270)&&((this.obstacle.getRotate())%360<360)){
                    return false;
                }
            }
            if(b.getColorname().equals("#660066")){//Purple
                if(((this.obstacle.getRotate()%360)>0)&&((this.obstacle.getRotate()%360)<90)){
                    return false;
                }
            }
            if(b.getColorname().equals("#ff0000")){//Red
                if((this.obstacle.getRotate()>180)&&(this.obstacle.getRotate()<270)){
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
class Obstacle3 extends Obstacle{
    transient ImageView obs_2;
    Obstacle3(ImageView obstacle,ImageView obs_2,ImageView Star,ImageView colorswitcher,Group patterns)
    {
        super(Star,colorswitcher);
        this.obstacle=obstacle;
        this.obs_2=obs_2;
        this.patterns=patterns;
    }
    @Override
    public void rotate(RotateTransition rt,int rotate_time)
    {
        rt= new RotateTransition(Duration.millis(rotate_time),obstacle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();

        rt= new RotateTransition(Duration.millis(rotate_time),obs_2);
        rt.setByAngle(-360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
    @Override
    public boolean CollisionObs(Obstacle obstacle, Group patterns, Ball b) {
        //
        if (Math.abs(obstacle.getimage().getLayoutY()+patterns.getTranslateY()+patterns.getLayoutY()+71-b.getY())<10)
        {   int rot=((int)(this.obstacle.getRotate())%360);
            System.out.println("aaya");
//            System.out.println("Down Collison\nRotation at this point"+obstacle.getimage().getRotate());
            //((rot<91)&&(rot>88))//red
            //(rot<271 && rot>269)//blue
            //(rot<181 && rot>179)//purple
            //(rot<1 || rot>359)//yellow

            if(b.getColorname().equals("#ffff00")){//Yellow.
                System.out.println("ball aaya yellow");
                if((rot<=185 && rot>=175)||(rot<=275 && rot>=265)||((rot<=95)&&(rot>=85))){
                    System.out.println("aaya yellow");
                    return true;
                }
            }

            if(b.getColorname().equals("#0000ff")){//Blue
                System.out.println(" ball aaya blue");
                if((rot<=185 && rot>=175)||((rot<=95)&&(rot>=85))||(rot<=5 || rot>=355)){
                    System.out.println("aaya blue");
                    return true;
                }
            }
            if(b.getColorname().equals("#660066")){//Purple
                System.out.println(" ball aaya purple");
                if((rot<=275 && rot>=265)||((rot<=95)&&(rot>=85))||(rot<=5 || rot>=355)){
                    System.out.println("aaya purple");
                    return true;
                }
            }
            if(b.getColorname().equals("#ff0000")){//Red
                System.out.println(" ball aaya red");
                if((rot<=185 && rot>=175)||(rot<=5 || rot>=355)||(rot<=275 && rot>=265)){
                    System.out.println("aaya red");
                    return true;
                }
            }
            System.out.println(rot);
            return false;

        }
        return false;
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
    public void rotate(RotateTransition rt,int rotate_time)
    {
        rt= new RotateTransition(Duration.millis(rotate_time),obstacle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
    @Override
    public boolean CollisionObs(Obstacle star, Group patterns, Ball b)
    {
        if (b.check_collision_down(star.getimage(), patterns))
        {
            if(b.getColorname().equals("#ffff00")){//Yellow.
//                System.out.println("aaya yellow");
                if((((int)(this.obstacle.getRotate())%360)>270)&&((this.obstacle.getRotate()%360)<360)){
//                    System.out.println("aaya yellow");
                    return false;
                }
            }
            if(b.getColorname().equals("#0000ff")){//Blue
                if(((this.obstacle.getRotate())%360>90)&&((this.obstacle.getRotate())%360<180)){
                    return false;
                }
            }
            if(b.getColorname().equals("#660066")){//Purple
                if(((this.obstacle.getRotate()%360)>180)&&((this.obstacle.getRotate()%360)<270)){
                    return false;
                }
            }
            if(b.getColorname().equals("#ff0000")){//Red
                if((this.obstacle.getRotate()>0)&&(this.obstacle.getRotate()<90)){
                    return false;
                }
            }
            return true;
        }
        if (b.check_collision_up(star.getimage(), patterns)) {
            if(b.getColorname().equals("#ffff00")){//Yellow.
//                System.out.println("aaya yellow");
                if((((int)(this.obstacle.getRotate())%360)>90)&&((this.obstacle.getRotate()%360)<180)){
//                    System.out.println("aaya yellow");
                    return false;
                }
            }
            if(b.getColorname().equals("#0000ff")){//Blue
                if(((this.obstacle.getRotate())%360>270)&&((this.obstacle.getRotate())%360<360)){
                    return false;
                }
            }
            if(b.getColorname().equals("#660066")){//Purple
                if(((this.obstacle.getRotate()%360)>0)&&((this.obstacle.getRotate()%360)<90)){
                    return false;
                }
            }
            if(b.getColorname().equals("#ff0000")){//Red
                if((this.obstacle.getRotate()>180)&&(this.obstacle.getRotate()<270)){
                    return false;
                }
            }
            return true;
        }

        return false;
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
    public void rotate(RotateTransition rt,int rotate_time)
    {
        rt= new RotateTransition(Duration.millis(rotate_time),obstacle);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
    @Override
    public boolean CollisionObs(Obstacle star, Group patterns, Ball b)
    {
        if (b.check_collision_down(star.getimage(), patterns))
        {
            if(b.getColorname().equals("#ffff00")){//Yellow.
//                System.out.println("ball aaya yellow");
                if((((int)(this.obstacle.getRotate())%360)>270)&&((this.obstacle.getRotate()%360)<360)){
//                    System.out.println("aaya yellow");
                    return false;
                }
            }
            if(b.getColorname().equals("#0000ff")){//Blue
//                System.out.println("ball aaya blue");
                if(((this.obstacle.getRotate())%360>90)&&((this.obstacle.getRotate())%360<180)){
//                    System.out.println("aaya blue");
                    return false;
                }
            }
            if(b.getColorname().equals("#660066")){//Purple
//                System.out.println("ball aaya purple");
                if(((this.obstacle.getRotate()%360)>180)&&((this.obstacle.getRotate()%360)<270)){
//                    System.out.println("aaya purple");
                    return false;
                }
            }
            if(b.getColorname().equals("#ff0000")){//Red
//                System.out.println("ball aaya red");
                if((this.obstacle.getRotate()>0)&&(this.obstacle.getRotate()<90)){
//                    System.out.println("aaya red");
                    return false;
                }
            }
            return true;
        }
        if (b.check_collision_up(star.getimage(), patterns)) {
            if(b.getColorname().equals("#ffff00")){//Yellow.
//                System.out.println("aaya yellow");
                if((((int)(this.obstacle.getRotate())%360)>90)&&((this.obstacle.getRotate()%360)<180)){
//                    System.out.println("aaya yellow");
                    return false;
                }
            }
            if(b.getColorname().equals("#0000ff")){//Blue
                if(((this.obstacle.getRotate())%360>270)&&((this.obstacle.getRotate())%360<360)){
                    return false;
                }
            }
            if(b.getColorname().equals("#660066")){//Purple
                if(((this.obstacle.getRotate()%360)>0)&&((this.obstacle.getRotate()%360)<90)){
                    return false;
                }
            }
            if(b.getColorname().equals("#ff0000")){//Red
                if((this.obstacle.getRotate()>180)&&(this.obstacle.getRotate()<270)){
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
class Star implements Serializable {
    private transient ImageView starimage;
    private boolean enable_star;

    Star(ImageView a, Boolean e) {
        this.starimage = a;
        this.enable_star = e;
    }

    public ImageView getimage() {
        return starimage;
    }

    public boolean is_notclaimed() {
        return enable_star;
    }

    public void set_claimed(boolean b) {
        enable_star = b;
    }

    public void setVisible(Boolean g) {
//        System.out.println(" called for star: "+g);
        starimage.setVisible(g);
    }
}
interface Collidable
{
    boolean CollisionObs(Obstacle star, Group patterns, Ball b);
}