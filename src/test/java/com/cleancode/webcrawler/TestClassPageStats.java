package com.cleancode.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestClassPageStats {

    @Test
    public void testCountOneImage(){
        Document documentOnlyImageTag = Jsoup.parse("<img>test.png</img>");
        PageStats statsTest = new PageStats(documentOnlyImageTag);
        assertEquals(1, statsTest.getImages());
    }

    @Test
    public void testCountMultipleImages(){
        Document documentMultipleImageTags = Jsoup.parse("<img>test.png</img>" +
                                                            "<img>test.png</img>"+
                                                            "<img>test.png</img>" +
                                                            "<img>test.png</img>" +
                                                            "<img>test.png</img>");
        PageStats statsTest = new PageStats(documentMultipleImageTags);

        assertEquals(5, statsTest.getImages());
    }

    @Test
    public void testCountOneVideo(){
        Document document = Jsoup.parse("<video width=\"320\" height=\"240\" controls>\n" +
                                            "  <source src=\"movie.mp4\" type=\"video/mp4\">\n" +
                                            "</video>");
        PageStats statsVideoTest = new PageStats(document);
        assertEquals(1, statsVideoTest.getVideos());
    }

    @Test
    public void testCountMultipleVideos(){
        Document document = Jsoup.parse("<video width=\"320\" height=\"240\" controls>\n" +
                                              "  <source src=\"movie.mp4\" type=\"video/mp4\">\n" +
                                            "</video>" +
                                            "<video width=\"320\" height=\"240\" controls>\n" +
                                            "  <source src=\"movie.mp4\" type=\"video/mp4\">\n" +
                                            "</video>");
        PageStats statsVideoTest = new PageStats(document);
        assertEquals(2, statsVideoTest.getVideos());
    }

    @Test
    public void testCountOneLink(){
        Document documentOneLinkTag = Jsoup.parse("<a>link</a>");
        PageStats statsLinkTest = new PageStats(documentOneLinkTag);
        assertEquals(1, statsLinkTest.getLinks());
    }

    @Test
    public void testCountMultipleLinks(){
        Document documentOneLinkTag = Jsoup.parse("<a>link</a>" +
                                                        "<a>link</a>"+
                                                        "<a>link</a>"+
                                                        "<a>link</a>");
        PageStats statsLinkTest = new PageStats(documentOneLinkTag);
        assertEquals(4, statsLinkTest.getLinks());
    }

    @Test
    public void testCountWordsWithoutTags(){
        Document documentWordsWithoutTag = Jsoup.parse("one two three");
        PageStats statsWordTest = new PageStats(documentWordsWithoutTag);
        assertEquals(3, statsWordTest.getWords());
    }

    @Test
    public void testCountWordsWithTags(){
        Document documentWordsWithTag= Jsoup.parse("<b>one two</b>");
        PageStats statsWordTest = new PageStats(documentWordsWithTag);
        assertEquals(2, statsWordTest.getWords());
    }

}
