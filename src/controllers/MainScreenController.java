package controllers;

import classes.helper.Filter;
import classes.java.App;
import classes.java.Category;
import classes.javaFx.AppFx;
import classes.javaFx.CategoryFx;
import classes.javaFx.PropertiesPane;
import db.daos.AppDao;
import db.daos.CategoryDao;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    private Stage stage;
    
    private HashMap<Integer, ArrayList<Category>> AllCategories;

    
    private double x,y;
    private boolean[] categoriesSet;
    private ArrayList<AppFx> appsFx;
    private HashMap<String, Paint> lnDefaults;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        x=0;
        y=0;
        AllCategories = new HashMap<>();
        appsFx = new ArrayList<>();
        lnDefaults = new HashMap<>();
        categoriesSet = new boolean[]{false, false};
        
        // Buttons on top (Close, minimize) Default Style
        lnDefaults.put("close", closeButton.getFill());
        lnDefaults.put("minimize", minimizeButton.getFill());
        
        // Properties pane 
        masterPane.setRight(new PropertiesPane(this));
        properties = (PropertiesPane) masterPane.getRight();
        
        // Categories VBox
        ((VBox) masterPane.getLeft()).setBackground(Background.EMPTY);
        categories = ((VBox) ((ScrollPane) ((VBox) masterPane.getLeft()).getChildren().get(6)).getContent());
        
        categories.setAlignment(Pos.TOP_CENTER);
        categories.setSpacing(10d);

        refreshApps();
        makeDraggable();
    }
    
    @FXML
    private void gamesButtonAction(ActionEvent event) {
        Filter.clear();
        refreshCategories(true);
    }

    @FXML
    private void softwareButtonAction(ActionEvent event) {
        Filter.clear();
        refreshCategories(false);
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
    
    private void getCategories(boolean game) { 
        categories.getChildren().clear();
        if ((categoriesSet[0] == true && game) || (categoriesSet[1] && !game)) {
            categoriesSet[0] = false;
            categoriesSet[1] = false;
            refreshApps();
            Filter.clear();
        } else {
            if (game) {
                categoriesSet[0] = true;
                Filter.setIsForGame(true);
            } else {
                categoriesSet[1] = true;
                Filter.setIsForGame(false);
            }
            ArrayList<Category> cs = AllCategories.get(game ? 1 : 0);
            cs.sort((final Category object1, final Category object2) -> object1.getName().compareTo(object2.getName()));
            for (Category c: cs) {
                categories.getChildren().add(new CategoryFx(c, this));
            }
            refreshApps();
        }
        
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
    
    public void setApp(App app) {
        properties.setAppScene(app);
    }
    
    public void setEditApp(App app) {
        properties.setEditAppScene(app);
    }

    public void refreshApps() {
        AppDao ad = new AppDao();
        apps.getChildren().removeAll(appsFx);
        appsFx.clear();
        ad.searchAll().forEach((app) -> {
            if (Filter.checkApp(app)) {
                appsFx.add(new AppFx(app, this));
            }
        });
        apps.getChildren().addAll(appsFx);
    }
    
    public void refreshCategories(boolean game) {
        CategoryDao cd = new CategoryDao();
        
        categories.getChildren().clear();
        
        if (!game) {
            AllCategories.remove(0);
            AllCategories.put(0, cd.searchForGames(false));
            getCategories(game);
        } else {
            AllCategories.remove(1);
            AllCategories.put(1, cd.searchForGames(true));
            getCategories(game);
        }
    }
    
    public void setEditCategory(Category c) {
        properties.setEditCategoryScene(c);
    }
}
