package com.cleancode.webcrawler;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.StringTokenizer;

public class PageStats {
    private Document document;
    private int wordCount;
    private int linkCount;
    private int imageCount;
    private int videoCount;

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
        wordCount = tokenizer.countTokens();
    }

    private int countTagElements(String tag){
        Elements elements = document.select(tag);
        return elements.size();
    }

    private void countLinks(){
        this.linkCount = countTagElements(LINK_TAG + EXCLUDE_SAME_PAGE_LINKS);
    }

    private void countImages(){
        this.imageCount = countTagElements(IMAGE_TAG);
    }

    private void countVideos(){
        this.videoCount = countTagElements(VIDEO_TAG);
    }

    @Override
    public String toString() {
        return String.format("%s word(s), %s link(s), %s image(s), %s video(s)", wordCount, linkCount, imageCount, videoCount);
    }

    int getWordCount() {
        return wordCount;
    }

    int getLinkCount() {
        return linkCount;
    }

    int getImageCount() {
        return imageCount;
    }

    int getVideoCount() {
        return videoCount;
    }
}
