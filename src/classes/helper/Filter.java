package classes.helper;

import classes.java.App;
import classes.java.Category;
import java.util.ArrayList;
import java.util.List;


public class Filter {
    
    private static ArrayList<Category> filters = new ArrayList<>();
    
    private static boolean isForGame = false; 
    
    public static void toggleFilter(Category c) {
        if (!filters.contains(c)) {
            filters.add(c);
        } else {
            filters.remove(c);
        }
    }

    public static ArrayList<Category> getFilters() {
        return filters;
    } 
    
    public static void clear() {
        filters.clear();
    }
    
    public static boolean checkApp(App a) {
        if (isForGame != a.isGame()) {
            return false;
        }
        
        if (filters.isEmpty()) {
            return true;
        }
        for (Category c: filters) {
            if (!a.getCategories().contains(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIsForGame() {
        return isForGame;
    }

    public static void setIsForGame(boolean isForGame) {
        Filter.isForGame = isForGame;
    }
    
    
}
