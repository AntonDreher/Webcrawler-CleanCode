package com.cleancode.webcrawler.report;

import com.cleancode.webcrawler.stats.CrawlerStats;

import java.io.PrintStream;

public class ReportPrinter {
    private final PrintStream printStream;

    public ReportPrinter(PrintStream printStream){
        this.printStream = printStream;
    }

    public void printAllStats(CrawlerStats crawlerStats){
        printValidPageStats(crawlerStats);
        printBrokenLinks(crawlerStats);
        printErrors(crawlerStats);
    }

    public void printValidPageStats(CrawlerStats crawlerStats) {
        printStream.printf("Report%n%n");

        crawlerStats.getPages().forEach(
                (page) -> printStream.println(page.toString())
        );
    }

    public void printBrokenLinks(CrawlerStats crawlerStats) {
        printStream.printf("%nBroken Links%n%n");

        crawlerStats.getNotFoundUrls().forEach(
                (url) -> printStream.println(url.toString())
        );
    }

    public void printErrors(CrawlerStats crawlerStats) {
        printStream.printf("%nErrors%n%n");

        crawlerStats.getCrawlingErrors().forEach(
                (error) -> printStream.println(error.toString())
        );
    }
}
