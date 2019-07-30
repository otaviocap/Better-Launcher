package db.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/launcher?useSSL=false",
                "root",
                "root");
    }

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        try {
            con = getConnection();

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar ao banco de dados");
        }
        if (con != null) {
            System.out.println("Conectado!");
            con.close();
        }
    }
}
