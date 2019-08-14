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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author otavi
 */
public class AppFx extends StackPane {
    
    //interface vars
    private Rectangle background = new Rectangle();
    private Label text;
    private ContextMenu cm;
    
    //app vars
    App app;
    
    
    public AppFx(String name) {
        super();
        app = new App();
        enableContextMenu();
        this.app.setName(name);
        text = new Label(this.app.getName());
        setDefaults();
        super.getChildren().add(background);
        super.getChildren().add(text);
    }

    public AppFx(String name, String pathExec, 
            int releaseYear, String description, boolean isGame, List<String> categories, String args, String imgUrl) {
        super();
        app = new App(name, pathExec, releaseYear, description, isGame, categories, args, imgUrl);
        enableContextMenu();
        this.text = new Label(this.app.getName());
        
        setDefaults();
        super.getChildren().add(background);
        super.getChildren().add(text);
    }
    
    public AppFx(String name, String pathExec, 
          int releaseYear, String description, boolean isGame, List<String> categories, String args) {
        super();
        app = new App(name, pathExec, releaseYear, description, isGame, categories, args);
        enableContextMenu();
        this.text = new Label(this.app.getName());

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
        
        if (hasImage()) {
            Image img = new Image("file:///"+app.getImgUrl());
            background.setFill(new ImagePattern(img));
        } else {
            Stop[] stops = new Stop[]{new Stop(0, Color.web("#4ab1ff")), new Stop(1, Color.web("#2bffc3"))};
            background.setFill(new LinearGradient(0, 0, 1.0, 1.0, true, CycleMethod.REFLECT, stops));
        }
        //text
        text.setFont(new Font("Poiret One", 20));
        
    }
    
    private void enableContextMenu() {
        
        AppFx me = this;
        
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
                cm.show(me, event.getScreenX(), event.getScreenY());
            }
        });
    }
    
    public void run() {
        ProcessBuilder pb = new ProcessBuilder();
        List<String> command = new ArrayList<>();
        String i = app.getPathExec() +" "+ app.getArgs();
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
        return !app.getImgUrl().isEmpty();
    }

    
}
