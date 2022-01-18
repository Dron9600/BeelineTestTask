package kz.beeline.aprudnikov.repository;

import kz.beeline.aprudnikov.entities.TestTaskEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestTaskEntityDAO {

    private final Connection connection;

    public TestTaskEntityDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        // SQL statement for creating a new table
        String sql = """
                CREATE TABLE IF NOT EXISTS test_task_entities (
                 id integer PRIMARY KEY,
                 data text NOT NULL
                );""";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void putEntity(TestTaskEntity entity) {
        // TODO make method working
    }

    public TestTaskEntity getEntity(int id) {
        // TODO make method working
        return new TestTaskEntity(0, "example");
    }

    public List<TestTaskEntity> getAllEntities() {
        // TODO make method working
        return new ArrayList<TestTaskEntity>();
    }

}
