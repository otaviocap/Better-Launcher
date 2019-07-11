/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.App;
import classes.FilePicker;
import classes.PropertiesPane;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {

    
    private HashMap<String, Paint> lnDefaults = new HashMap();
    
    private Stage stage;
    
    private double x = 0, y = 0;
    
    private int c = 0;

    
    @FXML
    private HBox parent;
    
    @FXML
    private Circle minimizeButton;
    
    @FXML
    private Circle closeButton;

    @FXML
    private TilePane apps;
    
    @FXML
    private BorderPane masterPane;
    
    private PropertiesPane properties;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lnDefaults.put("close", closeButton.getFill());
        lnDefaults.put("minimize", minimizeButton.getFill());
        masterPane.setRight(new PropertiesPane());
        apps.getChildren().add(new FilePicker("AA"));
        properties = (PropertiesPane) masterPane.getRight();
                
        makeDraggable();
    }    

    @FXML
    private void minimizeAction(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void closeAction(MouseEvent event) {
        System.exit(-1);
    }


    @FXML
    private void closeButtonEntered(MouseEvent event) {
        Stop[] stops = new Stop[]{new Stop(1, Color.web("#960000")), new Stop(0, Color.web("#ffa6a6"))};
        closeButton.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops));
    }
    
    @FXML
    private void closeButtonExited(MouseEvent event) {
        closeButton.setFill(lnDefaults.get("close"));
    }
    
        
    public void makeDraggable() {
       parent.setOnMousePressed(((event) -> {
           x = event.getSceneX();
           y = event.getSceneY();
       }));
       
       parent.setOnMouseDragged(((event) -> {
           stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           stage.setX(event.getScreenX() - x);
           stage.setY(event.getScreenY() - y);
       }));
               
    }

    @FXML
    private void addButtonAction(ActionEvent event) {
        properties.setAddScene();
    }

    @FXML
    private void minimizeButtonExited(MouseEvent event) {
        minimizeButton.setFill(lnDefaults.get("minimize"));
    }

    @FXML
    private void minimizeButtonEntered(MouseEvent event) {
        Stop[] stops = new Stop[]{new Stop(1, Color.web("#5281b8")), new Stop(0, Color.web("#17a87f"))};
        minimizeButton.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops));
    }
}
