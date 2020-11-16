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
        private int totalstars;
        Game mygame;
        static int video=0;

        App() {
            images = new ImageView[5];
            buttons = new Button[4];
       }
        public int get_stars() {
            return totalstars;
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

    }
}



