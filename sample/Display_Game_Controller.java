package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
public class Display_Game_Controller implements Initializable {
    @FXML
    ListView List;
    class ColoredCell extends ListCell<String>
    {
        @Override
        public void updateItem(String number, boolean empty) {
            super.updateItem(number, empty);

            if(number == null || empty)
            {
                setText(null);
                this.setStyle("-fx-background-color: transparent;");
                setGraphic(null);
            }
            else {

                setText(number); // This line is very important!
                this.setStyle( "-fx-alignment: center; -fx-font-size: 25px; -fx-font-family:Times New Roman;" );
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File directoryPath = new File("src\\sample\\savedgames");
        //List text files only
//        System.out.println("------------Text files------------");
        File[] files=directoryPath.listFiles((dir, name) -> name.endsWith(".txt"));
        //Remember to shift this to proper class-method once tested
        ObservableList<String> items = FXCollections.observableArrayList();
        for (File file : files)
        {
            String fullname=file.getName();
            items.add(fullname.substring(0,fullname.indexOf(".txt")));
        }
        List.setItems(items);
        List.setCellFactory((Callback<ListView<String>, ListCell<String>>) param -> new ColoredCell());
        List.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
            String selectedItem = (String) List.getSelectionModel().getSelectedItem();
//            int index = List.getSelectionModel().getSelectedIndex();
            selectedItem+=".txt";
            System.out.println(" Selected File is: "+ selectedItem);
            Game newgame;
            try {
                    newgame = Main.Deserialize(selectedItem);
                    newgame.initialise();
                    Main.colorswitch.mygame = newgame;
                    Main.colorswitch.gamepage = FXMLLoader.load(getClass().getResource("Game_Page.fxml"));
//                newgame.initialise2();
                    Main.window.setScene(Main.colorswitch.gamepage);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Alert confirm = new Alert(Alert.AlertType.ERROR);
                    confirm.setTitle("Incompatible Game");
                    confirm.setHeaderText(" The game version is older than the current verion, hence, this game cannot be loaded,try another");
                    confirm.setContentText("Choose another game");
                    Optional<ButtonType> result2 = confirm.showAndWait();
                    if (result2.get() == ButtonType.OK)
                    {

                    }
                }
        });

    }

}