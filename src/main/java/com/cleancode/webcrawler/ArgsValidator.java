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
    private final String[] argumentsToCheck;

    public ArgsValidator(String[] argumentsToCheck) {
        this.argumentsToCheck = argumentsToCheck;
    }

    public void validateArgsLength() {
        if (argumentsToCheck.length < 1) {
            System.err.println("Usage: webcrawler URLs... [FILE]");
            System.exit(1);
        }
    }

    public List<URL> getUrlsToCrawlFromArgs() {
        List<URL> urlsToCrawl = new ArrayList<>();

        for (String argument : argumentsToCheck) {
            URL currentURL = getValidUrlFromArgument(argument);
            if (urlsToCrawl.contains(currentURL)) {
                System.err.println(argument + " multiple times given");
                System.exit(1);
            }
            urlsToCrawl.add(currentURL);
        }

        return urlsToCrawl;
    }

    private URL getValidUrlFromArgument(String argument) {
        try {
            return new URL(argument);
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + argument);
            System.exit(1);
        }
        return null;
    }

    public PrintStream getPrintStreamFromArgs() {
        PrintStream output = System.out;
        int lastPosition = argumentsToCheck.length - 1;
        if (isLastParameterFilePath()) {
            try {
                output = new PrintStream(argumentsToCheck[lastPosition]);
            } catch (FileNotFoundException e) {
                System.err.println("Invalid file path");
                System.exit(1);
            }
        }
        return output;
    }

    boolean isLastParameterFilePath() {
        int lastPosition = argumentsToCheck.length - 1;
        try {
            Paths.get(argumentsToCheck[lastPosition]);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }
}
