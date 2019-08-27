package controllers;

import classes.java.Category;
import classes.javaFx.AppFx;
import classes.javaFx.CategoriesPane;
import classes.javaFx.PropertiesPane;
import db.daos.AppDao;
import db.daos.CategoryDao;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
    
    @FXML
    private Rectangle addGame;
    
    
    
    private PropertiesPane properties;
    private VBox categories;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lnDefaults.put("close", closeButton.getFill());
        lnDefaults.put("minimize", minimizeButton.getFill());
        masterPane.setRight(new PropertiesPane());
        properties = (PropertiesPane) masterPane.getRight();
        categories = (VBox) masterPane.getLeft();
        categories.setBackground(Background.EMPTY);
        
        getApps();
        getCategories();
                
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
        properties.setAddCategorieScene();
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

    @FXML
    private void addSoftwareAction(MouseEvent event) {
        properties.setAddAppScene();
    }

    private void getApps() {
        AppDao ad = new AppDao();
        List<AppFx> appsFx = new ArrayList<>();
        ad.searchAll().forEach((app) -> {
            appsFx.add(new AppFx(app));
        });
        apps.getChildren().addAll(appsFx);
    }
    
    private void getCategories() { 
        CategoryDao cd = new CategoryDao();
        ((VBox) ((ScrollPane) categories.getChildren().get(6)).getContent()).getChildren().add(new Label("Veio de dentro")); 
    }
}
