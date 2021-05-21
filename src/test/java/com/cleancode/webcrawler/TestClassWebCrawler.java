package com.cleancode.webcrawler;

import com.cleancode.webcrawler.document.*;

import org.jsoup.HttpStatusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class TestClassWebCrawler {
    public static final String STANDARD_URL_TO_TEST = "https://www.google.com";

    private URL standardTestUrl;

    private WebCrawler crawlerDepthZero;
    private WebCrawler crawlerDepthOne;
    private WebCrawler crawlerDepthTwo;

    @BeforeEach
    public void init() throws IOException {
        standardTestUrl = new URL(STANDARD_URL_TO_TEST);
        Page.setDocumentFactory(new FakeDocumentFactory(standardTestUrl));

        crawlerDepthZero = new WebCrawler(standardTestUrl, 0);
        crawlerDepthOne = new WebCrawler(standardTestUrl, 1);
        crawlerDepthTwo = new WebCrawler(standardTestUrl);
    }

    @Test
    public void testCrawlLinkedPages() throws IOException {
        crawlerDepthOne = new WebCrawler(standardTestUrl, 1);

        crawlerDepthOne.crawl();

        assertTrue(crawlerDepthOne.getVisitedUrls().contains(new URL("https://www.test.at")));
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
        crawlerDepthTwo.crawlRecursive(standardTestUrl, 0);
        crawlerDepthTwo.crawlRecursive(standardTestUrl, 1);
        assertTrue(crawlerDepthTwo.isVisitedUrl(standardTestUrl));
    }

    @Test
    public void testVisitedURLFalse(){
        crawlerDepthZero.crawlRecursive(standardTestUrl, 1);
        assertFalse(crawlerDepthZero.isVisitedUrl(standardTestUrl));
    }

    @Test
    public void testInvalidURLStatus404() throws IOException {
        URL invalidURL = new URL("https://www.google.com/not/valid");
        Page.setDocumentFactory(new Fake404DocumentFactory());
        crawlerDepthZero = new WebCrawler(invalidURL, 0);

        crawlerDepthZero.crawl();

        assertTrue(crawlerDepthZero.getNotFoundUrls().contains(invalidURL));
    }

    @Test
    public void testInvalidURLStatus400() throws IOException {
        URL invalidURL = new URL("https://www.google.com/asdf");
        Page.setDocumentFactory(new Fake400DocumentFactory());
        crawlerDepthZero = new WebCrawler(invalidURL, 0);

        crawlerDepthZero.crawl();

        assertFalse(crawlerDepthZero.getNotFoundUrls().contains(invalidURL));
        assertTrue(crawlerDepthZero.getVisitedUrls().contains(invalidURL));
    }

    @Test
    public void testPrintStatsTo() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        crawlerDepthZero.crawl();

        crawlerDepthZero.printStatsTo(printStream);
        assertEquals(
                "Crawler Stats\n\n" +
                "https://www.google.com: 11 word(s), 3 link(s), 2 image(s), 1 video(s)\n\n" +
                "Broken Links\n\n",
                outputStream.toString()
        );
    }

    @Test
    public void testPrintStatsToWithBrokenLink() {
        Page.setDocumentFactory(new Fake404DocumentFactory());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        crawlerDepthZero.crawl();

        crawlerDepthZero.printStatsTo(printStream);
        assertEquals(
                "Crawler Stats\n\n\n" +
                        "Broken Links\n\n" +
                        standardTestUrl + "\n",
                outputStream.toString()
        );
    }
}