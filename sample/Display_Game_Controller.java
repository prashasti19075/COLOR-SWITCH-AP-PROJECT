package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
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
        File directoryPath = new File("C:/Users/rachn/Desktop/java/GUI/src/sample");
        //List text files only
        System.out.println("------------Text files------------");
        File[] files=directoryPath.listFiles((dir, name) -> name.endsWith(".txt"));
        //Remember to shift this to proper class-method once tested
        ObservableList<String> items = FXCollections.observableArrayList();
        for (File file : files)
        {
            items.add(file.getName());
        }
        List.setItems(items);

        List.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ColoredCell();
            }
        });
    }
}