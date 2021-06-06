package com.cleancode.webcrawler;

import com.cleancode.webcrawler.document.adapter.DocumentFactoryImpl;
import com.cleancode.webcrawler.page.PageFactory;
import com.cleancode.webcrawler.page.PageFactoryImpl;
import com.cleancode.webcrawler.stats.CrawlerStats;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static com.cleancode.webcrawler.testutil.TestingConstants.*;


public class TestClassWebCrawlerScheduler {
    private WebCrawlerScheduler scheduler;


    @Test
    public void testTwoURLsMultipleTimesExceptSameResult(){
        int numberOfRuns = 3;
        List<URL> urlsToCrawl = new ArrayList<>();
        PageFactory pageFactory = new PageFactoryImpl(new DocumentFactoryImpl());
        WebCrawler.setPageFactory(pageFactory);
        List<CrawlerStats> crawlerStatsList = new ArrayList<>();
        urlsToCrawl.add(validTestUrl);
        scheduler = new WebCrawlerScheduler(urlsToCrawl);
        for(int i=0; i<numberOfRuns; i++){
            scheduler.startWebcrawlers();
            scheduler.waitForWebcrawlersToFinish();
            crawlerStatsList.add(scheduler.getCombinedCrawlerStats());
        }
        Assertions.assertTrue(allElementsInListAreEqual(crawlerStatsList));
    }

    private boolean allElementsInListAreEqual(List<CrawlerStats> crawlerStatsToCompare){
        CrawlerStats objectToCompare = crawlerStatsToCompare.get(0);

        for(CrawlerStats currentObject : crawlerStatsToCompare){
            if(currentObject.getCrawlingErrors().size() != objectToCompare.getCrawlingErrors().size() ||
                    currentObject.getNotFoundUrls().size() != objectToCompare.getNotFoundUrls().size() ||
                    currentObject.getPages().size() != objectToCompare.getPages().size()){
               return false;
            }
        }

        return true;
    }
}
