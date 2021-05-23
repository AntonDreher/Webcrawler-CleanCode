package com.cleancode.webcrawler;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArgsValidator {
    private static String[] argumentsToCheck = null;

    static void validateArgsLength() {
        if (argumentsToCheck.length < 1) {
            System.err.println("Usage: webcrawler URLs... [FILE]");
            System.exit(1);
        }
    }

    static List<URL> getUrlsToCrawlFromArgs() {
        ArrayList<URL> urlsToCrawl = new ArrayList<URL>();

        for(int i=0; i<argumentsToCheck.length; i++) {
            try {
                URL currentURL = new URL(argumentsToCheck[i]);
                if(!urlsToCrawl.contains(currentURL)) {
                    urlsToCrawl.add(currentURL);
                }else{
                    System.err.println(argumentsToCheck[i] + " multiple times given");
                    System.exit(1);
                }
            } catch (MalformedURLException e) {
                System.err.println("Invalid url at position " +i);
                System.exit(1);
            }
        }
        return urlsToCrawl;
    }

    public static void setArgumentsToCheck(String[] argumentsToCheck) {
        ArgsValidator.argumentsToCheck = argumentsToCheck;
    }

    static PrintStream getPrintStreamFromArgs() {
        PrintStream output = System.out;
        int lastPosition = argumentsToCheck.length-1;
        if(isLastParameterFilePath()){
            try {
                output = new PrintStream(argumentsToCheck[lastPosition]);
            } catch (FileNotFoundException e) {
                System.err.println("Invalid file path");
                System.exit(1);
            }
        }
        return output;
    }

    static boolean isLastParameterFilePath(){
        int lastPosition = argumentsToCheck.length-1;
        try{
            Paths.get(argumentsToCheck[lastPosition]);
        }catch(InvalidPathException | NullPointerException ex){
            return false;
        }
        return true;
    }
}
