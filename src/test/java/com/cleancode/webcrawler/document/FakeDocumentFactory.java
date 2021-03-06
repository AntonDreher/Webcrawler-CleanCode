package com.cleancode.webcrawler.document;

import java.net.URL;
import java.util.Set;

public class FakeDocumentFactory implements DocumentFactory {
    Set<URL> nonEmptyPageUrls;

    public FakeDocumentFactory(URL... nonEmptyPageUrls) {
        this.nonEmptyPageUrls = Set.of(nonEmptyPageUrls);
    }

    @Override
    public Document getDocumentFrom(URL url) {
        if (nonEmptyPageUrls.contains(url)) {
            return new FakeDocument();
        }
        return new FakeEmptyDocument();
    }
}
