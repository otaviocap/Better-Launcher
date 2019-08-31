package db.daos;

import classes.java.App;
import classes.java.Category;
import db.helper.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao implements Dao<Category> {

    @Override
    public boolean add(Category c) {
        
        //Registering in software table
        String sql = db.helper.cons.Categories.INSERT;
        int id;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, null);
                stmt.setString(2, c.getName());
                stmt.setBoolean(3, c.getIsForAGame());
                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        c.setId(id);
        return true;
    }

    @Override
    public boolean alter(Category c) {
                
        //Registering in software table
        String sql = db.helper.cons.Categories.UPDATE;
        int id;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, c.getName());
                stmt.setBoolean(2, c.getIsForAGame());
                stmt.setInt(3, c.getId());
                stmt.execute();
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        
        return true;
    }

    @Override
    public boolean remove(Category c) {
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(db.helper.cons.CategorySoftware.REMOVEALLBYCATEGORY)) {
                stmt.setInt(1, c.getId());
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(db.helper.cons.Categories.REMOVE)) {
                stmt.setInt(1, c.getId());
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean search(Category c) {
        ArrayList<Category> todos = searchAll();

        for (Category ca : todos) {
            if (c.equals(ca)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Category> searchAll() {
        ArrayList<Category> Categories = new ArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(db.helper.cons.Categories.SEARCH);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Category a = new Category();
                    a.setId(rs.getInt("categoryId"));
                    a.setName(rs.getString("name"));
                    a.setIsForAGame(rs.getBoolean("isForAGame"));
                    Categories.add(a);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Categories;
    }
    
    public ArrayList<Category> searchForGames(boolean bool) {
        ArrayList<Category> Categories = new ArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(db.helper.cons.Categories.SEARCHGAMES)) {
                stmt.setBoolean(1, bool);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Category a = new Category();
                    a.setId(rs.getInt("categoryId"));
                    a.setName(rs.getString("name"));
                    a.setIsForAGame(rs.getBoolean("isForAGame"));
                    Categories.add(a);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Categories.forEach((c) -> {System.out.println((c.getName()));});
        return Categories;
    }
    
    public Category searchByName(String name) {
        List<Category> categories = searchAll();
        for (Category c : categories) {
            if (c.getName().equals(name)) {
                return c;
            }
        } 
        return null;
    }
 
    public ArrayList<Category> searchBySoftwareId(int sid) {
        ArrayList<Category> Categories = new ArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(db.helper.cons.CategorySoftware.SEARCHBYSOFTWAREGETCATEGORIES)) {
                stmt.setInt(1, sid);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Category a = new Category();
                    a.setId(rs.getInt("categoryId"));
                    a.setName(rs.getString("name"));
                    a.setIsForAGame(rs.getBoolean("isForAGame"));
                    Categories.add(a);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Categories;
    }
    
    
}
