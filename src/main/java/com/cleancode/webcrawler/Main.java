package com.cleancode.webcrawler;

import com.cleancode.webcrawler.document.adapter.DocumentFactoryImpl;

import java.io.PrintStream;
import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArgsValidator.setArgumentsToCheck(args);
        ArgsValidator.validateArgsLength();
        List<URL> urlsToCrawl = ArgsValidator.getUrlsToCrawlFromArgs();
        PrintStream output = ArgsValidator.getPrintStreamFromArgs();
        Page.setDocumentFactory(new DocumentFactoryImpl());
        startCrawling(urlsToCrawl, output);
    }

    static void startCrawling(List<URL> urlsToCrawl, PrintStream output){
        WebCrawlerSchedule scheduler = new WebCrawlerSchedule(urlsToCrawl, output);
        scheduler.run();
    }
}
