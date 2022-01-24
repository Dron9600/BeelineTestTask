package kz.beeline.aprudnikov.service;

import kz.beeline.aprudnikov.domain.TestTaskEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestTaskEntityFileManager {

    private final String directory; // folder for files - "C:\\Users\\Lenovo\\Desktop\\Java\\Projects\\BeelineTestTask\\BTest\\TextFiles\\";
    private final int linesNumber; // number of lines in each file
    private final String separator;

    public TestTaskEntityFileManager(String directory, int linesNumber, String separator) {
        this.directory = directory;
        this.linesNumber = linesNumber;
        this.separator = separator;
    }

    String[] dataCollection = new String[]{"Andy", "Jonny", "Biber", "Fred", "Tod", "Bob"};

    public void fillRandom(int fileNumber) throws IOException {
        String fileName = "File#" + fileNumber + ".txt";
        PrintWriter fw = new PrintWriter(new FileWriter(directory + fileName));
        for (int i = 1; i < linesNumber + 1; i++) {
            fw.write((int)(i * Math.random() * 100000) + separator + "Owner name of this id is " + dataCollection[(int)(Math.random() * 6)]  + "\n");
        }
        fw.close();
    }

    public void createRandomFilledFiles(int filesNumber) throws IOException {
        for (int i = 0; i < filesNumber; i++) {
            String fileName = "File#" + filesNumber + ".txt";
            new File(directory + fileName);
            fillRandom(i + 1);
        }
    }

    public List<String> readToList(String fileName) {
        List<String> str = new ArrayList<>();
        try  {
            File file = new File(directory + fileName);
            FileReader fr = new FileReader(file); // reads the file
            BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
            String line;
            while((line = br.readLine()) != null) {
                str.add(line);
            }
            fr.close(); // closes the stream and release the resources
        }
        catch(IOException e)  {
            e.printStackTrace();
        }
        return str;
    }

    public String readToString(String fileName) {
        StringBuilder sb = new StringBuilder();
        try  {
            File file = new File(directory + fileName);
            FileReader fr = new FileReader(file); // reads the file
            BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line); // appends line to string buffer
                sb.append("\n"); // line feed
            }
            fr.close(); // closes the stream and release the resources
        }
        catch(IOException e)  {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public List<TestTaskEntity> readToEntitiesList(String fileName) {
        List<TestTaskEntity> testTaskEntities = new ArrayList<>();
        try  {
            File file = new File(directory + fileName);
            FileReader fr = new FileReader(file); // reads the file
            BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
            String line;
            while((line = br.readLine()) != null) {
                String[] lines = line.split(separator);
                int id = Integer.parseInt(lines[0]);
                String data = lines[1];
                testTaskEntities.add(new TestTaskEntity(id, data));
            }
            fr.close(); // closes the stream and release the resources
        }
        catch(IOException e)  {
            e.printStackTrace();
        }
        return testTaskEntities;
    }

    public void deleteFolder(String directory) {
        File file = new File(directory);
        File[] files = file.listFiles();
        if (files != null) {
            for (File f:files) {
                if (f.isFile() && f.exists()) {
                    boolean deleteResult = f.delete();
                    // System.out.println(f.getName() + " deleted: " + deleteResult);
                } else {
                    System.out.println("can not delete a file due to open or error");
                }
            }
        }
    }


}
