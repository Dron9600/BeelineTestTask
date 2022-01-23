package kz.beeline.aprudnikov.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final String url;
    private Connection connection;

    public DBConnection(String dbLocate, String dbName) { // "C:\\Users\\Lenovo\\Desktop\\Java\\Projects\\BeelineTestTask\\BTest\\TextFiles\\DB"; // "seconddb.db";
        this.url = "jdbc:sqlite:" + dbLocate + "\\" + dbName;
    }

    public Connection connect() {
        try {
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return this.connection;
    }

    public Connection getConnection() {
        return this.connection;
    }

}