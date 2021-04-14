package com.cleancode.webcrawler;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


public class TestClassWebCrawler {
    public static final String STANDARD_URL_TO_TEST = "https://www.google.com";
    private static URL standardTestUrl;

    private WebCrawler crawlerDepthZero;
    private WebCrawler crawlerDepthOne;
    private WebCrawler crawlerDepthTwo;

    private MockConnection connection;
    private Document document;

    @BeforeEach
    public void init() throws IOException {
        standardTestUrl = new URL(STANDARD_URL_TO_TEST);
        connection = new MockConnection(STANDARD_URL_TO_TEST);
        document = Document.createShell(STANDARD_URL_TO_TEST);
        connection.setDocument(Document.createShell(STANDARD_URL_TO_TEST));
        crawlerDepthZero = new WebCrawler(standardTestUrl, 0);
        crawlerDepthOne = new WebCrawler(standardTestUrl, 1);
        crawlerDepthTwo = new WebCrawler(standardTestUrl);
    }

    private void addLinkedPageToDocument(String url) throws IOException {
        URL linkedUrl = new URL(url);
        document.body().append("<a href='" + linkedUrl.toString() + "'></a>");
        MockConnection.addMockConnectionToEmptyDocument("https://www.youtube.com/");
        connection.setDocument(document);
    }

    @Test
    public void testCrawlLinkedPages() throws IOException {
        String linkedUrl = "https://www.youtube.com/";
        addLinkedPageToDocument(linkedUrl);
        crawlerDepthOne = new WebCrawler(standardTestUrl, 1);

        crawlerDepthOne.crawl();

        assertTrue(crawlerDepthOne.getVisitedUrls().contains(new URL(linkedUrl)));
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
        MockConnection connection = new MockConnection(invalidURL.toString());
        connection.setThrows(new HttpStatusException("Error 404", 404, invalidURL.toString()));
        crawlerDepthZero = new WebCrawler(invalidURL, 0);

        crawlerDepthZero.crawl();

        assertTrue(crawlerDepthZero.getNotFoundUrls().contains(invalidURL));
    }

    @Test
    public void testInvalidURLStatus400() throws IOException {
        URL invalidURL = new URL("https://www.google.com/asdf");
        MockConnection connection = new MockConnection(invalidURL.toString());
        connection.setThrows(new HttpStatusException("Error 400", 400, invalidURL.toString()));
        crawlerDepthZero = new WebCrawler(invalidURL, 0);

        crawlerDepthZero.crawl();

        assertFalse(crawlerDepthZero.getNotFoundUrls().contains(invalidURL));
        assertTrue(crawlerDepthZero.getVisitedUrls().contains(invalidURL));
    }

    @Test
    public void testPrintStatsTo() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        connection.setDocument(TestClassPage.getDocumentFromHTMLFile("src/test/resources/testHTML.html"));

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
    public void testPrintStatsToWithBrokenLink() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        connection.setThrows(new HttpStatusException("Error 404", 404, standardTestUrl.toString()));

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