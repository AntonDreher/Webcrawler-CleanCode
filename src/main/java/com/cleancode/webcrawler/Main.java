package com.cleancode.webcrawler;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        validateArgsLength(args);
        URL startUrl = getStartUrlFromArgs(args);
        PrintStream output = getPrintStreamFromArgs(args);
        WebCrawler webCrawler = new WebCrawler(startUrl);
        webCrawler.crawl();
        webCrawler.printStatsTo(output);
    }

    static void validateArgsLength(String[] args){
        if (args.length < 1 || args.length > 2){
            System.err.println("Usage: webcrawler URL [FILE]");
            System.exit(1);
        }
    }

    static URL getStartUrlFromArgs(String[] args){
        URL startUrl = null;
        try {
            startUrl = new URL(args[0]);
        } catch (MalformedURLException e) {
            System.err.println("Invalid url");
            System.exit(1);
        }
        return startUrl;
    }

    static PrintStream getPrintStreamFromArgs(String[] args){
        PrintStream output = System.out;
        if (args.length >= 2){
            try {
                output = new PrintStream(args[1]);
            } catch (FileNotFoundException e) {
                System.err.println("Invalid file path");
                System.exit(1);
            }
        }
        return output;
    }
}
