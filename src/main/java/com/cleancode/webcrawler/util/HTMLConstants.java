package com.cleancode.webcrawler.util;

public final class HTMLConstants {
    public final static String LINK_TAG = "a";
    public final static String EXCLUDE_SAME_PAGE_LINKS = ":not([href^=#])";
    public final static String IMAGE_TAG = "img";
    public final static String VIDEO_TAG = "video";
    public final static String LINK_REFERENCE_ATTRIBUTE = "href";
    private HTMLConstants() {
    }
}
