package com.cleancode.webcrawler.document;

import java.io.IOException;
import java.net.URL;

public class Fake404DocumentFactory implements DocumentFactory {

    @Override
    public Document getDocumentFrom(URL url) throws IOException {
        throw new HttpStatusException("404", 404, url.toString());
    }
}
