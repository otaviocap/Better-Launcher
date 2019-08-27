package db.tests;

import classes.java.App;
import db.daos.AppDao;
import java.util.ArrayList;
import java.util.List;

public class testApp {
    
    public static void main(String[] args) {        
        /*
        List<String> c = new ArrayList<>();
        c.add("aaa");
        c.add("bbb");
        c.add("ddd");

        App a = new App("Test6", 
                "path/to/Exec", 
                1990, 
                "Some good description", 
                true,
                c,
                "- Args");
        */
        AppDao appDao = new AppDao();
        List<App> apps = appDao.searchAll();
        apps.get(3).setName("aasas");
        System.out.println(apps.get(2));
        appDao.alter(apps.get(3));
        }
   
    
}
