package classes.javaFx;

import classes.helper.Filter;
import classes.java.Category;
import controllers.MainScreenController;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;


public class CategoryFx extends Button {

    private Category parentCategory;
    
    private MainScreenController msc;
    
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
    }

    public String getBaseCss() {
        return baseCss;
    }
    
    
    

}
