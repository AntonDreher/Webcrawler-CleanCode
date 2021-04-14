package com.cleancode.webcrawler;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestClassPageStats {
    private PageStats statsTest;

    @BeforeEach
    public void setup() throws IOException {
        Document document = TestClassPage.getDocumentFromHTMLFile("src/test/resources/testHTML.html");
        statsTest = new PageStats(document);
    }

    @Test
    public void testCountImages() {
        assertEquals(2, statsTest.getImageCount());
    }

    @Test
    public void testCountVideos(){
        assertEquals(1, statsTest.getVideoCount());
    }

    @Test
    public void testCountLinks() {
        assertEquals(3, statsTest.getLinkCount());
    }

    @Test
    public void testCountWords(){
        assertEquals(11, statsTest.getWordCount());
    }

    @Test
    public void testToString() {
        assertEquals("11 word(s), 3 link(s), 2 image(s), 1 video(s)", statsTest.toString());
    }
}
