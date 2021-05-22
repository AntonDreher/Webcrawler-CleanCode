package com.cleancode.webcrawler;

import com.cleancode.webcrawler.document.adapter.DocumentFactoryImpl;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        validateArgsLength(args);
        ArrayList<URL> urlsToCrawl = getUrlsToCrawlFromArgs(args);
        PrintStream output = getPrintStreamFromArgs(args);
        Page.setDocumentFactory(new DocumentFactoryImpl());
        startCrawling(urlsToCrawl);
    }

    static void validateArgsLength(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: webcrawler URL[]");
            System.exit(1);
        }
    }

    static ArrayList<URL> getUrlsToCrawlFromArgs(String[] args) {
        ArrayList<URL> urlsToCrawl = new ArrayList<URL>();

        for(int i=0; i<args.length; i++) {
            try {
                URL currentURL = new URL(args[i]);
                if(!urlsToCrawl.contains(currentURL)) {
                    urlsToCrawl.add(currentURL);
                }else{
                    System.err.println(args[i] + " multiple times given");
                    System.exit(1);
                }
            } catch (MalformedURLException e) {
                System.err.println("Invalid url at position " +i);
                System.exit(1);
            }
        }
        return urlsToCrawl;
    }

    static void startCrawling(ArrayList<URL> urlsToCrawl){
        WebCrawlerSchedule scheduler = new WebCrawlerSchedule(urlsToCrawl);
        scheduler.run();
    }
    static PrintStream getPrintStreamFromArgs(String[] args) {
        PrintStream output = System.out;
      /*  if (args.length >= 2) {
            try {
                output = new PrintStream(args[1]);
            } catch (FileNotFoundException e) {
                System.err.println("Invalid file path");
                System.exit(1);
            }
        }* TODO*/
        return output;
    }


}
