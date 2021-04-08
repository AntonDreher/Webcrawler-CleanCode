package com.cleancode.webcrawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class TestClassPage {
    private static URL standardTestUrl;
    private static Page testPage;
    private static String STANDARD_URL_TO_TEST = "https://www.google.com/";

    @BeforeAll
    public static void init() throws IOException {
        //As we cannot mock final classes, this is a workaround for mocking
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
    public void testGetAbsoulteUrlInvalid(){
        String invalidURL = "htt://www...123...";
        assertEquals(null, testPage.getAbsoluteUrl(invalidURL));
    }

    @Test
    public void testGetUrls(){
        Set<URL> linkedUrls= testPage.getLinkedUrls();
        assertTrue(linkedUrls.size() > 0);
    }

    @Test
    public void testLinksInHTMLFile() {
        try {
            testPage.setDocument(getDocumentFromHTMLFile("src/test/resources/linkList.html"));
            assertEquals(3, testPage.getLinkedUrls().size());
        }catch (IOException e){
            fail("could not open HTML file");
        }
    }

    private Document getDocumentFromHTMLFile(String fileLocation) throws IOException {
        FileReader fileReader = new FileReader(fileLocation);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder contentOfFile = new StringBuilder(3);
        String s;
        while((s=bufferedReader.readLine()) != null){
            contentOfFile.append(s);
        }
        return Jsoup.parse(contentOfFile.toString());
    }
}
