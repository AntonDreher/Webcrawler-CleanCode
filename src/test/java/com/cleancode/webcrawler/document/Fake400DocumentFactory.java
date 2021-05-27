package com.cleancode.webcrawler.document;

import java.net.URL;

public class Fake400DocumentFactory implements DocumentFactory {

    @Override
    public Document getDocumentFrom(URL url) {
        throw new GetDocumentException("Bad Request", new HttpStatusException("Bad Request", 400, url.toString()), url);
    }
}
