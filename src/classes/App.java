/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

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
    
    private Rectangle background;
    private Label text;
    private boolean hasImage = false;
    private String imgUrl = "";
    private ContextMenu cm;
    
    public App(String name) {
        super();
        enableContextMenu();
        text = new Label(name);
        background = new Rectangle();
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
            Image img = new Image(imgUrl);
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
