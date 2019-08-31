package classes.javaFx;

import java.io.File;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
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
        
        // This will set up the action of the file picker
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
        // Background
        rec.setWidth(145);
        rec.setHeight(202);
        rec.setArcHeight(10);
        rec.setArcWidth(10);
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#4ab1ff")), new Stop(1, Color.web("#2bffc3"))};
        rec.setFill(new LinearGradient(0, 0, 1.0, 1.0, true, CycleMethod.REFLECT, stops));
      
        // Text
        text.setStyle("-fx-font-family: Montserrat; -fx-font-size: 15;"); 
    }
    
    public void setImageAsBackground(String imgUrl) {
        Image img = new Image("file:///"+imgUrl);
        System.out.println("file://"+imgUrl);
        rec.setFill(new ImagePattern(img));
    }
    
    public void setImageFilters() {
        isAnImagePicker = true;
        fileChooser.setTitle("Open an Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg")
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

    public void setFile(File file) {
        this.file = file;
    }

    public void setIsAnImagePicker(Boolean isAnImagePicker) {
        this.isAnImagePicker = isAnImagePicker;
    }
    
    
}
