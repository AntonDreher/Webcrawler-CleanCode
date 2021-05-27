package com.cleancode.webcrawler;

import com.cleancode.webcrawler.page.Fake400PageFactory;
import com.cleancode.webcrawler.page.Fake404PageFactory;
import com.cleancode.webcrawler.page.FakePageFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cleancode.webcrawler.testutil.TestingConstants.*;
import static org.junit.jupiter.api.Assertions.*;


public class TestClassWebCrawler {
    private WebCrawler crawlerDepthZero;
    private WebCrawler crawlerDepthOne;
    private WebCrawler crawlerDepthTwo;

    @BeforeEach
    public void init() {
        WebCrawler.setPageFactory(new FakePageFactory());

        crawlerDepthZero = new WebCrawler(validTestUrl, 0);
        crawlerDepthOne = new WebCrawler(validTestUrl, 1);
        crawlerDepthTwo = new WebCrawler(validTestUrl);
    }

    @Test
    public void testCrawlLinkedPages() {
        crawlerDepthOne = new WebCrawler(validTestUrl, 1);
        crawlerDepthOne.crawl();
        assertTrue(crawlerDepthOne.getVisitedUrls().contains(linkedTestUrl));
    }

    @Test
    public void testHasExceedMaxDepthFalse() {
        assertFalse(crawlerDepthZero.hasExceededMaxDepth(0));
    }

    @Test
    public void testHasExceedMaxDepthTrue() {
        assertTrue(crawlerDepthTwo.hasExceededMaxDepth(3));
    }

    @Test
    public void testVisitedURLTrue() {
        crawlerDepthTwo.crawlRecursive(validTestUrl, 0);
        crawlerDepthTwo.crawlRecursive(validTestUrl, 1);
        assertTrue(crawlerDepthTwo.isVisitedUrl(validTestUrl));
    }

    @Test
    public void testVisitedURLFalse() {
        crawlerDepthZero.crawlRecursive(validTestUrl, 1);
        assertFalse(crawlerDepthZero.isVisitedUrl(validTestUrl));
    }

    @Test
    public void testInvalidURLStatus404() {
        WebCrawler.setPageFactory(new Fake404PageFactory());
        crawlerDepthZero = new WebCrawler(notFoundTestUrl, 0);

        crawlerDepthZero.crawl();

        assertTrue(crawlerDepthZero.getNotFoundUrls().contains(notFoundTestUrl));
    }

    @Test
    public void testInvalidURLStatus400() {
        WebCrawler.setPageFactory(new Fake400PageFactory());
        crawlerDepthZero = new WebCrawler(invalidTestUrl, 0);

        crawlerDepthZero.crawl();

        assertFalse(crawlerDepthZero.getNotFoundUrls().contains(invalidTestUrl));
        assertTrue(crawlerDepthZero.getVisitedUrls().contains(invalidTestUrl));
    }
}