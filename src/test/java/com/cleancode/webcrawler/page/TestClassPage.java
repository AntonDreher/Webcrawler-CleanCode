package com.cleancode.webcrawler.page;

import com.cleancode.webcrawler.document.FakeDocumentFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Set;

import static com.cleancode.webcrawler.testutil.TestingConstants.validTestUrl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestClassPage {
    private Page testPage;

    @BeforeEach
    public void init() {
        testPage = new ValidPage(validTestUrl, new FakeDocumentFactory(validTestUrl).getDocumentFrom(validTestUrl));
        testPage.computePageStatistics();
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
        assertTrue(testPage.toString().startsWith(validTestUrl.toString()));
    }
}
