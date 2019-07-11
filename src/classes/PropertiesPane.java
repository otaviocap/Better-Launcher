/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;


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
    
    public void setAddScene() {
        reset();
        
        //File Picker
        FilePicker fp = new FilePicker("Select an Image");
        fp.setImageFilters();
        
        //TextField
        TextField tf = new TextField();
        tf.setPromptText("Name of the app");
        tf.setStyle("-fx-background-radius: 8;"
                +   "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #4ab1ff, #2bffc3);"
                +   "-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        tf.setPrefHeight(49);
        
        //Path picker
        FilePicker pp = new FilePicker("Path to the Executable");
        Rectangle ppRect = pp.getRect();
        ppRect.setWidth(300);
        ppRect.setHeight(50);
        ppRect.setArcHeight(15);
        ppRect.setArcWidth(15);
        
        
        super.getChildren().addAll(
                fp,
                tf,
                pp
        );
    }
    
    
}
