package db.daos;

import classes.java.App;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import db.helper.ConnectionFactory;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class AppDao implements Dao<App> {

    @Override
    public boolean add(App app) {
        
        //Registering in software table
        String sql = db.helper.cons.Software.INSERT;
        int id;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, app.getName());
                stmt.setBoolean(2, app.isGame());
                stmt.setString(3, app.getPathExec());
                stmt.setString(4, app.getArgs());
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
        
        
        //Regestering the software in info
        sql = db.helper.cons.Info.INSERT;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, id);
                stmt.setString(2, app.getDescription());
                stmt.setInt(3, app.getReleaseYear());
                stmt.setString(4, app.getImgUrl());
                stmt.setInt(5, app.getTimesExecuted());
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        app.setId(id);
        return true;
    }

    @Override
    public boolean alter(App app) {
                
        //Registering in software table
        String sql = db.helper.cons.Software.UPDATE;
        int id;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, app.getName());
                stmt.setBoolean(2, app.isGame());
                stmt.setString(3, app.getPathExec());
                stmt.setString(4, app.getArgs());
                stmt.setInt(5, app.getId());
                stmt.execute();
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        
        //Regestering the software in info
        sql = db.helper.cons.Info.UPDATE;
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, app.getDescription());
                stmt.setInt(2, app.getReleaseYear());
                stmt.setString(3, app.getImgUrl());
                stmt.setInt(4, app.getTimesExecuted());
                stmt.setInt(5, app.getId());                
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        
        return true;
    }

    @Override
    public boolean remove(App app) {
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(db.helper.cons.Info.REMOVE)) {
                stmt.setInt(1, app.getId());
                stmt.execute();
            }
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(db.helper.cons.Software.REMOVE)) {
                stmt.setInt(1, app.getId());
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean search(App app) {
        List<App> todos = searchAll();

        for (App ap : todos) {
            if (ap.equals(app)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<App> searchAll() {
        List<App> apps = new ArrayList();
        try {
            try (Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(db.helper.cons.InfoSoftware.SEARCH);
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    App a = new App();
                    a.setId(rs.getInt("softwareId"));
                    a.setName(rs.getString("name"));
                    a.setIsGame(rs.getBoolean("isAGame"));
                    a.setPathExec(rs.getString("pathToExecutable"));
                    a.setArgs(rs.getString("execParams"));
                    a.setDescription(rs.getString("info"));
                    a.setReleaseYear(rs.getInt("yearReleased"));
                    a.setImgUrl(rs.getString("picture"));
                    a.setTimesExecuted(rs.getInt("timesExecuted"));
                    apps.add(a);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return apps;
    }
    
}
