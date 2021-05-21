package com.cleancode.webcrawler.document;

import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.net.URL;

public class Fake400DocumentFactory implements DocumentFactory {

    @Override
    public Document getDocumentFrom(URL url) throws IOException {
        throw new HttpStatusException("400", 400, url.toString());
    }
}
