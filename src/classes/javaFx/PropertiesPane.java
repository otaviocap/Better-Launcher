package classes.javaFx;

import classes.java.App;
import classes.java.Category;
import controllers.MainScreenController;
import db.daos.AppDao;
import db.daos.CategoryDao;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label; 
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.StringConverter;

public class PropertiesPane extends VBox {
    
    private boolean[] inScene = {false, false, false};
    
    private MainScreenController msc;
    
    private App lastApp;
    
    private Category lastCategory;
        
    public PropertiesPane(MainScreenController msc) {
        super();
        this.msc = msc;
        reset();
        
    }
    
    
    // ================== HELPER METHODS ======================
    
    private void setDarkerBackground() {
        Stop[] stops = new Stop[]{new Stop(0, Color.web("#53FA82")), new Stop(1, Color.web("#4ba364"))};
        setBackground(new Background(new BackgroundFill(new LinearGradient(.5, .5, 1.5, 1.5, true, CycleMethod.REFLECT, stops), CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    public void reset() {
        super.getChildren().clear();
        super.setAlignment(Pos.TOP_CENTER);
        super.setPrefHeight(638);
        super.setPrefWidth(300);
        super.setBackground(new Background(new BackgroundFill(Color.web("53FA82"), CornerRadii.EMPTY, Insets.EMPTY)));
        super.setPadding(new Insets(10, 10, 10, 10));
        super.setSpacing(20);
        super.setEffect(null);
    }
    
    // ====================== SCENES ===========================
    
    // =================== APP SCENES ==========================
    
    public void setAppScene(App app) {
        reset();
        if (!app.equals(lastApp)) {
            lastApp = app;
            Rectangle imgRect = new Rectangle();
            imgRect.setWidth(145);
            imgRect.setHeight(202);
            imgRect.setArcHeight(10);
            imgRect.setArcWidth(10);

            setDarkerBackground();
            if (app.getImgUrl() != null && !app.getImgUrl().equals("")) {
                try {
                    Image img = new Image("file:///"+app.getImgUrl());
                    imgRect.setFill(new ImagePattern(img));

                } catch (Exception e) {

                }
            } else {
                System.out.println("Here");
                imgRect.setFill(Color.TRANSPARENT);
            }

            Label nameText = new Label(app.getName());
            nameText.setFont(new Font("Montserrat Light", 25));

            Label desc = new Label(app.getDescription());
            desc.setFont(new Font("Montserrat Light", 13));
            desc.setMaxWidth(200d);
            desc.setMaxHeight(100d);
            desc.setAlignment(Pos.CENTER);
            desc.setWrapText(true);

            HBox hb = categoriesAndReleaseYearDisplay(app);
                    
            VBox vb = runAndDeleteOptions(app);
                    
            super.getChildren().addAll(
                    imgRect,
                    nameText,
                    desc,
                    hb,
                    vb

            );
        } else {
            lastApp = null;
        }
        inScene[0] = false;
        inScene[1] = false;
        inScene[2] = false;
    }
    
    public void setAddAppScene() {
        reset();
        if (!inScene[0]) {
            inScene[0] = true;
            inScene[1] = false;
            inScene[2] = false;
            
            setDarkerBackground();

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
            
            ArrayList<Category> categories = enableCategories(hb2, hb3, lb, true);
            
            
            //Advanced Options
            TextField ea = customTf("Executable Arguments");
            ea.setPrefHeight(20d);

            // Required Fields Warning
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
                String releaseYear = ((TextField) hb1.getChildren().get(0)).getText().isEmpty() ? "0" : ((TextField) hb1.getChildren().get(0)).getText();
                String description = ((TextField) hb1.getChildren().get(1)).getText().isEmpty() ? "" : ((TextField) hb1.getChildren().get(1)).getText();
                Category category = ((ComboBox<Category>) hb3.getChildren().get(0)).getValue() != null ? ((ComboBox<Category>) hb3.getChildren().get(0)).getValue() : null;
                Boolean gameOrSoftware = ((RadioButton) hb2.getChildren().get(0)).isSelected();
                String exec = ea.getText().isEmpty() ? "" : ea.getText();



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
            inScene[0] = false;
            inScene[1] = false;
            inScene[2] = false;
        }
    }
    
    public void setEditAppScene(App app) {
        reset();
        if (!inScene[1]) {
            inScene[0] = false;
            inScene[1] = true;
            inScene[2] = false;
            
            setDarkerBackground();

            //File Picker
            FilePicker fp = new FilePicker("Select an Image");
            fp.setImageFilters();
            if (!app.getImgUrl().equals("") && app.getImgUrl() != null) {
                fp.setFile(new File(app.getImgUrl()));
                fp.setImageAsBackground(app.getImgUrl());
                fp.getText().setVisible(false);
            }

            //TextField
            TextField tf = customTf("Name of the app*");
            tf.setText(app.getName());

            //Path picker
            FilePicker pp = pathPicker();
            pp.getText().setText(app.getPathExec());
            pp.setFile(new File(app.getPathExec()));
                    

            //Release Year and Description
            HBox hb1 = releaseYearDesc();
            ((TextField) hb1.getChildren().get(0)).setText(Integer.toString(app.getReleaseYear()));
            ((TextField) hb1.getChildren().get(1)).setText(app.getDescription());
            
            
            //Radio buttons
            HBox hb2 = gameOrSoftware();


            //Categories
            HBox hb3 = categoriesPicker();
            
            Label lb = new Label("Categories selected: ");
            
            ArrayList<Category> categories = enableCategories(hb2, hb3, lb, false);
            
            if (app.isGame()) {
                ((RadioButton) hb2.getChildren().get(0)).fire();
            } else {
                ((RadioButton) hb2.getChildren().get(1)).fire();
            }
            categories.addAll(app.getCategories());
            ((Button) hb3.getChildren().get(1)).fire();
            
            //Advanced Options
            TextField ea = customTf("Executable Arguments");
            ea.setPrefHeight(20d);
            ea.setText(app.getArgs());

            
            // Required Field Warning
            Label text = new Label();
            text.setText("Required Fields with an *");
            text.setStyle("-fx-font-family: Montserrat;" + "-fx-font-size: 12;");
            text.setTextAlignment(TextAlignment.CENTER);
            text.setPrefHeight(65);

            
            // Edit Button
            Button bt = customButton("Edit");

            bt.setOnAction((event) -> {
                String image = fp.getFile() != null ? fp.getFile().getAbsolutePath() : null;
                String nameOfTheApp = tf.getText().isEmpty() ? null : tf.getText();
                String path = pp.getFile() != null ? pp.getFile().getAbsolutePath() : null;
                String releaseYear = ((TextField) hb1.getChildren().get(0)).getText().isEmpty() ? "0" : ((TextField) hb1.getChildren().get(0)).getText();
                String description = ((TextField) hb1.getChildren().get(1)).getText().isEmpty() ? "" : ((TextField) hb1.getChildren().get(1)).getText();
                Boolean gameOrSoftware = ((RadioButton) hb2.getChildren().get(0)).isSelected();
                String exec = ea.getText().isEmpty() ? "" : ea.getText();
                
                app.setImgUrl(image);
                app.setName(nameOfTheApp);
                app.setPathExec(path);
                app.setReleaseYear(Integer.parseInt(releaseYear));
                app.setDescription(description);
                app.setIsGame(gameOrSoftware);
                app.setArgs(exec);
                app.setCategories(categories);
                

                System.out.println("Image: " + image + "\n" +
                                   "Name of the app: " + nameOfTheApp + "\n" +
                                   "Path of the exec: " + path + "\n" +
                                   "Release Year: " + releaseYear + "\n" +
                                   "Description: " + description  + "\n" +
                                   "Categorie: " + categories.toString() + "\n" +
                                   "Game or Software: " + (gameOrSoftware ? "Game" : "Software") + "\n" +
                                   "Executable Arguments: " + exec + "\n");
                if (nameOfTheApp != null && path != null) {
                    new AppDao().alter(app);
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
            inScene[0] = false;
            inScene[1] = false;
            inScene[2] = false;
        }
    }
    
    // =================== CATEGORY SCENES ======================

    public void setAddCategorieScene() {
        reset();
        if (!inScene[2]) {
            
            inScene[0] = false;
            inScene[1] = false;
            inScene[2] = true;
            setDarkerBackground();
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
            inScene[0] = false;
            inScene[1] = false;
            inScene[2] = false;
        }
    }
    
    public void setEditCategoryScene(Category c) {
        reset();
        if (!c.equals(lastCategory)) {
            lastCategory = c;
            
            inScene[0] = false;
            inScene[1] = false;
            inScene[2] = false;
            setDarkerBackground();
            super.setAlignment(Pos.CENTER);

            //Text Field
            TextField tf = customTf("Name of the category*");
            tf.setText(c.getName());
            

            // Radio Buttons
            HBox hb = gameOrSoftware();
            hb.setAlignment(Pos.CENTER);
            hb.setSpacing(10);
            
            if (c.getIsForAGame()) {
                ((RadioButton) hb.getChildren().get(0)).fire();
            } else {
                ((RadioButton) hb.getChildren().get(0)).fire();
            }


            // Register
            Button bt = customButton("Edit");

            super.getChildren().addAll(tf, hb, bt);

            bt.setOnAction((event) -> {
                String name = tf.getText().isEmpty() ? null : tf.getText();
                Boolean gameOrSoftware = ((RadioButton) hb.getChildren().get(0)).isSelected();
                
                c.setName(name);
                c.setIsForAGame(gameOrSoftware);

                System.out.println(
                                   "Name of the categorie: " + name + "\n" +
                                   "Game or Software: " + (gameOrSoftware ? "Game" : "Software") + "\n");
                if (name != null) {
                    new CategoryDao().alter(c);
                    reset();
                } else {
                    tf.setStyle(tf.getStyle() + "-fx-prompt-text-fill: #c71423;");
                }
            });   
        } else {
            inScene[0] = false;
            inScene[1] = false;
            inScene[2] = false;
            lastCategory = null;
        }
    } 
    
    
    // ==================== CUSTOM STUFF =======================
    
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
        
    private ArrayList enableCategories(HBox hb2, HBox hb3, Label lb, boolean fireEvent) {
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
            } else if (cb.getValue() != null){
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
        if (fireEvent) {
            ((RadioButton) hb2.getChildren().get(1)).fire();
        }
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
    
    private Button simpleButton(String name) {
        Button bt = customButton(name);
        bt.setBackground(Background.EMPTY);
        bt.setStyle(bt.getStyle() + "-fx-background-color: transparent;");
        return bt;
    }
    
    private HBox categoriesAndReleaseYearDisplay(App app) {
        HBox hb = new HBox();
        ScrollPane sp = new ScrollPane();
        VBox vb1 = new VBox();
        VBox vb2 = new VBox();

        for (Category c: app.getCategories()) {
            Label a = new Label(c.getName());
            a.setWrapText(true);
            a.setStyle("-fx-text-fill: -fx-text-base-color; -fx-font-family: Montserrat Light; -fx-font-size: 15; -fx-text-alignment: center;");
            a.setMaxWidth(100d);
            vb1.getChildren().add(a);
        }

        vb2.getChildren().add(new Label(Integer.toString(app.getReleaseYear())));
        ((Label) vb2.getChildren().get(0)).setFont(new Font("Montserrat", 20));

        vb1.setAlignment(Pos.CENTER);
        vb2.setAlignment(Pos.CENTER);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(10d);

        sp.setContent(vb1);
        sp.setPrefHeight(100d);
        sp.setPrefWidth(100d);
        sp.setBackground(Background.EMPTY);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setStyle("-fx-border-color: transparent;"
                  + "-fx-background-color: transparent;"
                  + "-fx-background: transparent;");
        hb.getChildren().addAll(sp, vb2);
        return hb;
        }
    
    private VBox runAndDeleteOptions(App app) {
        VBox vb3 = new VBox();
        Button bt = customButton("Run");
        bt.setPrefHeight(50d);
        bt.setStyle(bt.getStyle() + "-fx-font-size: 20;");
            
        HBox hb4 = new HBox();
        Button btRemove = simpleButton("Remove App");
        hb4.getChildren().add(btRemove);

        vb3.setAlignment(Pos.BOTTOM_CENTER);
        hb4.setAlignment(Pos.CENTER);
        hb4.setMaxWidth(200d);
        vb3.setPrefHeight(200d);
        vb3.getChildren().addAll(bt, hb4);
        vb3.setSpacing(10d);

        bt.setOnAction((event) -> {
            try {
                Runtime rt = Runtime.getRuntime();
                Process myProcess = rt.exec(app.getPathExec() + " " + app.getArgs());
                System.out.println(myProcess.getOutputStream());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        btRemove.setOnAction((event) -> {
            hb4.getChildren().remove(btRemove);
            Button yes = simpleButton("yes");
            Button no = simpleButton("No");

            hb4.getChildren().addAll(yes, no);
            yes.setOnAction((a) -> {
                new AppDao().remove(app);
                msc.refreshApps();
                reset();
            });
            no.setOnAction((b) -> {
                hb4.getChildren().clear();
                hb4.getChildren().add(btRemove);
            });

        });
        return vb3;
    }
}
