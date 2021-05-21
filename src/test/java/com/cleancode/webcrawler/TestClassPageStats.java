package com.cleancode.webcrawler;

import com.cleancode.webcrawler.document.Document;
import com.cleancode.webcrawler.document.FakeDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestClassPageStats {
    private PageStats statsTest;

    @BeforeEach
    public void setup() {
        Document document = new FakeDocument();
        statsTest = new PageStats(document);
    }

    @Test
    public void testCountImages() {
        assertEquals(2, statsTest.getImageCount());
    }

    @Test
    public void testCountVideos() {
        assertEquals(1, statsTest.getVideoCount());
    }

    @Test
    public void testCountLinks() {
        assertEquals(3, statsTest.getLinkCount());
    }

    @Test
    public void testCountWords() {
        assertEquals(11, statsTest.getWordCount());
    }

    @Test
    public void testToString() {
        assertEquals("11 word(s), 3 link(s), 2 image(s), 1 video(s)", statsTest.toString());
    }
}
