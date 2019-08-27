package classes.javaFx;

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
            if (file != null) {
                if (isAnImagePicker) {
                    setImageAsBackground(file.getPath());
                    text.setVisible(false);
                } else {
                    text.setTextFill(Color.BLACK);
                    text.setText(file.getPath());
                }
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
        text.setStyle("-fx-font-family: Montserrat; -fx-font-size: 15;"); 
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
