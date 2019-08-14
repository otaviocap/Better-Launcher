package classes;

import db.daos.AppDao;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label; 
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class PropertiesPane extends VBox {
        
    public PropertiesPane() {
        super();
        reset();
    }
    
    public void reset() {
        super.getChildren().clear();
        super.setAlignment(Pos.TOP_CENTER);
        super.setPrefHeight(638);
        super.setPrefWidth(300);
        super.setStyle("-fx-background-color: #53FA82;");
        super.setPadding(new Insets(10, 10, 10, 10));
        super.setSpacing(20);
    }
    
    public void setAddApp() {
        reset();
        
        //File Picker
        FilePicker fp = new FilePicker("Select an Image");
        fp.setImageFilters();
        
        //TextField
        TextField tf = new TextField();
        tf.setPromptText("Name of the app*");
        tf.setStyle("-fx-background-radius: 8;"
                +   "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #4ab1ff, #2bffc3);"
                +   "-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        tf.setPrefHeight(35);
        
        //Path picker
        FilePicker pp = new FilePicker("Path to the Executable*");
        Rectangle ppRect = pp.getRect();
        ppRect.setWidth(300);
        ppRect.setHeight(50);
        ppRect.setArcHeight(15);
        ppRect.setArcWidth(15);
        
        //Release Year and Description
        HBox hb1 = new HBox();
        TextField ry = new TextField();
        TextField ds = new TextField();
        
        ry.setPromptText("Release Year");
        ds.setPromptText("Description");
        
        ry.setStyle("-fx-background-radius: 8 0 0 8;"
                +   "-fx-background-color: linear-gradient(from 0% 0% to 110% 110%, #4ab1ff, #2bffc3);"
                +   "-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        
        ds.setStyle("-fx-background-radius: 0 8 8 0;"
                +   "-fx-background-color: linear-gradient(from -10% -10% to 100% 100%, #2bffc3, #4ab1ff);"
                +   "-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        
        ry.setPrefHeight(50);
        ry.setPrefWidth(300);
        ds.setPrefHeight(50);
        ds.setPrefWidth(300);
        
        ry.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                ry.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        hb1.getChildren().addAll(ry, ds);
        
        
        //Radio buttons
        HBox hb2 = new HBox();
        
        ToggleGroup tg = new ToggleGroup();
        
        RadioButton rbGame = new RadioButton();
        RadioButton rbSoftware = new RadioButton();
        
        rbGame.setToggleGroup(tg);
        rbSoftware.setToggleGroup(tg);
        
        rbGame.setText("Game");
        rbSoftware.setText("Software");
        
        rbGame.setStyle("-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        
        rbSoftware.setStyle("-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        
        rbSoftware.fire();
        
        hb2.getChildren().addAll(rbGame, rbSoftware);
        hb2.setAlignment(Pos.CENTER);
        hb2.setSpacing(10);
        
        
        //Categories
        ComboBox cb = new ComboBox();
        cb.getItems().addAll("1","2");
        cb.setStyle("-fx-background-radius: 8;"
                +   "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #4ab1ff, #2bffc3);"
                +   "-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        cb.setPrefHeight(50);
        cb.setPrefWidth(300);
        cb.setPromptText("Categories");
        
        //Advanced Options
        TextField ea = new TextField();
        
        ea.setStyle("-fx-background-radius: 8;"
                +   "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #4ab1ff, #2bffc3);"
                +   "-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        ea.setPrefHeight(20);
        ea.setPromptText("Executable Arguments");
        
        Label text = new Label();
        text.setText("Required Fields with an *");
        text.setStyle("-fx-font-family: Montserrat;" + "-fx-font-size: 12;");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setPrefHeight(65);
        
        //Register
        Button bt = new Button();
        bt.setText("Register");
        bt.setPrefWidth(300);
        bt.setStyle("-fx-background-radius: 8;"
                +   "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #4ab1ff, #2bffc3);"
                +   "-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        
        bt.setOnAction((event) -> {
            String image = fp.getFile() != null ? fp.getFile().getAbsolutePath() : null;
            String nameOfTheApp = tf.getText().isEmpty() ? null : tf.getText();
            String path = pp.getFile() != null ? pp.getFile().getAbsolutePath() : null;
            String releaseYear = ry.getText().isEmpty() ? null : ry.getText();
            String description = ds.getText().isEmpty() ? null : ds.getText();
            Object categories = cb.getValue() != null ? cb.getValue() : null;
            Boolean gameOrSoftware = rbGame.isArmed();
            String exec = ea.getText().isEmpty() ? null : ea.getText();
            
            
            
            System.out.println("Image: " + image + "\n" +
                               "Name of the app: " + nameOfTheApp + "\n" +
                               "Path of the exec: " + path + "\n" +
                               "Release Year: " + releaseYear + "\n" +
                               "Description: " + description  + "\n" +
                               "Categorie: " + categories + "\n" +
                               "Game or Software: " + (gameOrSoftware ? "Game" : "Software") + "\n" +
                               "Executable Arguments: " + exec + "\n");
            if (nameOfTheApp != null && path != null) {
                ArrayList<String> al = new ArrayList<>();
                al.add((String) categories);
                new AppDao().add(new App(nameOfTheApp, path, Integer.parseInt(releaseYear), description, gameOrSoftware, al, exec, image));
                reset();
            } else {
                if (nameOfTheApp == null) { tf.setStyle(tf.getStyle() + "-fx-prompt-text-fill: #c71423;");}
                if (path == null) {pp.getText().setTextFill(Color.web("#c71423"));}
            }
        });

        super.getChildren().addAll(
                fp,
                tf,
                pp,
                hb1,
                hb2,
                cb,
                ea,
                text,
                bt
        );
    }
    
    public void setAppScene(App app) {
        //todo
    }
    
    
}
