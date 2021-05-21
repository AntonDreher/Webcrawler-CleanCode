package com.cleancode.webcrawler.document.adapter;

import com.cleancode.webcrawler.document.Document;
import com.cleancode.webcrawler.document.Elements;

public class DocumentAdapter implements Document {
    private final org.jsoup.nodes.Document document;

    public DocumentAdapter(org.jsoup.nodes.Document document) {
        this.document = document;
    }

    @Override
    public Elements selectCss(String cssSelector) {
        return new ElementsAdapter(document.select(cssSelector));
    }

    @Override
    public String getText() {
        return document.text();
    }
}
