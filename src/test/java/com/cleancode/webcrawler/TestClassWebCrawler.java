package com.cleancode.webcrawler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestClassWebCrawler {
    private static String STANDARD_URL_TO_TEST = "https://www.google.com/";
    private static URL standardTestUrl;
    private static WebCrawler crawlerDepthZero;
    private static WebCrawler crawlerDepthTwo;
    @BeforeAll
    public static void init() throws IOException {
        //As we cannot mock final classes, this is a workaround for mocking
        standardTestUrl = new URL(STANDARD_URL_TO_TEST);
        crawlerDepthZero = new WebCrawler(standardTestUrl, 0);
        crawlerDepthTwo = new WebCrawler(standardTestUrl);
    }

    @Test
    public void testHasExceedMaxDepthFalse(){
        assertFalse(crawlerDepthZero.hasExceededMaxDepth(0));
    }

    @Test
    public void testHasExceedMaxDepthTrue(){
        assertTrue(crawlerDepthTwo.hasExceededMaxDepth(3));
    }

    @Test
    public void testVisitedURLTrue(){
        crawlerDepthZero.crawl();
        assertTrue(crawlerDepthZero.isVisitedUrl(standardTestUrl));
    }

    @Test
    public void testVisitedURLFalse(){
        crawlerDepthZero.crawlRecursive(standardTestUrl, 1);
        assertFalse(crawlerDepthZero.isVisitedUrl(standardTestUrl));
    }

    @Test
    public void testInvalidURLStatus404() throws MalformedURLException {
        URL invalidURL = new URL("https://www.google.com/not/valid");
        crawlerDepthZero.crawl();
        assertTrue(crawlerDepthZero.getNotFoundUrls().contains(invalidURL));
    }

    @Test
    public void testInvalidURLStatus400() throws MalformedURLException {
        URL invalidURL = new URL("https://www.crawler-test.com/status_codes/status_400");
        crawlerDepthZero.crawl();
        assertFalse(crawlerDepthZero.getNotFoundUrls().contains(invalidURL));
    }

}