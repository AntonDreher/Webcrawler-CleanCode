package com.cleancode.webcrawler.document.adapter;

import com.cleancode.webcrawler.document.Elements;

import java.util.List;

public class ElementsAdapter implements Elements {
    private final org.jsoup.select.Elements elements;

    public ElementsAdapter(org.jsoup.select.Elements elements) {
        this.elements = elements;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public List<String> eachAttr(String attributeKey) {
        return elements.eachAttr(attributeKey);
    }
}
