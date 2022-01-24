package kz.beeline.aprudnikov.service;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class TestTaskEntityFileManagerTest {

    int numberOfFiles = 10;
    int numberOfLinesInEachFile = 10;

    // files read directory
    String filesDirectory = "C:\\Users\\Lenovo\\Desktop\\Java\\BeelineTestQuestionFilesTest\\";

    // delete all files in folder before program started
    TestTaskEntityFileManager fileManager = new TestTaskEntityFileManager(filesDirectory, numberOfLinesInEachFile, ";");

    @Before
    public void testInit() throws IOException {
        // making directory if not exists
        File file = new File(filesDirectory);
        boolean mkdirs = file.mkdirs();
        // clearing directory from previous executions data
        fileManager.deleteFolder(filesDirectory);
        fileManager.createRandomFilledFiles(numberOfFiles);
        System.out.println("Test initialization completed");
    }

    @Test
    public void fileCreateTest() {
        // test for number of created files asserting
        File file = new File(filesDirectory);
        File[] files = file.listFiles();
        assert(Objects.requireNonNull(files).length == numberOfFiles);
        System.out.println("File creation test passed");
    }

    @Test
    public void fileReadTest() {
        // test for number of created lines in all files asserting
        File directory = new File(filesDirectory);
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            List<String> lines = fileManager.readToList(file.getName());
            assert(lines.size() == numberOfLinesInEachFile);
        }
        System.out.println("File reading test passed");
    }

    @Test
    public void fileReadTestSecond() {
        // test for number of created lines in all files asserting
        File directory = new File(filesDirectory);
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            String lines = fileManager.readToString(file.getName());
            assert(lines.split("\n").length == numberOfLinesInEachFile);
        }
        System.out.println("File reading test passed");
    }

}
