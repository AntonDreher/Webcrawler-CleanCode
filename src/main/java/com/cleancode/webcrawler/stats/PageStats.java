package com.cleancode.webcrawler.stats;

import com.cleancode.webcrawler.document.Document;
import com.cleancode.webcrawler.document.Elements;

import java.util.StringTokenizer;

import static com.cleancode.webcrawler.util.HTMLConstants.*;

public class PageStats {
    private final Document document;
    private int wordCount;
    private int linkCount;
    private int imageCount;
    private int videoCount;

    public PageStats(Document document) {
        this.document = document;
        initializeStats();
    }

    private void initializeStats() {
        countWords();
        countImages();
        countLinks();
        countVideos();
    }

    private void countWords() {
        String text = document.getText();
        StringTokenizer tokenizer = new StringTokenizer(text);
        wordCount = tokenizer.countTokens();
    }

    private int countTagElements(String tag) {
        Elements elements = document.selectCss(tag);
        return elements.size();
    }

    private void countLinks() {
        this.linkCount = countTagElements(LINK_TAG + EXCLUDE_SAME_PAGE_LINKS);
    }

    private void countImages() {
        this.imageCount = countTagElements(IMAGE_TAG);
    }

    private void countVideos() {
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
