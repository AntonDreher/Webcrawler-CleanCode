package com.cleancode.webcrawler;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;

public class WebCrawlerSchedule implements Runnable{
    private ArrayList<URL> urlsToCrawl;
    private PrintStream output;
    public WebCrawlerSchedule(ArrayList<URL> urlsToCrawl, PrintStream output){
        this.urlsToCrawl = urlsToCrawl;
        this.output = output;
    }
    @Override
    public void run() {
        for(URL currentUrl :  urlsToCrawl){
            Runnable crawlerHandler = () -> {
                WebCrawler crawler = new WebCrawler(currentUrl);
                crawler.crawl();
                crawler.printStatsTo(output);
            };
            Thread crawlerThread = new Thread(crawlerHandler);
            crawlerThread.start();
        }
    }
}
