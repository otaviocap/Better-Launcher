package classes.javaFx;

import classes.java.App;
import controllers.MainScreenController;
import db.daos.AppDao;
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

public class AppFx extends StackPane {
    
    //interface vars
    private Rectangle background = new Rectangle();
    private Label text;
    private ContextMenu cm;
    private MainScreenController msc;
    
    //app vars
    App app;
    
    public AppFx(App app, MainScreenController msc) {
        super();
        this.app = app;
        this.msc = msc;
        
        text = new Label(this.app.getName());
        
        enableContextMenu();
        setDefaults();
        
       getChildren().add(background);
       getChildren().add(text);
       
       super.setOnMouseClicked((event) -> {
           msc.setApp(this.app);
       });
    }
    
    private void setDefaults() {
        //background
        super.setMaxWidth(145);
        super.setMaxHeight(202);
        background.setWidth(145);
        background.setHeight(202);
        background.setArcHeight(10);
        background.setArcWidth(10);
        
        if (hasImage()) {
            try {
                Image img = new Image("file:///"+app.getImgUrl());
                background.setFill(new ImagePattern(img));
                text.setVisible(false);
            } catch (Exception e) {
                app.setImgUrl("");
                text.setVisible(true);
                setDefaults();
            }
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
                new MenuItem("Edit"),
                new MenuItem("Remove"));
        
        
        // Edit option
        cm.getItems().get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                msc.setEditApp(app);
            }
        });

        // Remove option
        cm.getItems().get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new AppDao().remove(app);
                msc.refreshApps();
            }
        });
        
        
        // Context menu enable action
        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
                cm.show(me, event.getScreenX(), event.getScreenY());
            }
        });
    }   

    
    public Label getText() {
        return text;
    }

    public boolean hasImage() {
        return !app.getImgUrl().equals("");
    }

    
}
