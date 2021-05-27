package com.cleancode.webcrawler.document;

import java.net.URL;

public class Fake404DocumentFactory implements DocumentFactory {

    @Override
    public Document getDocumentFrom(URL url) {
        throw new Http404StatusException("Not Found", url.toString());
    }
}
