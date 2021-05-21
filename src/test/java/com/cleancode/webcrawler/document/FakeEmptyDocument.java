package com.cleancode.webcrawler.document;

public class FakeEmptyDocument implements Document{
    @Override
    public Elements selectCss(String cssSelector) {
        return new FakeEmptyElements();
    }

    @Override
    public String getText() {
        return "";
    }
}
