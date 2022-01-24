package kz.beeline.aprudnikov;

import kz.beeline.aprudnikov.repository.DBConnection;
import kz.beeline.aprudnikov.repository.TestTaskEntityDAO;
import kz.beeline.aprudnikov.service.TestTaskEntityFileManager;
import kz.beeline.aprudnikov.service.TestTaskManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {

    public static void main(String[] args) throws IOException, InterruptedException {

        int numberOfWorkingThreads = 5;
        int numberOfFiles = 10;
        int numberOfLinesInEachFile = 10;

        // files read directory
        String filesDirectory = "C:\\Users\\Lenovo\\Desktop\\Java\\BeelineTestQuestionFiles\\";
        String DBdirectory = "C:\\Users\\Lenovo\\Desktop\\Java\\BeelineTestQuestionDB\\";

        // delete all files in folder before program started
        TestTaskEntityFileManager fileManager = new TestTaskEntityFileManager(filesDirectory, numberOfLinesInEachFile, ";");
        fileManager.deleteFolder(filesDirectory);

        // db connector todo interface for psql or mysql using possibility
        DBConnection sqlLiteConnection = new DBConnection(DBdirectory, "testTaskEntities.db");
        sqlLiteConnection.createNewDatabase(DBdirectory, "testTaskEntities.db");
        sqlLiteConnection.connect();

        // test entity data access object
        TestTaskEntityDAO entityDAO = new TestTaskEntityDAO(sqlLiteConnection.getConnection());

        // create empty table with test task entity structure
        entityDAO.createTable();

        // file manager object init, directory - file directory, lines number - file lines number contains, separator - sign of separation (id;data)
        fileManager.create(numberOfFiles);

        // thread pool manager
        TestTaskManager taskManager = new TestTaskManager(filesDirectory, fileManager);

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

    }

}
