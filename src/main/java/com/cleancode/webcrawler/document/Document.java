package com.cleancode.webcrawler.document;

public interface Document {
    Elements selectCss(String cssSelector);

    String getText();
}
