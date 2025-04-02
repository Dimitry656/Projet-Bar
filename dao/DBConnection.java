package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Paramètres de connexion
    private static final String URL = "jdbc:postgresql://147.93.95.108:5432/bar_db";
    private static final String USER = "bar_user";
    private static final String PASSWORD = "projetBar";

    /**
     * Obtient une connexion à la base de données.
     *
     * @return une instance de Connection.
     * @throws SQLException si la connexion échoue.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
