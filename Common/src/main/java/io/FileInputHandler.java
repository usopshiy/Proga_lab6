package io;

import file.FileHandler;

import java.util.Scanner;

/**
 * Input handler for files
 */
public class FileInputHandler extends InputHandler {
    public FileInputHandler(String path){
        super(new Scanner(new FileHandler(path).read()));
        getScanner().useDelimiter("\n");
    }
}
