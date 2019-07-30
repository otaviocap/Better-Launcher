/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author otavi
 */
public class App extends StackPane {
    
    //interface vars
    private Rectangle background = new Rectangle();
    private Label text;
    private boolean hasImage = false;
    private ContextMenu cm;
    
    //app vars
    private String imgUrl = "";
    private String name;
    private String pathExec;
    private int releaseYear;
    private String description;
    private boolean isGame;
    private List<String> categories;
    private String args;
    
    
    public App(String name) {
        super();
        enableContextMenu();
        this.name = name;
        text = new Label(name);
        setDefaults();
        super.getChildren().add(background);
        super.getChildren().add(text);
    }

    public App(String name, String pathExec, 
            int releaseYear, String description, boolean isGame, List<String> categories, String args) {
        super();
        enableContextMenu();
        this.text = new Label(name);
        this.name = name;
        this.pathExec = pathExec;
        this.releaseYear = releaseYear;
        this.description = description;
        this.isGame = isGame;
        this.categories = categories;
        this.args = args;
        
        setDefaults();
        super.getChildren().add(background);
        super.getChildren().add(text);
    }
    
    public App(String imgUrl, String name, String pathExec, 
            int releaseYear, String description, boolean isGame, List<String> categories) {
        super();
        enableContextMenu();
        this.imgUrl = imgUrl;
        this.text = new Label(name);
        this.name = name;
        this.pathExec = pathExec;
        this.releaseYear = releaseYear;
        this.description = description;
        this.isGame = isGame;
        this.categories = categories;
        this.args = "";

        this.hasImage = true;
        
        setDefaults();
        super.getChildren().add(background);
        super.getChildren().add(text);
    }
    
    //Remove pls
       public App(String imgUrl, String name, String pathExec, 
            int releaseYear, String description, boolean isGame) {
        super();
        enableContextMenu();
        this.imgUrl = imgUrl;
        this.text = new Label(name);
        this.name = name;
        this.pathExec = pathExec;
        this.releaseYear = releaseYear;
        this.description = description;
        this.isGame = isGame;
        this.categories = categories;
        this.args = "";
        
        this.hasImage = true;
        
        setDefaults();
        super.getChildren().add(background);
        super.getChildren().add(text);
    }
    
    
    
    
    private void setDefaults() {
        //background
        background.setWidth(145);
        background.setHeight(202);
        background.setArcHeight(10);
        background.setArcWidth(10);
        
        if (hasImage) {
            Image img = new Image("file:///"+imgUrl);
            background.setFill(new ImagePattern(img));
        } else {
            Stop[] stops = new Stop[]{new Stop(0, Color.web("#4ab1ff")), new Stop(1, Color.web("#2bffc3"))};
            background.setFill(new LinearGradient(0, 0, 1.0, 1.0, true, CycleMethod.REFLECT, stops));
        }
        //text
        text.setFont(new Font("Poiret One", 20));
        
    }
    
    private void enableContextMenu() {
        
        App me = this;
        
        cm = new ContextMenu(
                new MenuItem("Remove"),
                new MenuItem("Edit"));
        
        cm.getItems().get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(me.getText());
            }
        });
        
        //Context menu action
        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
                cm.show(me , event.getScreenX(), event.getScreenY());
            }
        });
    }
    
    public void run() {
        ProcessBuilder pb = new ProcessBuilder();
        List<String> command = new ArrayList<>();
        String i = pathExec +" "+ args;
        command.addAll(Arrays.asList(i.split(" ")));
        pb.command(command);
        try {
            pb.start();
        } catch (IOException ex) {
            System.out.println("An error occured while trying to execute the app " + ex.getMessage());
        }
    }

    
    public Label getText() {
        return text;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    
    
}
