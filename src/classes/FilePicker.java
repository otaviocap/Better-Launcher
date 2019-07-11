/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author otavi
 */
public class FilePicker extends StackPane {
    
    private Rectangle rec;
    private Label text;
    private File file;
    private Boolean isAnImagePicker = false;
    FileChooser fileChooser = new FileChooser();
    
    public FilePicker(String name) {
        super();
        text = new Label(name);
        rec = new Rectangle();
        setDefaults();
        super.getChildren().add(rec);
        super.getChildren().add(text);
        fileChooser.setTitle("Open an File");
        actions();
    }
    
    private void actions() {
        this.setOnMouseClicked((event) -> {
            file = fileChooser.showOpenDialog(((Stage) ((Node) event.getSource()).getScene().getWindow()));
            if (isAnImagePicker) {
                setImageAsBackground(file.getPath());
                text.setVisible(false);
            } else {
                text.setFont(new Font(text.getFont().getFamily(), 15));
                text.setText(file.getPath());
            }
        });
    }
    
    private void setDefaults() {
        //background
        rec.setWidth(145);
        rec.setHeight(202);
        rec.setArcHeight(10);
        rec.setArcWidth(10);
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#4ab1ff")), new Stop(1, Color.web("#2bffc3"))};
        rec.setFill(new LinearGradient(0, 0, 1.0, 1.0, true, CycleMethod.REFLECT, stops));
      
        //text
        text.setFont(new Font("Montserrat", 20));   
    }
    
    private void setImageAsBackground(String imgUrl) {
        Image img = new Image("file:///"+imgUrl);
        System.out.println("file://"+imgUrl);
        rec.setFill(new ImagePattern(img));
    }
    
    public void setImageFilters() {
        isAnImagePicker = true;
        fileChooser.setTitle("Open an Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Files", "*.*"),
            new FileChooser.ExtensionFilter("Jpg", "*.jpg"),
            new FileChooser.ExtensionFilter("Png", "*.png")
        );
    }
    
    public Label getText() {
        return text;
    }
    
    public File getFile() {
        return file;
    }
    
    public Rectangle getRect() {
        return rec;
    }
    
    
}
