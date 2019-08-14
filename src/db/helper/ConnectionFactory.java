package db.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Launcher?useSSL=false",
                "root",
                "root");
    }

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        try {
            con = getConnection();

        } catch (SQLException ex) {
            System.out.println("Error while trying to connect to the mysql server");
        }
        if (con != null) {
            System.out.println("Conected!");
            con.close();
        }
    }
}
