package classes.javaFx;

import classes.java.App;
import classes.java.Category;
import controllers.MainScreenController;
import db.daos.AppDao;
import db.daos.CategoryDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label; 
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.StringConverter;

public class PropertiesPane extends VBox {
    
    private boolean inScene = false;
    
    private MainScreenController msc;
        
    public PropertiesPane(MainScreenController msc) {
        super();
        this.msc = msc;
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
    
    private TextField customTf(String name) {
        TextField tf = new TextField();
        tf.setPromptText(name);
        tf.setStyle("-fx-background-radius: 8;"
                +   "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #4ab1ff, #2bffc3);"
                +   "-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        tf.setPrefHeight(35);
        return tf;
    }
    
    private FilePicker pathPicker() {
        FilePicker pp = new FilePicker("Path to the Executable*");
        Rectangle ppRect = pp.getRect();
        ppRect.setWidth(300);
        ppRect.setHeight(50);
        ppRect.setArcHeight(15);
        ppRect.setArcWidth(15);
        return pp;
    }
    
    private HBox releaseYearDesc() {
                    HBox hb1 = new HBox();
            TextField ry = new TextField();
            TextField ds = new TextField();

            ry.setPromptText("Release Year");
            ds.setPromptText("Description");

            ry.setStyle("-fx-background-radius: 8 0 0 8;"
                    +   "-fx-background-color: linear-gradient(from 0% 0% to 110% 110%, #4ab1ff, #2bffc3);"
                    +   "-fx-prompt-text-fill: #000000;"
                    +   "-fx-font-family: Montserrat;"
            );

            ds.setStyle("-fx-background-radius: 0 8 8 0;"
                    +   "-fx-background-color: linear-gradient(from -10% -10% to 100% 100%, #2bffc3, #4ab1ff);"
                    +   "-fx-prompt-text-fill: #000000;"
                    +   "-fx-font-family: Montserrat;"
            );

            ry.setPrefHeight(50);
            ry.setPrefWidth(300);
            ds.setPrefHeight(50);
            ds.setPrefWidth(300);

            ry.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\d*")) {
                    ry.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });

            hb1.getChildren().addAll(ry, ds);
            return hb1;
    }
    
    private HBox gameOrSoftware() {
        HBox hb2 = new HBox();

        ToggleGroup tg = new ToggleGroup();

        RadioButton rbGame = new RadioButton();
        RadioButton rbSoftware = new RadioButton();

        rbGame.setToggleGroup(tg);
        rbSoftware.setToggleGroup(tg);

        rbGame.setText("Game");
        rbSoftware.setText("Software");

        rbGame.setStyle("-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );

        rbSoftware.setStyle("-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );

        hb2.getChildren().addAll(rbGame, rbSoftware);
        hb2.setAlignment(Pos.CENTER);
        hb2.setSpacing(10);
        return hb2;
    }
    
    private HBox categoriesPicker() {            
        HBox hb3 = new HBox();

        Button btAdd = new Button(">>>");
        btAdd.setPrefWidth(100);
        btAdd.setPrefHeight(50);
        hb3.setSpacing(5d);

        btAdd.setStyle("-fx-background-radius: 8;"
                +   "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #4ab1ff, #2bffc3);"
                +   "-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );

        ComboBox<Category> cb = new ComboBox();
        cb.setStyle("-fx-background-radius: 8;"
                +   "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #4ab1ff, #2bffc3);"
                +   "-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        
        cb.setPrefHeight(50);
        cb.setPrefWidth(300);

        hb3.getChildren().addAll(cb, btAdd);

        
        return hb3;
    }
        
    private ArrayList enableCategories(HBox hb2, HBox hb3, Label lb) {
        ArrayList<Category> categories = new ArrayList<>();
        CategoryDao cd = new CategoryDao();
        ArrayList<Category> lc = new ArrayList<>();
        
        ComboBox<Category> cb = ((ComboBox) hb3.getChildren().get(0));
        Button btAdd = ((Button) hb3.getChildren().get(1));

        Comparator<Category> comp = new Comparator<Category>() {
                    @Override
                    public int compare(final Category object1, final Category object2) {
                        return object1.getName().compareTo(object2.getName());
                    }
                };



        cb.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category c) {
                return c.getName();
            }

            @Override
            public Category fromString(String name) {
                return new CategoryDao().searchByName(name);
            }
        });
        
        btAdd.setOnAction((event) -> {
            if (categories.contains(cb.getValue())) {
                categories.remove(cb.getValue());
            } else {
                categories.add(cb.getValue());
            }
            lb.setText("Categories selected: "+ categories.toString());

        });
        
        ((RadioButton) hb2.getChildren().get(1)).setOnAction((event) -> {
                lc.clear();
                categories.clear();
                lb.setText("Categories selected: ");
                cd.searchForGames(false).forEach((e) -> lc.add(e));
                lc.sort(comp);
                cb.getItems().setAll(lc);
            });
            
        ((RadioButton) hb2.getChildren().get(0)).setOnAction((event) -> {
           lc.clear();
           categories.clear();
           lb.setText("Categories selected: ");
           cd.searchForGames(true).forEach((e) -> lc.add(e));
           lc.sort(comp);
           cb.getItems().setAll(lc);
        });
            
        ((RadioButton) hb2.getChildren().get(1)).fire();
        return categories;
    }
    
    private Button customButton(String name) {
        Button bt = new Button();
        bt.setText(name);
        bt.setPrefWidth(300);
        bt.setStyle("-fx-background-radius: 8;"
                +   "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #4ab1ff, #2bffc3);"
                +   "-fx-prompt-text-fill: #000000;"
                +   "-fx-font-family: Montserrat;"
        );
        return bt;
    }
    
    
    
    public void setAddAppScene() {
        reset();
        if (!inScene) {
            inScene = true;

            //File Picker
            FilePicker fp = new FilePicker("Select an Image");
            fp.setImageFilters();

            //TextField
            TextField tf = customTf("Name of the app*");

            //Path picker
            FilePicker pp = pathPicker();
                    

            //Release Year and Description
            HBox hb1 = releaseYearDesc();
            
            
            //Radio buttons
            HBox hb2 = gameOrSoftware();

            //Categories
            
            HBox hb3 = categoriesPicker();
            
            Label lb = new Label("Categories selected: ");
            
            ArrayList<Category> categories = enableCategories(hb2, hb3, lb);
            
            
            //Advanced Options
            TextField ea = new TextField();

            ea.setStyle("-fx-background-radius: 8;"
                    +   "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #4ab1ff, #2bffc3);"
                    +   "-fx-prompt-text-fill: #000000;"
                    +   "-fx-font-family: Montserrat;"
            );
            ea.setPrefHeight(20);
            ea.setPromptText("Executable Arguments");

            Label text = new Label();
            text.setText("Required Fields with an *");
            text.setStyle("-fx-font-family: Montserrat;" + "-fx-font-size: 12;");
            text.setTextAlignment(TextAlignment.CENTER);
            text.setPrefHeight(65);

            //Register
            
            Button bt = customButton("Register");

            bt.setOnAction((event) -> {
                String image = fp.getFile() != null ? fp.getFile().getAbsolutePath() : null;
                String nameOfTheApp = tf.getText().isEmpty() ? null : tf.getText();
                String path = pp.getFile() != null ? pp.getFile().getAbsolutePath() : null;
                String releaseYear = ((TextField) hb1.getChildren().get(0)).getText().isEmpty() ? null : ((TextField) hb1.getChildren().get(0)).getText();
                String description = ((TextField) hb1.getChildren().get(1)).getText().isEmpty() ? null : ((TextField) hb1.getChildren().get(1)).getText();
                Category category = ((ComboBox<Category>) hb3.getChildren().get(0)).getValue() != null ? ((ComboBox<Category>) hb3.getChildren().get(0)).getValue() : null;
                Boolean gameOrSoftware = ((RadioButton) hb2.getChildren().get(0)).isSelected();
                String exec = ea.getText().isEmpty() ? null : ea.getText();



                System.out.println("Image: " + image + "\n" +
                                   "Name of the app: " + nameOfTheApp + "\n" +
                                   "Path of the exec: " + path + "\n" +
                                   "Release Year: " + releaseYear + "\n" +
                                   "Description: " + description  + "\n" +
                                   "Categorie: " + category.toString() + "\n" +
                                   "Game or Software: " + (gameOrSoftware ? "Game" : "Software") + "\n" +
                                   "Executable Arguments: " + exec + "\n");
                if (nameOfTheApp != null && path != null) {
                    new AppDao().add(new App(nameOfTheApp, path, Integer.parseInt(releaseYear), description, gameOrSoftware, categories, exec, image));
                    reset();
                } else {
                    if (nameOfTheApp == null) { tf.setStyle(tf.getStyle() + "-fx-prompt-text-fill: #c71423;");}
                    if (path == null) {pp.getText().setTextFill(Color.web("#c71423"));}
                }
            });

            super.getChildren().addAll(
                    fp,
                    tf,
                    pp,
                    hb1,
                    hb2,
                    hb3,
                    lb,
                    ea,
                    text,
                    bt
            );
        } else {
            inScene = false;
        }
    }
    
    public void setAppScene(App app) {
        //todo
    }
    
    public void setAddCategorieScene() {
        reset();
        if (!inScene) {
            inScene = true;
            super.setAlignment(Pos.CENTER);

            //Text Field
            TextField tf = customTf("Name of the category*");
            

            // Radio Buttons
            HBox hb = gameOrSoftware();
            hb.setAlignment(Pos.CENTER);
            hb.setSpacing(10);
            
            ((RadioButton) hb.getChildren().get(1)).fire();


            // Register
            Button bt = customButton("Register");

            super.getChildren().addAll(tf, hb, bt);

            bt.setOnAction((event) -> {
                String name = tf.getText().isEmpty() ? null : tf.getText();
                Boolean gameOrSoftware = ((RadioButton) hb.getChildren().get(0)).isSelected();

                System.out.println(
                                   "Name of the categorie: " + name + "\n" +
                                   "Game or Software: " + (gameOrSoftware ? "Game" : "Software") + "\n");
                if (name != null) {
                    new CategoryDao().add(new Category(name, gameOrSoftware));
                    reset();
                } else {
                    tf.setStyle(tf.getStyle() + "-fx-prompt-text-fill: #c71423;");
                }
            });   
        } else {
            inScene = false;
        }
    }
}
