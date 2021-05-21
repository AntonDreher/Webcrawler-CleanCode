package com.cleancode.webcrawler;

import java.net.URL;
import java.util.ArrayList;

public class WebCrawlerSchedule implements Runnable{
    private ArrayList<URL> urlsToCrawl;
    public WebCrawlerSchedule(ArrayList<URL> urlsToCrawl){
        this.urlsToCrawl = urlsToCrawl;
    }
    @Override
    public void run() {
        for(URL currentUrl :  urlsToCrawl){
            Runnable crawlerHandler = () -> {
                WebCrawler crawler = new WebCrawler(currentUrl);
                crawler.crawl();
                crawler.printStatsTo(System.out);
            };
            Thread crawlerThread = new Thread(crawlerHandler);
            crawlerThread.start();
        }
    }
}
