package com.cleancode.webcrawler;

import com.cleancode.webcrawler.stats.CrawlerStats;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebCrawlerScheduler {
    private final List<URL> urlsToCrawl;
    private final List<Future<CrawlerStats>> crawlerStatsFutures;
    private final CrawlerStats combinedCrawlerStats = new CrawlerStats();

    public WebCrawlerScheduler(List<URL> urlsToCrawl) {
        this.urlsToCrawl = urlsToCrawl;
        this.crawlerStatsFutures = new ArrayList<>();
    }

    public void startWebcrawlers() {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (URL currentUrl : urlsToCrawl) {
            Future<CrawlerStats> crawlerStatsFuture = pool.submit(new WebCrawler(currentUrl));
            crawlerStatsFutures.add(crawlerStatsFuture);
        }
        pool.shutdown();
    }

    public void waitForWebcrawlersToFinish() {
        for (Future<CrawlerStats> crawlerStatsFuture : crawlerStatsFutures) {
            try {
                combinedCrawlerStats.mergeWith(crawlerStatsFuture.get());
            } catch (ExecutionException | InterruptedException e) {
                // TODO: find a proper way to handle this
                e.printStackTrace();
            }
        }
    }

    public CrawlerStats getCombinedCrawlerStats() {
        return combinedCrawlerStats;
    }
}
