package kz.beeline.aprudnikov.repository;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final String url;
    private Connection connection;

    public DBConnection(String dbLocate, String dbName) { // "C:\\Users\\Lenovo\\Desktop\\Java\\Projects\\BeelineTestTask\\BTest\\TextFiles\\DB"; // "seconddb.db";
        this.url = "jdbc:sqlite:" + dbLocate + "\\" + dbName;
    }

    public void connect() {
        try {
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void createNewDatabase(String dbLocate, String dbName) {
        String url = "jdbc:sqlite:" + dbLocate  + dbName;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}