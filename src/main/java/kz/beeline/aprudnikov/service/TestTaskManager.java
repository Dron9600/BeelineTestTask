package kz.beeline.aprudnikov.service;

import kz.beeline.aprudnikov.domain.TestTaskEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestTaskManager implements Runnable {

    private final List<String> fileNames = new ArrayList<>();
    private final List<TestTaskEntity> testTaskEntities = new ArrayList<>();
    private final TestTaskEntityFileManager testTaskEntityFileManager;
    private int finishedThreads = 0;

    public TestTaskManager(String directory, TestTaskEntityFileManager testTaskEntityFileManager) {
        File directoryFiles = new File(directory);
        File[] files = directoryFiles.listFiles();
        assert files != null;
        for (File file : files){
            fileNames.add(file.getName());
        }
        this.testTaskEntityFileManager = testTaskEntityFileManager;
    }

    public void reset(String directory) {
        fileNames.clear();
        testTaskEntities.clear();
        finishedThreads = 0;
        File directoryFiles = new File(directory);
        File[] files = directoryFiles.listFiles();
        assert files != null;
        for (File file : files){
            fileNames.add(file.getName());
        }
    }

    private synchronized String takeFile() {
        int unreadedFilesNumber = fileNames.size();
        if (unreadedFilesNumber > 0) {
            String fileName = fileNames.get(unreadedFilesNumber - 1);
            fileNames.remove(unreadedFilesNumber - 1);
            return fileName;
        } else {
            return null;
        }
    }

    private synchronized void putResult(List<TestTaskEntity> entities) {
        testTaskEntities.addAll(entities);
    }

    public List<TestTaskEntity> getTestTaskEntities() {
        return testTaskEntities;
    }

    @Override
    public void run() {
        String currentFile = takeFile();
        if (currentFile != null) {
            List<TestTaskEntity> listResult = testTaskEntityFileManager.readToEntitiesList(currentFile);
            putResult(listResult);
            System.out.println("Now thread " + Thread.currentThread().getName() + " read file: " + currentFile);
        } else {
            finishedThreads++;
        }
    }

    public int isFinished() {
        return finishedThreads;
    }

}
