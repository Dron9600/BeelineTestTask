package kz.beeline.aprudnikov.repository;

import kz.beeline.aprudnikov.domain.TestTaskEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestTaskEntityDAO {

    private final Connection connection;

    public TestTaskEntityDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
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

    public void insert(List<TestTaskEntity> testTaskEntities) throws SQLException {
        String sql = "INSERT INTO test_task_entities(id, data) VALUES(?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        testTaskEntities.forEach(itm -> {
            try {
                pstmt.setInt(1, itm.getId());
                pstmt.setString(2, itm.getData());
                pstmt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public void insert(TestTaskEntity testTaskEntity) {
        String sql = "INSERT INTO test_task_entities(id, data) VALUES(?,?)";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, testTaskEntity.getId());
            pstmt.setString(2, testTaskEntity.getData());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(String[] fromFile) {
        String sql = "INSERT INTO test_task_entities(id, data) VALUES(?,?)";
        String id = fromFile[0];
        String name = fromFile[1];
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<TestTaskEntity> select() {
        String sql ="SELECT * FROM test_task_entities";
        List<TestTaskEntity> resultList = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String data = rs.getString("data");
                resultList.add(new TestTaskEntity(id, data));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultList;
    }



}
