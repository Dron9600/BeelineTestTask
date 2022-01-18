package kz.beeline.aprudnikov.repository;

import java.sql.*;

public class SqlLiteConnector {

    private final String url; // url example "jdbc:sqlite:C:/sqlite/"
    private Connection connection = null;

    public SqlLiteConnector(String url) {
        this.url = url;
    }

    public Connection getConnection() {
        return connection;
    }

    public void connect(String fileName) { // db file name example "BeelineTestTask.db"
        try {
            // create a connection to the database
            connection = DriverManager.getConnection(this.url + fileName);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createNewDatabaseConnection(String fileName) {
        String newDataBaseUrl = this.url + fileName;
        try {
            Connection newConnection = DriverManager.getConnection(newDataBaseUrl);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                connection = newConnection;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (this.connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
