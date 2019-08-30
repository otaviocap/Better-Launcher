package db.tests;

import classes.java.App;
import classes.java.Category;
import db.daos.AppDao;
import db.daos.CategoryDao;
import java.util.ArrayList;
import java.util.List;

public class testApp {
    
    public static void main(String[] args) {
        ArrayList<Category> c = new CategoryDao().searchBySoftwareId(5);
        c.forEach((Category cc) -> System.out.println(cc.getName()));
    }
}
