package com.cleancode.webcrawler;

import java.io.PrintStream;
import java.net.URL;
import java.util.List;

public class WebCrawlerSchedule implements Runnable{
    private List<URL> urlsToCrawl;
    private PrintStream output;

    public WebCrawlerSchedule(List<URL> urlsToCrawl, PrintStream output) {
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
