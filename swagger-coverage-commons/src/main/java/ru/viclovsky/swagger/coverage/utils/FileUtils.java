package ru.viclovsky.swagger.coverage.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static Path writeInFile(String json, String name, String suffix) {
        String fileName = name + suffix;
        //Paths.get("")
        //Files.write()
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(fileName, true)) {
            fileWriter.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Could not write json to file", e);
        }

        return Paths.get(fileName);
    }
}
