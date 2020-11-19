package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.io.Serializable;

public class Classes {

    class Game {
        Game()
        {

        }
    }
    static class App implements Serializable {
        ImageView[] images;
        Button[] buttons;
        Media music;
        private static int totalstars;
        Game mygame;
        static int video=0;
        Controller cont;
        App() {
            images = new ImageView[5];
            buttons = new Button[4];
            images = new ImageView[5];
            buttons = new Button[4];
            buttons[0]=cont.videobutton;
            buttons[1]=cont.New_Game;
            buttons[2]=cont.Resume_Game;
            buttons[3]=cont.Exit_Game;

            images[0]=cont.Background_img;
            images[1]=cont.New_Game_img;
            images[2]=cont.Resume_Game_img;
            images[3]=cont.Exit_Game_img;
            images[4]=cont.Title_img;
       }
        public static void setVideo() {
            video = 1;
        }
        public static boolean is_video() {
            if (video == 0)
                return true;
            else
                return false;
        }
        public static int get_stars()
        {
            return totalstars;
        }
        public static void bonusstars()
        {
            totalstars+=30;
        }
        public static void updatestars(int stars)
        {
            totalstars+=stars;
        }

    }
}



