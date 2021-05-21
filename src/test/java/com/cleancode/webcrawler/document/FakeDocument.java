package com.cleancode.webcrawler.document;

import static com.cleancode.webcrawler.util.HTMLConstants.*;

public class FakeDocument implements Document {
    @Override
    public Elements selectCss(String cssSelector) {
        switch (cssSelector) {
            case LINK_TAG + EXCLUDE_SAME_PAGE_LINKS:
                return new FakeLinkElements();
            case IMAGE_TAG:
                return new FakeImageElements();
            case VIDEO_TAG:
                return new FakeVideoElements();
            default:
                return new FakeEmptyElements();
        }
    }

    @Override
    public String getText() {
        return "Hallo, das ist ein Test. Dieser Text ist 11 Wörter lang.";
    }
}
