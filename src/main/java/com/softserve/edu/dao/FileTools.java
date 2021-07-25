package com.softserve.edu.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileTools {
    public final String FILE_NOT_FOUND_EXCEPTION = "File %s could not be found";
    public final String FILE_NOT_READ_EXCEPTION = "File %s could not be read";
    //
    public final String PATH_SEPARATOR = "/";
    //
    private String filePath;

    public FileTools(String fileName) {
        this.filePath = this.getClass().getResource(PATH_SEPARATOR + fileName).getPath(); //.substring(1);
    }

    public List<String> readRows() {
        List<String> result = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String row = null;
            while ((row = bufferedReader.readLine()) != null) {
                result.add(row);
            }
        } catch (FileNotFoundException e) {
            // TODO Develop Custom Exceptions
            throw new RuntimeException(String.format(FILE_NOT_FOUND_EXCEPTION, filePath), e);
        } catch (IOException e) {
            throw new RuntimeException(String.format(FILE_NOT_READ_EXCEPTION, filePath), e);
        }
        return result;
    }

}
