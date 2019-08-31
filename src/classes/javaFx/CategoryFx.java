package classes.javaFx;

import classes.helper.Filter;
import classes.java.Category;
import controllers.MainScreenController;
import db.daos.CategoryDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;


public class CategoryFx extends Button {

    private Category parentCategory;
    
    private MainScreenController msc;
    
    private ContextMenu cm;
    
    private final String baseCss = "-fx-background-color: transparent;"
                    +  "-fx-font-family: Montserrat;"
                    +  "-fx-font-size: 20px;";
    
    public CategoryFx(Category ParentCategory, MainScreenController msc) {
        super(ParentCategory.getName());
        this.parentCategory = ParentCategory;
        this.msc = msc;
        super.setStyle(baseCss);
        super.setOnAction((a) -> {
            Filter.toggleFilter(parentCategory, this);
            msc.refreshApps();
            System.out.println(Filter.getFilters().toString());
        });
        
        enableContextMenu();

    }

    public String getBaseCss() {
        return baseCss;
    }

    private void enableContextMenu() {
        
        CategoryFx me = this;
        
        cm = new ContextMenu(
                new MenuItem("Edit"),
                new MenuItem("Remove"));
        
        
        // Edit option
        cm.getItems().get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                msc.setEditCategory(parentCategory);
            }
        });
        
        // Remove Option
        cm.getItems().get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new CategoryDao().remove(parentCategory);
                msc.refreshCategories(parentCategory.getIsForAGame());
            }
        });
        
        //Context menu enable action
        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
                cm.show(me, event.getScreenX(), event.getScreenY());
            }
        });
    }
    
    
    

}
