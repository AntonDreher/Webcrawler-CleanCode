package com.cleancode.webcrawler.stats;

import com.cleancode.webcrawler.document.DocumentFactory;
import com.cleancode.webcrawler.document.FakeGetDocumentException;
import com.cleancode.webcrawler.document.adapter.DocumentFactoryImpl;
import com.cleancode.webcrawler.page.InvalidPage;
import com.cleancode.webcrawler.page.ValidPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.cleancode.webcrawler.testutil.TestingConstants.*;


public class TestClassCrawlerStats {
    private static DocumentFactory documentFactory;
    private static CrawlerStats stats;

    @BeforeEach
    public  void init(){
        documentFactory = new DocumentFactoryImpl();
        stats = new CrawlerStats();
    }
    @Test
    public void testAddPageOnePage(){
        stats.addPage(new ValidPage(validTestUrl, documentFactory.getDocumentFrom(validTestUrl)));
        Assertions.assertEquals(1, stats.getPages().size());
    }

    @Test
    public void testAddPageMultiplePages(){
        stats.addPage(new ValidPage(validTestUrl, documentFactory.getDocumentFrom(validTestUrl)));
        stats.addPage(new InvalidPage());
        Assertions.assertEquals(2, stats.getPages().size());
    }

    @Test
    public void testMergerCrawlerStats(){
        CrawlerStats toMerge = new CrawlerStats();
        toMerge.addPage(new InvalidPage());
        toMerge.addPage(new InvalidPage());
        toMerge.addPage(new ValidPage(validTestUrl, documentFactory.getDocumentFrom(validTestUrl)));
        toMerge.addCrawlingError(new FakeGetDocumentException());
        toMerge.addNotFoundUrl(notFoundTestUrl);
        stats.mergeWith(toMerge);

        Assertions.assertEquals(3, stats.getPages().size());
        Assertions.assertEquals(1, stats.getNotFoundUrls().size());
        Assertions.assertEquals(1, stats.getCrawlingErrors().size());
    }
}
