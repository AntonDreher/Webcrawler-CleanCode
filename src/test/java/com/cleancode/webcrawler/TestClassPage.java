package com.cleancode.webcrawler;

import com.cleancode.webcrawler.document.FakeDocumentFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class TestClassPage {
    public static final String STANDARD_URL_TO_TEST = "https://www.google.com/";

    private Page testPage;
    private URL standardTestUrl;

    @BeforeEach
    public void init() throws IOException {
        standardTestUrl = new URL(STANDARD_URL_TO_TEST);
        Page.setDocumentFactory(new FakeDocumentFactory(standardTestUrl));
        testPage = new Page(standardTestUrl);
        testPage.computePageStatistics();
    }

    @Test
    public void testGetAbsoluteUrl() {
        String absoluteUrl = "https://www.wikipedia.com";
        assertEquals(absoluteUrl, testPage.getAbsoluteUrl(absoluteUrl).toString());
    }

    @Test
    public void testGetAbsoluteUrlWithRelativeUrl() {
        String relativeUrl = "relative/URL/";
        assertEquals(STANDARD_URL_TO_TEST + relativeUrl, testPage.getAbsoluteUrl(relativeUrl).toString());
    }

    @Test
    public void testGetAbsoluteUrlInvalid() {
        String invalidURL = "htt://www...123...";
        assertNull(testPage.getAbsoluteUrl(invalidURL));
    }

    @Test
    public void testGetUrls() {
        Set<URL> linkedUrls = testPage.getLinkedUrls();
        assertTrue(linkedUrls.size() > 0);
    }

    @Test
    public void testLinksInHTMLFile() {
        assertEquals(3, testPage.getLinkedUrls().size());
    }

    @Test
    public void testToString() {
        assertTrue(testPage.toString().startsWith(standardTestUrl.toString()));
    }
}
