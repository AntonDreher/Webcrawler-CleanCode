package com.cleancode.webcrawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class TestClassPage {
    private static Page testPage;
    public static final String STANDARD_URL_TO_TEST = "https://www.google.com/";
    private static URL standardTestUrl;

    @BeforeEach
    public void init() throws IOException {
        MockConnection connection = new MockConnection(STANDARD_URL_TO_TEST);
        connection.setDocument(getDocumentFromHTMLFile("src/test/resources/testHTML.html"));
        standardTestUrl = new URL(STANDARD_URL_TO_TEST);
        testPage = new Page(standardTestUrl);
    }

    @Test
    public void testGetAbsoluteUrl(){
        String absoluteUrl = "https://www.wikipedia.com";
        assertEquals(absoluteUrl, testPage.getAbsoluteUrl(absoluteUrl).toString());
    }

    @Test
    public void testGetAbsoluteUrlWithRelativeUrl(){
        String relativeUrl = "relative/URL/";
        assertEquals(STANDARD_URL_TO_TEST + relativeUrl,  testPage.getAbsoluteUrl(relativeUrl).toString());
    }

    @Test
    public void testGetAbsoluteUrlInvalid(){
        String invalidURL = "htt://www...123...";
        assertNull(testPage.getAbsoluteUrl(invalidURL));
    }

    @Test
    public void testGetUrls(){
        Set<URL> linkedUrls= testPage.getLinkedUrls();
        assertTrue(linkedUrls.size() > 0);
    }

    @Test
    public void testLinksInHTMLFile() {
        assertEquals(3, testPage.getLinkedUrls().size());
    }

    static Document getDocumentFromHTMLFile(String fileLocation) throws IOException {
        return Jsoup.parse(Files.readString(Path.of(fileLocation)));
    }
}
