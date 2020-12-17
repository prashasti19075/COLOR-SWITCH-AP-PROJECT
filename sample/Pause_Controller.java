package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Pause_Controller{

    @FXML
    public void Menu() throws IOException {
        int currstars=Main.colorswitch.mygame.retStars();
        Main.colorswitch.updatestars(currstars);
        Scene homepage = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main.window.setScene(homepage);
    }
    @FXML
    public void resume()
    {
        Main.window.setScene(Main.colorswitch.gamepage);
        Main.colorswitch.mygame.play_music();
    }
    @FXML
    public void Restart() throws IOException {
        Main.colorswitch.mygame=new Game();
        Scene gamepage = FXMLLoader.load(getClass().getResource("Game_Page.fxml"));
        Main.window.setScene(gamepage);
    }
    @FXML
    public void SaveandExit(){
//        System.out.println("Here1");
        String filename=null;
        TextInputDialog td = new TextInputDialog("default");
        td.setHeaderText("Enter Game Name");
        File directoryPath = new File("src\\sample\\savedgames");
        File[] files=directoryPath.listFiles((dir, name) -> name.endsWith(".txt"));

        while(filename == null)
        {
            int ok=1;
            TextInputDialog dialog = new TextInputDialog("default");
            dialog.setTitle("Game Name");
            dialog.setHeaderText("Please enter your game name\n 1. Make sure to not repeat the names already saved \n 2. Make sure to have no special charecters");
            dialog.setContentText("Game Name: ");
            Optional<String> result = dialog.showAndWait();
            // The Java 8 way to get the response value (with lambda expression).
            if(!result.isEmpty())
            {
                String input=result.get();
                if(input.contains("."))
                {
                    filename=null;
                    ok=0;
                }
                input+=".txt";
                for(File file: files)
                {
//                    System.out.println("File Name is:"+ file.getName());
                    if(file.getName().equalsIgnoreCase(input))
                    {
//                        System.out.println(" Match ");
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                        confirm.setTitle("Confirmation Dialog");
                        confirm.setHeaderText("Your previous game "+ input.substring(0,input.indexOf(".txt"))+ " will be overwritten" );
                        confirm.setContentText("Are you ok with this?");

                        Optional<ButtonType> result2 = confirm.showAndWait();
                        if (result2.get() == ButtonType.OK)
                        {
                            break;
                        }
                        else {
                            ok=0;
                            break;
                        }

                    }
                }
                if(ok==1)
                {
//                    System.out.println("Accepted");
                    filename = input;
                    try
                    {
                        File newfile=new File("src\\sample\\savedgames\\"+filename);
                        if (newfile.createNewFile()) {
                            System.out.println("File created: " + newfile.getName());
                        } else {
                            System.out.println("File already exists.");
                        }
                    }
                    catch (IOException e)
                    {
                        System.out.println("An error occurred.");
                        filename=null;
                    }
                    try {
                        Main.Serialize(Main.colorswitch.mygame, filename);
                    }
                    catch(IOException e)
                    {
                        System.out.println(" Some Error ");
                    }
                    Alert exit_message = new Alert(Alert.AlertType.INFORMATION);
                    exit_message.setTitle("GAME SAVED");
                    exit_message.setHeaderText(null);
                    exit_message.setContentText("Your game has been saved, you will be directed to the main page now !!");
                    exit_message.showAndWait();
                    Scene homepage = null;
                    try {
                        homepage = FXMLLoader.load(getClass().getResource("sample.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Main.window.setScene(homepage);
                }
//                    System.out.println("Filename is: " + filename + " Input is: " + input);
                }
            }
        }
    }
