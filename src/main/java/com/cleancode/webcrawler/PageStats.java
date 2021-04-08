package com.cleancode.webcrawler;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.StringTokenizer;

public class PageStats {
    private Document document;
    private int words;
    private int links;
    private int images;
    private int videos;

    final static String LINK_TAG = "a";
    final static String EXCLUDE_SAME_PAGE_LINKS = ":not([href^=#])";
    final static String IMAGE_TAG = "img";
    final static String VIDEO_TAG = "video";

    public PageStats(Document document){
        this.document = document;
        setStats();
    }

    private void setStats(){
        countWords();
        countImages();
        countLinks();
        countVideos();
    }

    private void countWords(){
        String text = document.text();
        StringTokenizer tokenizer = new StringTokenizer(text);
        words = tokenizer.countTokens();
    }

    private int countTagElements(String tag){
        Elements elements = document.select(tag);
        return elements.size();
    }

    private void countLinks(){
        this.links = countTagElements(LINK_TAG + EXCLUDE_SAME_PAGE_LINKS);
    }

    private void countImages(){
        this.images = countTagElements(IMAGE_TAG);
    }

    private void countVideos(){
        this.videos = countTagElements(VIDEO_TAG);
    }

    @Override
    public String toString() {
        return String.format("%s words, %s links, %s images, %s videos", words, links, images, videos);
    }

    int getWords() {
        return words;
    }

    int getLinks() {
        return links;
    }

    int getImages() {
        return images;
    }

    int getVideos() {
        return videos;
    }
}
