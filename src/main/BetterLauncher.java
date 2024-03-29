package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BetterLauncher extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        //Custom Fonts
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Major+Mono+Display");
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Poiret+One");
        scene.getStylesheets().add("/css/radio.css");
        scene.getStylesheets().add("/css/checkComboBox.css");
        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Better Launcher");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
