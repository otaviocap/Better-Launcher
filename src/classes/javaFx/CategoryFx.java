package classes.javaFx;

import classes.helper.Filter;
import classes.java.Category;
import controllers.MainScreenController;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;


public class CategoryFx extends Button {

    private Category parentCategory;
    
    private MainScreenController msc;
    
    public CategoryFx(Category ParentCategory, MainScreenController msc) {
        super(ParentCategory.getName());
        this.parentCategory = ParentCategory;
        this.msc = msc;
        super.setStyle("-fx-background-color: transparent;"
                    +  "-fx-prompt-text-fill: #000000;"
                    +  "-fx-font-family: Montserrat;"
                    +  "-fx-font-size: 20px;");
        super.setOnAction((a) -> {
            Filter.toggleFilter(parentCategory);
            msc.refreshApps();
            System.out.println(Filter.getFilters().toString());
        });
    }
    

}
