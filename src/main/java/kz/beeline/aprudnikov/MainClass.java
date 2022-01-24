package kz.beeline.aprudnikov;

import kz.beeline.aprudnikov.domain.TestTaskEntity;
import kz.beeline.aprudnikov.repository.DBConnection;
import kz.beeline.aprudnikov.repository.TestTaskEntityDAO;
import kz.beeline.aprudnikov.service.TestTaskEntityFileManager;
import kz.beeline.aprudnikov.service.TestTaskManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {

    public static void main(String[] args) throws IOException, InterruptedException {

        // initialization --------->

        // config
        int numberOfWorkingThreads = 5;
        int numberOfFiles = 10;
        int numberOfLinesInEachFile = 10;

        // files folder and database directories
        String filesDirectory = "C:\\Users\\Lenovo\\Desktop\\Java\\BeelineTestQuestionFiles\\";
        String dbDirectory = "C:\\Users\\Lenovo\\Desktop\\Java\\BeelineTestQuestionDB\\";
        String dbName = "testTaskEntities.db";
        String linesSeparator = ";";

        // db connector
        DBConnection sqlLiteConnection = new DBConnection(dbDirectory, dbName);
        sqlLiteConnection.createNewDatabase(dbDirectory, dbName);
        sqlLiteConnection.connect();

        // test entity data access object
        TestTaskEntityDAO entityDAO = new TestTaskEntityDAO(sqlLiteConnection.getConnection());

        // file manager init
        TestTaskEntityFileManager fileManager = new TestTaskEntityFileManager(filesDirectory, numberOfLinesInEachFile, linesSeparator);

        // thread pool manager
        TestTaskManager taskManager = new TestTaskManager(filesDirectory, fileManager);

        // main part --------->

        // delete all files in folder before program started
        fileManager.deleteFolder(filesDirectory);

        // clear table from previous executions data
        entityDAO.deleteTable();

        // create empty table with test task entity structure
        entityDAO.createTable();

        // file manager object init, directory - file directory, lines number - file lines number contains, separator - sign of separation (id;data)
        fileManager.createRandomFilledFiles(numberOfFiles);

        // attempt to read files via pool of threads
        ExecutorService readExecutorService = Executors.newFixedThreadPool(numberOfWorkingThreads);
        for (int i = 1; i <= numberOfFiles; i++) {
            readExecutorService.execute(taskManager);
        }
        readExecutorService.shutdown();

        // Wait until threads finish their jobs or time out
        Thread.sleep(10000L);

        // print all random filled entities
        taskManager.getTestTaskEntities().forEach(item -> System.out.println("id: " + item.getId() + "; data: " + item.getData()));

        // fill table of test task entities
        try {
            entityDAO.insert(taskManager.getTestTaskEntities());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // receive sorted entities from db
        List<TestTaskEntity> resultList = entityDAO.selectAll();

        // fill result file
        fileManager.fill(resultList, "Result.txt");

    }

}
