package classes.javaFx;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;


public class CategoriesPane extends VBox {
    
    private Button add;
    
    private Line separator;
    
    
    public CategoriesPane() {
        setPrefHeight(643);
        setPrefWidth(238);
        
        this.separator = new Line(0d,-100d,0d,100d);
        this.separator.setStrokeWidth(3d);
        this.separator.setStyle("-fx-background-color: linear-gradient(from -10% -10% to 100% 100%, #2bffc3, #4ab1ff);");
        
        this.add = new Button("Add");
        this.add.setStyle("-fx-background-radius: 15; -fx-background-color:linear-gradient(from 25% 25% to 100% 100%, #53FA82, #7460EA)");
        
        Label category = new Label("Categories");
        
        getChildren().addAll(
                category,
                separator,
                add
        );

    }
    

}
